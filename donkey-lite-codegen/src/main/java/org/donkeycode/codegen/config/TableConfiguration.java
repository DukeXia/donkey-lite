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
package org.donkeycode.codegen.config;

import static org.donkeycode.codegen.internal.util.EqualsUtil.areEqual;
import static org.donkeycode.codegen.internal.util.HashCodeUtil.SEED;
import static org.donkeycode.codegen.internal.util.HashCodeUtil.hash;
import static org.donkeycode.codegen.internal.util.StringUtility.composeFullyQualifiedTableName;
import static org.donkeycode.codegen.internal.util.StringUtility.stringHasValue;
import static org.donkeycode.codegen.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.donkeycode.codegen.api.dom.xml.Attribute;
import org.donkeycode.codegen.api.dom.xml.XmlElement;

/**
 * The Class TableConfiguration.
 *
 * @author Jeff Butler
 */
public class TableConfiguration extends PropertyHolder {

	private List<ColumnOverride> columnOverrides;
	private Map<IgnoredColumn, Boolean> ignoredColumns;
	private GeneratedKey generatedKey;
	private String catalog;
	private String schema;
	private String tableName;
	private String domainObjectName;
	private String alias;
	private ModelType modelType;
	private boolean wildcardEscapingEnabled;
	private String configuredModelType;
	private boolean delimitIdentifiers;
	private DomainObjectRenamingRule domainObjectRenamingRule;
	private ColumnRenamingRule columnRenamingRule;
	private boolean isAllColumnDelimitingEnabled;

	private String mapperName;
	private String sqlProviderName;


	public TableConfiguration(Context context) {
		super();

		this.modelType = context.getDefaultModelType();

		columnOverrides = new ArrayList<ColumnOverride>();
		ignoredColumns = new HashMap<IgnoredColumn, Boolean>();
	}

	public boolean isColumnIgnored(String columnName) {
		for (Map.Entry<IgnoredColumn, Boolean> entry : ignoredColumns.entrySet()) {
			if (entry.getKey().matches(columnName)) {
				entry.setValue(Boolean.TRUE);
				return true;
			}
		}


		return false;
	}

	public void addIgnoredColumn(IgnoredColumn ignoredColumn) {
		ignoredColumns.put(ignoredColumn, Boolean.FALSE);
	}



	public void addColumnOverride(ColumnOverride columnOverride) {
		columnOverrides.add(columnOverride);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TableConfiguration)) {
			return false;
		}

		TableConfiguration other = (TableConfiguration) obj;

		return areEqual(this.catalog, other.catalog) && areEqual(this.schema, other.schema) && areEqual(this.tableName, other.tableName);
	}

	@Override
	public int hashCode() {
		int result = SEED;
		result = hash(result, catalog);
		result = hash(result, schema);
		result = hash(result, tableName);

		return result;
	}

	/**
	 * May return null if the column has not been overridden.
	 *
	 * @param columnName
	 *            the column name
	 * @return the column override (if any) related to this column
	 */
	public ColumnOverride getColumnOverride(String columnName) {
		for (ColumnOverride co : columnOverrides) {
			if (co.isColumnNameDelimited()) {
				if (columnName.equals(co.getColumnName())) {
					return co;
				}
			} else {
				if (columnName.equalsIgnoreCase(co.getColumnName())) {
					return co;
				}
			}
		}

		return null;
	}

	public GeneratedKey getGeneratedKey() {
		return generatedKey;
	}

	public void setGeneratedKey(GeneratedKey generatedKey) {
		this.generatedKey = generatedKey;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getDomainObjectName() {
		return domainObjectName;
	}

	public void setDomainObjectName(String domainObjectName) {
		this.domainObjectName = domainObjectName;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<ColumnOverride> getColumnOverrides() {
		return columnOverrides;
	}

	/**
	 * Returns a List of Strings. The values are the columns
	 * that were specified to be ignored in the table, but do not exist in the
	 * table.
	 *
	 * @return a List of Strings - the columns that were improperly configured
	 *         as ignored columns
	 */
	public List<String> getIgnoredColumnsInError() {
		List<String> answer = new ArrayList<String>();

		for (Map.Entry<IgnoredColumn, Boolean> entry : ignoredColumns.entrySet()) {
			if (Boolean.FALSE.equals(entry.getValue())) {
				answer.add(entry.getKey().getColumnName());
			}
		}

		return answer;
	}

	public ModelType getModelType() {
		return modelType;
	}

	public void setConfiguredModelType(String configuredModelType) {
		this.configuredModelType = configuredModelType;
		this.modelType = ModelType.getModelType(configuredModelType);
	}

	public boolean isWildcardEscapingEnabled() {
		return wildcardEscapingEnabled;
	}

	public void setWildcardEscapingEnabled(boolean wildcardEscapingEnabled) {
		this.wildcardEscapingEnabled = wildcardEscapingEnabled;
	}

	public XmlElement toXmlElement() {
		XmlElement xmlElement = new XmlElement("table");
		xmlElement.addAttribute(new Attribute("tableName", tableName));

		if (stringHasValue(catalog)) {
			xmlElement.addAttribute(new Attribute("catalog", catalog));
		}

		if (stringHasValue(schema)) {
			xmlElement.addAttribute(new Attribute("schema", schema));
		}

		if (stringHasValue(alias)) {
			xmlElement.addAttribute(new Attribute("alias", alias));
		}

		if (stringHasValue(domainObjectName)) {
			xmlElement.addAttribute(new Attribute("domainObjectName", domainObjectName));
		}

		if (configuredModelType != null) {
			xmlElement.addAttribute(new Attribute("modelType", configuredModelType));
		}

		if (wildcardEscapingEnabled) {
			xmlElement.addAttribute(new Attribute("escapeWildcards", "true"));
		}

		if (isAllColumnDelimitingEnabled) {
			xmlElement.addAttribute(new Attribute("delimitAllColumns", "true"));
		}

		if (delimitIdentifiers) {
			xmlElement.addAttribute(new Attribute("delimitIdentifiers", "true"));
		}

		if (stringHasValue(mapperName)) {
			xmlElement.addAttribute(new Attribute("mapperName", mapperName));
		}

		if (stringHasValue(sqlProviderName)) {
			xmlElement.addAttribute(new Attribute("sqlProviderName", sqlProviderName));
		}

		addPropertyXmlElements(xmlElement);

		if (generatedKey != null) {
			xmlElement.addElement(generatedKey.toXmlElement());
		}

		if (domainObjectRenamingRule != null) {
			xmlElement.addElement(domainObjectRenamingRule.toXmlElement());
		}

		if (columnRenamingRule != null) {
			xmlElement.addElement(columnRenamingRule.toXmlElement());
		}

		if (ignoredColumns.size() > 0) {
			for (IgnoredColumn ignoredColumn : ignoredColumns.keySet()) {
				xmlElement.addElement(ignoredColumn.toXmlElement());
			}
		}

	
		if (columnOverrides.size() > 0) {
			for (ColumnOverride columnOverride : columnOverrides) {
				xmlElement.addElement(columnOverride.toXmlElement());
			}
		}

		return xmlElement;
	}

	@Override
	public String toString() {
		return composeFullyQualifiedTableName(catalog, schema, tableName, '.');
	}

	public boolean isDelimitIdentifiers() {
		return delimitIdentifiers;
	}

	public void setDelimitIdentifiers(boolean delimitIdentifiers) {
		this.delimitIdentifiers = delimitIdentifiers;
	}

	public void validate(List<String> errors, int listPosition) {
		if (!stringHasValue(tableName)) {
			errors.add(getString("ValidationError.6", Integer.toString(listPosition)));
		}

		String fqTableName = composeFullyQualifiedTableName(catalog, schema, tableName, '.');

		if (generatedKey != null) {
			generatedKey.validate(errors, fqTableName);
		}

		// when using column indexes, either both or neither query ids
		// should be set

		if (domainObjectRenamingRule != null) {
			domainObjectRenamingRule.validate(errors, fqTableName);
		}

		if (columnRenamingRule != null) {
			columnRenamingRule.validate(errors, fqTableName);
		}

		for (ColumnOverride columnOverride : columnOverrides) {
			columnOverride.validate(errors, fqTableName);
		}

		for (IgnoredColumn ignoredColumn : ignoredColumns.keySet()) {
			ignoredColumn.validate(errors, fqTableName);
		}

	
	}

	public DomainObjectRenamingRule getDomainObjectRenamingRule() {
		return domainObjectRenamingRule;
	}

	public void setDomainObjectRenamingRule(DomainObjectRenamingRule domainObjectRenamingRule) {
		this.domainObjectRenamingRule = domainObjectRenamingRule;
	}

	public ColumnRenamingRule getColumnRenamingRule() {
		return columnRenamingRule;
	}

	public void setColumnRenamingRule(ColumnRenamingRule columnRenamingRule) {
		this.columnRenamingRule = columnRenamingRule;
	}

	public boolean isAllColumnDelimitingEnabled() {
		return isAllColumnDelimitingEnabled;
	}

	public void setAllColumnDelimitingEnabled(boolean isAllColumnDelimitingEnabled) {
		this.isAllColumnDelimitingEnabled = isAllColumnDelimitingEnabled;
	}

	public String getMapperName() {
		return mapperName;
	}

	public void setMapperName(String mapperName) {
		this.mapperName = mapperName;
	}

	public String getSqlProviderName() {
		return sqlProviderName;
	}

	public void setSqlProviderName(String sqlProviderName) {
		this.sqlProviderName = sqlProviderName;
	}
}
