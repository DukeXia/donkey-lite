/**
 * Copyright 2006-2018 the original author or authors.
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
package org.donkeycode.codegen.internal.types;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.donkeycode.codegen.api.IntrospectedColumn;
import org.donkeycode.codegen.api.JavaTypeResolver;
import org.donkeycode.codegen.api.dom.java.FullyQualifiedJavaType;
import org.donkeycode.codegen.config.Context;
import org.donkeycode.codegen.config.PropertyRegistry;
import org.donkeycode.codegen.internal.util.StringUtility;

/**
 *
 * @author Jeff Butler
 */
public class JavaTypeResolverDefaultImpl implements JavaTypeResolver {

	protected List<String> warnings;

	protected Properties properties;

	protected Context context;

	protected boolean forceBigDecimals;
	protected boolean useJSR310Types;

	protected Map<Integer, JdbcTypeInformation> typeMap;

	// TODO - remove when we get to JDK 8
	private static final int TIME_WITH_TIMEZONE = 2013;
	private static final int TIMESTAMP_WITH_TIMEZONE = 2014;

	public JavaTypeResolverDefaultImpl() {
		super();
		properties = new Properties();
		typeMap = new HashMap<Integer, JdbcTypeInformation>();

		typeMap.put(Types.ARRAY, new JdbcTypeInformation("ARRAY", new FullyQualifiedJavaType(Object.class.getName())));
		typeMap.put(Types.BIGINT, new JdbcTypeInformation("BIGINT", new FullyQualifiedJavaType(Long.class.getName())));
		typeMap.put(Types.BINARY, new JdbcTypeInformation("BINARY", new FullyQualifiedJavaType("byte[]")));
		typeMap.put(Types.BIT, new JdbcTypeInformation("BIT", new FullyQualifiedJavaType(Boolean.class.getName())));
		typeMap.put(Types.BLOB, new JdbcTypeInformation("BLOB", new FullyQualifiedJavaType("byte[]")));
		typeMap.put(Types.BOOLEAN, new JdbcTypeInformation("BOOLEAN", new FullyQualifiedJavaType(Boolean.class.getName())));
		typeMap.put(Types.CHAR, new JdbcTypeInformation("CHAR", new FullyQualifiedJavaType(String.class.getName())));
		typeMap.put(Types.CLOB, new JdbcTypeInformation("CLOB", new FullyQualifiedJavaType(String.class.getName())));
		typeMap.put(Types.DATALINK, new JdbcTypeInformation("DATALINK", new FullyQualifiedJavaType(Object.class.getName())));
		typeMap.put(Types.DATE, new JdbcTypeInformation("DATE", new FullyQualifiedJavaType(Date.class.getName())));
		typeMap.put(Types.DECIMAL, new JdbcTypeInformation("DECIMAL", new FullyQualifiedJavaType(BigDecimal.class.getName())));
		typeMap.put(Types.DISTINCT, new JdbcTypeInformation("DISTINCT", new FullyQualifiedJavaType(Object.class.getName())));
		typeMap.put(Types.DOUBLE, new JdbcTypeInformation("DOUBLE", new FullyQualifiedJavaType(Double.class.getName())));
		typeMap.put(Types.FLOAT, new JdbcTypeInformation("FLOAT", new FullyQualifiedJavaType(Double.class.getName())));
		typeMap.put(Types.INTEGER, new JdbcTypeInformation("INTEGER", new FullyQualifiedJavaType(Integer.class.getName())));
		typeMap.put(Types.JAVA_OBJECT, new JdbcTypeInformation("JAVA_OBJECT", new FullyQualifiedJavaType(Object.class.getName())));
		typeMap.put(Types.LONGNVARCHAR, new JdbcTypeInformation("LONGNVARCHAR", new FullyQualifiedJavaType(String.class.getName())));
		typeMap.put(Types.LONGVARBINARY, new JdbcTypeInformation("LONGVARBINARY", new FullyQualifiedJavaType("byte[]")));
		typeMap.put(Types.LONGVARCHAR, new JdbcTypeInformation("LONGVARCHAR", new FullyQualifiedJavaType(String.class.getName())));
		typeMap.put(Types.NCHAR, new JdbcTypeInformation("NCHAR", new FullyQualifiedJavaType(String.class.getName())));
		typeMap.put(Types.NCLOB, new JdbcTypeInformation("NCLOB", new FullyQualifiedJavaType(String.class.getName())));
		typeMap.put(Types.NVARCHAR, new JdbcTypeInformation("NVARCHAR", new FullyQualifiedJavaType(String.class.getName())));
		typeMap.put(Types.NULL, new JdbcTypeInformation("NULL", new FullyQualifiedJavaType(Object.class.getName())));
		typeMap.put(Types.NUMERIC, new JdbcTypeInformation("NUMERIC", new FullyQualifiedJavaType(BigDecimal.class.getName())));
		typeMap.put(Types.OTHER, new JdbcTypeInformation("OTHER", new FullyQualifiedJavaType(Object.class.getName())));
		typeMap.put(Types.REAL, new JdbcTypeInformation("REAL", new FullyQualifiedJavaType(Float.class.getName())));
		typeMap.put(Types.REF, new JdbcTypeInformation("REF", new FullyQualifiedJavaType(Object.class.getName())));
		typeMap.put(Types.SMALLINT, new JdbcTypeInformation("SMALLINT", new FullyQualifiedJavaType(Short.class.getName())));
		typeMap.put(Types.STRUCT, new JdbcTypeInformation("STRUCT", new FullyQualifiedJavaType(Object.class.getName())));
		typeMap.put(Types.TIME, new JdbcTypeInformation("TIME", new FullyQualifiedJavaType(Date.class.getName())));
		typeMap.put(Types.TIMESTAMP, new JdbcTypeInformation("TIMESTAMP", new FullyQualifiedJavaType(Date.class.getName())));
		typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Byte.class.getName())));
		typeMap.put(Types.VARBINARY, new JdbcTypeInformation("VARBINARY", new FullyQualifiedJavaType("byte[]")));
		typeMap.put(Types.VARCHAR, new JdbcTypeInformation("VARCHAR", new FullyQualifiedJavaType(String.class.getName())));
		// JDK 1.8 types
		typeMap.put(TIME_WITH_TIMEZONE, new JdbcTypeInformation("TIME_WITH_TIMEZONE", new FullyQualifiedJavaType("java.time.OffsetTime")));
		typeMap.put(TIMESTAMP_WITH_TIMEZONE, new JdbcTypeInformation("TIMESTAMP_WITH_TIMEZONE", new FullyQualifiedJavaType("java.time.OffsetDateTime")));
	}

	@Override
	public void addConfigurationProperties(Properties properties) {
		this.properties.putAll(properties);
		forceBigDecimals = StringUtility.isTrue(properties.getProperty(PropertyRegistry.TYPE_RESOLVER_FORCE_BIG_DECIMALS));
		useJSR310Types = StringUtility.isTrue(properties.getProperty(PropertyRegistry.TYPE_RESOLVER_USE_JSR310_TYPES));
	}

	@Override
	public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
		FullyQualifiedJavaType answer = null;
		JdbcTypeInformation jdbcTypeInformation = typeMap.get(introspectedColumn.getJdbcType());

		if (jdbcTypeInformation != null) {
			answer = jdbcTypeInformation.getFullyQualifiedJavaType();
			answer = overrideDefaultType(introspectedColumn, answer);
		}

		return answer;
	}

	protected FullyQualifiedJavaType overrideDefaultType(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
		FullyQualifiedJavaType answer = defaultType;

		switch (column.getJdbcType()) {
		case Types.BIT:
			answer = calculateBitReplacement(column, defaultType);
			break;
		case Types.DATE:
			answer = calculateDateType(column, defaultType);
			break;
		case Types.DECIMAL:
		case Types.NUMERIC:
			answer = calculateBigDecimalReplacement(column, defaultType);
			break;
		case Types.TIME:
			answer = calculateTimeType(column, defaultType);
			break;
		case Types.TIMESTAMP:
			answer = calculateTimestampType(column, defaultType);
			break;
		default:
			break;
		}

		return answer;
	}

	protected FullyQualifiedJavaType calculateDateType(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
		FullyQualifiedJavaType answer;

		if (useJSR310Types) {
			answer = new FullyQualifiedJavaType("java.time.LocalDate");
		} else {
			answer = defaultType;
		}

		return answer;
	}

	protected FullyQualifiedJavaType calculateTimeType(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
		FullyQualifiedJavaType answer;

		if (useJSR310Types) {
			answer = new FullyQualifiedJavaType("java.time.LocalTime");
		} else {
			answer = defaultType;
		}

		return answer;
	}

	protected FullyQualifiedJavaType calculateTimestampType(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
		FullyQualifiedJavaType answer;

		if (useJSR310Types) {
			answer = new FullyQualifiedJavaType("java.time.LocalDateTime");
		} else {
			answer = defaultType;
		}

		return answer;
	}

	protected FullyQualifiedJavaType calculateBitReplacement(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
		FullyQualifiedJavaType answer;

		if (column.getLength() > 1) {
			answer = new FullyQualifiedJavaType("byte[]");
		} else {
			answer = defaultType;
		}

		return answer;
	}

	protected FullyQualifiedJavaType calculateBigDecimalReplacement(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
		FullyQualifiedJavaType answer;

		if (column.getScale() > 0 || column.getLength() > 18 || forceBigDecimals) {
			answer = defaultType;
		} else if (column.getLength() > 9) {
			answer = new FullyQualifiedJavaType(Long.class.getName());
		} else if (column.getLength() > 4) {
			answer = new FullyQualifiedJavaType(Integer.class.getName());
		} else {
			answer = new FullyQualifiedJavaType(Short.class.getName());
		}

		return answer;
	}

	@Override
	public String calculateJdbcTypeName(IntrospectedColumn introspectedColumn) {
		String answer = null;
		JdbcTypeInformation jdbcTypeInformation = typeMap.get(introspectedColumn.getJdbcType());

		if (jdbcTypeInformation != null) {
			answer = jdbcTypeInformation.getJdbcTypeName();
		}

		return answer;
	}

	@Override
	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}

	@Override
	public void setContext(Context context) {
		this.context = context;
	}

	public static class JdbcTypeInformation {
		private String jdbcTypeName;

		private FullyQualifiedJavaType fullyQualifiedJavaType;

		public JdbcTypeInformation(String jdbcTypeName, FullyQualifiedJavaType fullyQualifiedJavaType) {
			this.jdbcTypeName = jdbcTypeName;
			this.fullyQualifiedJavaType = fullyQualifiedJavaType;
		}

		public String getJdbcTypeName() {
			return jdbcTypeName;
		}

		public FullyQualifiedJavaType getFullyQualifiedJavaType() {
			return fullyQualifiedJavaType;
		}
	}
}
