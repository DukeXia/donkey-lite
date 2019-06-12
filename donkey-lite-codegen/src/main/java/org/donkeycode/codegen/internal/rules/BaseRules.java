package org.donkeycode.codegen.internal.rules;

import org.donkeycode.codegen.api.IntrospectedTable;
import org.donkeycode.codegen.api.dom.java.FullyQualifiedJavaType;
import org.donkeycode.codegen.config.PropertyRegistry;
import org.donkeycode.codegen.config.TableConfiguration;
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

		if (generateBaseRecordClass()) {
			answer = introspectedTable.getBaseRecordType();
		} else {
			answer = introspectedTable.getPrimaryKeyType();
		}

		return new FullyQualifiedJavaType(answer);
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

		return false;
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
		return false;
	}

	@Override
	public boolean generateJavaClient() {
		return !isModelOnly;
	}
}
