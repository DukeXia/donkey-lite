/**
 * Copyright 2006-2017 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.donkeycode.mybatis.api;

import java.sql.Types;
import java.util.Properties;

import io.donkeycode.mybatis.api.dom.java.FullyQualifiedJavaType;
import io.donkeycode.mybatis.config.Context;
import io.donkeycode.mybatis.internal.util.StringUtility;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class holds information about an introspected column. The class has
 * utility methods useful for generating iBATIS objects.
 *
 * @author Jeff Butler
 */
@Setter
@Getter
@ToString
public class IntrospectedColumn {

	protected String actualColumnName;

	protected int jdbcType;

	protected String jdbcTypeName;

	protected boolean nullable;

	protected int length;

	protected int scale;

	protected boolean identity;

	protected boolean isSequenceColumn;

	protected String javaProperty;

	protected FullyQualifiedJavaType fullyQualifiedJavaType;

	protected String tableAlias;

	protected String typeHandler;

	protected Context context;

	protected boolean isColumnNameDelimited;

	protected IntrospectedTable introspectedTable;

	protected Properties properties;

	// any database comment associated with this column. May be null
	protected String remarks;

	protected String defaultValue;

	/**
	 * true if the JDBC driver reports that this column is auto-increment.
	 */
	protected boolean isAutoIncrement;

	/**
	 * true if the JDBC driver reports that this column is generated.
	 */
	protected boolean isGeneratedColumn;

	/**
	 * True if there is a column override that defines this column as GENERATED ALWAYS.
	 */
	protected boolean isGeneratedAlways;

	/**
	 * Constructs a Column definition. This object holds all the information
	 * about a column that is required to generate Java objects and SQL maps;
	 */
	public IntrospectedColumn() {
		super();
		properties = new Properties();
	}

	 
	public void setActualColumnName(String actualColumnName) {
		this.actualColumnName = actualColumnName;
		isColumnNameDelimited = StringUtility.stringContainsSpace(actualColumnName);
	}

	public boolean isBLOBColumn() {
		String typeName = getJdbcTypeName();

		return "BINARY".equals(typeName) || "BLOB".equals(typeName) //$NON-NLS-2$
				|| "CLOB".equals(typeName) || "LONGNVARCHAR".equals(typeName) //$NON-NLS-2$
				|| "LONGVARBINARY".equals(typeName) || "LONGVARCHAR".equals(typeName) //$NON-NLS-2$
				|| "NCLOB".equals(typeName) || "VARBINARY".equals(typeName); //$NON-NLS-2$
	}

	public boolean isStringColumn() {
		return fullyQualifiedJavaType.equals(FullyQualifiedJavaType.getStringInstance());
	}

	public boolean isJdbcCharacterColumn() {
		return jdbcType == Types.CHAR || jdbcType == Types.CLOB || jdbcType == Types.LONGVARCHAR || jdbcType == Types.VARCHAR || jdbcType == Types.LONGNVARCHAR || jdbcType == Types.NCHAR || jdbcType == Types.NCLOB || jdbcType == Types.NVARCHAR;
	}

	public String getJavaProperty() {
		return getJavaProperty(null);
	}

	public String getJavaProperty(String prefix) {
		if (prefix == null) {
			return javaProperty;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		sb.append(javaProperty);

		return sb.toString();
	}
 

	public boolean isJDBCDateColumn() {
		return fullyQualifiedJavaType.equals(FullyQualifiedJavaType.getDateInstance()) && "DATE".equalsIgnoreCase(jdbcTypeName);
	}

	public boolean isJDBCTimeColumn() {
		return fullyQualifiedJavaType.equals(FullyQualifiedJavaType.getDateInstance()) && "TIME".equalsIgnoreCase(jdbcTypeName);
	}

	public String getJdbcTypeName() {
		if (jdbcTypeName == null) {
			return "OTHER";
		}

		return jdbcTypeName;
	}

}
