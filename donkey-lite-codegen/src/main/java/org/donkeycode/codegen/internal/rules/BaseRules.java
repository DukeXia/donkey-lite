package org.donkeycode.codegen.internal.rules;

import org.donkeycode.codegen.api.IntrospectedTable;
import org.donkeycode.codegen.api.IntrospectedTable.TargetRuntime;
import org.donkeycode.codegen.api.dom.java.FullyQualifiedJavaType;
import org.donkeycode.codegen.config.PropertyRegistry;
import org.donkeycode.codegen.config.TableConfiguration;
import org.donkeycode.codegen.generator.mybatis3.ListUtilities;
import org.donkeycode.codegen.internal.util.StringUtility;

/**
 * This class centralizes all the rules related to code generation - including
 * the methods and objects to create, and certain attributes related to those
 * objects.
 *
 * @author Jeff Butler
 */
public abstract class BaseRules implements Rules {

	protected TableConfiguration tableConfiguration;

	protected IntrospectedTable introspectedTable;

	protected final boolean isModelOnly;

	public BaseRules(IntrospectedTable introspectedTable) {
		super();
		this.introspectedTable = introspectedTable;
		this.tableConfiguration = introspectedTable.getTableConfiguration();
		String modelOnly = tableConfiguration.getProperty(PropertyRegistry.TABLE_MODEL_ONLY);
		isModelOnly = StringUtility.isTrue(modelOnly);
	}

	/**
	 * Implements the rule for generating the insert SQL Map element and DAO
	 * method. If the insert statement is allowed, then generate the element and
	 * method.
	 *
	 * @return true if the element and method should be generated
	 */
	@Override
	public boolean generateInsert() {
		if (isModelOnly) {
			return false;
		}

		return tableConfiguration.isInsertStatementEnabled();
	}

	/**
	 * Implements the rule for generating the insert selective SQL Map element
	 * and DAO method. If the insert statement is allowed, then generate the
	 * element and method.
	 *
	 * @return true if the element and method should be generated
	 */
	@Override
	public boolean generateInsertSelective() {
		if (isModelOnly) {
			return false;
		}

		return tableConfiguration.isInsertStatementEnabled();
	}

	/**
	 * Calculates the class that contains all fields. This class is used as the
	 * insert statement parameter, as well as the returned value from the select
	 * by primary key method. The actual class depends on how the domain model
	 * is generated.
	 *
	 * @return the type of the class that holds all fields
	 */
	@Override
	public FullyQualifiedJavaType calculateAllFieldsClass() {

		String answer;

		if (generateRecordWithBLOBsClass()) {
			answer = introspectedTable.getRecordWithBLOBsType();
		} else if (generateBaseRecordClass()) {
			answer = introspectedTable.getBaseRecordType();
		} else {
			answer = introspectedTable.getPrimaryKeyType();
		}

		return new FullyQualifiedJavaType(answer);
	}

	/**
	 * Implements the rule for generating the update by primary key selective
	 * SQL Map element and DAO method. If the table has a primary key as well as
	 * other fields, and the updateByPrimaryKey statement is allowed, then
	 * generate the element and method.
	 *
	 * @return true if the element and method should be generated
	 */
	@Override
	public boolean generateUpdateByPrimaryKeySelective() {
		if (isModelOnly) {
			return false;
		}

		if (ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns()).isEmpty()) {
			return false;
		}

		boolean rc = tableConfiguration.isUpdateByPrimaryKeyStatementEnabled() && introspectedTable.hasPrimaryKeyColumns() && (introspectedTable.hasBLOBColumns() || introspectedTable.hasBaseColumns());

		return rc;
	}

	/**
	 * Implements the rule for generating the delete by primary key SQL Map
	 * element and DAO method. If the table has a primary key, and the
	 * deleteByPrimaryKey statement is allowed, then generate the element and
	 * method.
	 *
	 * @return true if the element and method should be generated
	 */
	@Override
	public boolean generateDeleteByPrimaryKey() {
		if (isModelOnly) {
			return false;
		}

		boolean rc = tableConfiguration.isDeleteByPrimaryKeyStatementEnabled() && introspectedTable.hasPrimaryKeyColumns();

		return rc;
	}

	/**
	 * Implements the rule for generating the delete by example SQL Map element
	 * and DAO method. If the deleteByExample statement is allowed, then
	 * generate the element and method.
	 *
	 * @return true if the element and method should be generated
	 */
	@Override
	public boolean generateDeleteByExample() {
		if (isModelOnly) {
			return false;
		}

		boolean rc = tableConfiguration.isDeleteByExampleStatementEnabled();

		return rc;
	}

	/**
	 * Implements the rule for generating the result map without BLOBs. If
	 * either select method is allowed, then generate the result map.
	 *
	 * @return true if the result map should be generated
	 */
	@Override
	public boolean generateBaseResultMap() {
		if (isModelOnly) {
			return true;
		}

		boolean rc = tableConfiguration.isSelectByExampleStatementEnabled() || tableConfiguration.isSelectByPrimaryKeyStatementEnabled();

		return rc;
	}

	/**
	 * Implements the rule for generating the SQL example where clause element.
	 *
	 * <p>In iBATIS2, generate the element if the selectByExample, deleteByExample,
	 * updateByExample, or countByExample statements are allowed.
	 *
	 * <p>In MyBatis3, generate the element if the selectByExample,
	 * deleteByExample, or countByExample statements are allowed.
	 *
	 * @return true if the SQL where clause element should be generated
	 */
	@Override
	public boolean generateSQLExampleWhereClause() {
		if (isModelOnly) {
			return false;
		}

		boolean rc = tableConfiguration.isSelectByExampleStatementEnabled() || tableConfiguration.isDeleteByExampleStatementEnabled() || tableConfiguration.isCountByExampleStatementEnabled();

		if (introspectedTable.getTargetRuntime() == TargetRuntime.IBATIS2) {
			rc |= tableConfiguration.isUpdateByExampleStatementEnabled();
		}

		return rc;
	}

	/**
	 * Implements the rule for generating the SQL example where clause element
	 * specifically for use in the update by example methods.
	 *
	 * <p>In iBATIS2, do not generate the element.
	 *
	 * <p>In MyBatis3, generate the element if the updateByExample statements are
	 * allowed.
	 *
	 * @return true if the SQL where clause element should be generated
	 */
	@Override
	public boolean generateMyBatis3UpdateByExampleWhereClause() {
		if (isModelOnly) {
			return false;
		}

		return introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3 && tableConfiguration.isUpdateByExampleStatementEnabled();
	}

	/**
	 * Implements the rule for generating the select by primary key SQL Map
	 * element and DAO method. If the table has a primary key as well as other
	 * fields, and the selectByPrimaryKey statement is allowed, then generate
	 * the element and method.
	 *
	 * @return true if the element and method should be generated
	 */
	@Override
	public boolean generateSelectByPrimaryKey() {
		if (isModelOnly) {
			return false;
		}

		boolean rc = tableConfiguration.isSelectByPrimaryKeyStatementEnabled() && introspectedTable.hasPrimaryKeyColumns() && (introspectedTable.hasBaseColumns() || introspectedTable.hasBLOBColumns());

		return rc;
	}

	@Override
	public boolean generateCountByExample() {
		if (isModelOnly) {
			return false;
		}

		boolean rc = tableConfiguration.isCountByExampleStatementEnabled();

		return rc;
	}

	@Override
	public IntrospectedTable getIntrospectedTable() {
		return introspectedTable;
	}

	@Override
	public boolean generateBaseColumnList() {
		if (isModelOnly) {
			return false;
		}

		return generateSelectByPrimaryKey();
	}

	@Override
	public boolean generateBlobColumnList() {
		if (isModelOnly) {
			return false;
		}

		return introspectedTable.hasBLOBColumns() && (tableConfiguration.isSelectByExampleStatementEnabled() || tableConfiguration.isSelectByPrimaryKeyStatementEnabled());
	}

	@Override
	public boolean generateJavaClient() {
		return !isModelOnly;
	}
}
