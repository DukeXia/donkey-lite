package org.donkeycode.codegen.config;

import static org.donkeycode.codegen.internal.util.StringUtility.stringContainsSpace;
import static org.donkeycode.codegen.internal.util.StringUtility.stringHasValue;
import static org.donkeycode.codegen.internal.util.messages.Messages.getString;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.donkeycode.codegen.api.dom.xml.Attribute;
import org.donkeycode.codegen.api.dom.xml.XmlElement;

/**
 * The Class ColumnOverride.
 *
 * @author Jeff Butler
 */
@Setter
@Getter
public class ColumnOverride extends PropertyHolder {

	/**
	 * The column name.
	 */
	private String columnName;

	/**
	 * The java property.
	 */
	private String javaProperty;

	/**
	 * The jdbc type.
	 */
	private String jdbcType;

	/**
	 * The java type.
	 */
	private String javaType;

	/**
	 * The type handler.
	 */
	private String typeHandler;

	/**
	 * The is column name delimited.
	 */
	private boolean isColumnNameDelimited;

	/**
	 * The configured delimited column name.
	 */
	private String configuredDelimitedColumnName;

	/**
	 * If true, the column is a GENERATED ALWAYS column which means
	 * that it should not be used in insert or update statements.
	 */
	private boolean isGeneratedAlways;

	/**
	 * Instantiates a new column override.
	 *
	 * @param columnName the column name
	 */
	public ColumnOverride(String columnName) {
		super();
		this.columnName = columnName;
		isColumnNameDelimited = stringContainsSpace(columnName);
	}

	/**
	 * To xml element.
	 *
	 * @return the xml element
	 */
	public XmlElement toXmlElement() {
		XmlElement xmlElement = new XmlElement("columnOverride");
		xmlElement.addAttribute(new Attribute("column", columnName));

		if (stringHasValue(javaProperty)) {
			xmlElement.addAttribute(new Attribute("property", javaProperty));
		}

		if (stringHasValue(javaType)) {
			xmlElement.addAttribute(new Attribute("javaType", javaType));
		}

		if (stringHasValue(jdbcType)) {
			xmlElement.addAttribute(new Attribute("jdbcType", jdbcType));
		}

		if (stringHasValue(typeHandler)) {
			xmlElement.addAttribute(new Attribute("typeHandler", typeHandler));
		}

		if (stringHasValue(configuredDelimitedColumnName)) {
			xmlElement.addAttribute(new Attribute("delimitedColumnName", configuredDelimitedColumnName));
		}

		addPropertyXmlElements(xmlElement);
		return xmlElement;
	}

	/**
	 * Checks if is column name delimited.
	 *
	 * @return true, if is column name delimited
	 */
	public boolean isColumnNameDelimited() {
		return isColumnNameDelimited;
	}

	/**
	 * Sets the column name delimited.
	 *
	 * @param isColumnNameDelimited the new column name delimited
	 */
	public void setColumnNameDelimited(boolean isColumnNameDelimited) {
		this.isColumnNameDelimited = isColumnNameDelimited;
		configuredDelimitedColumnName = isColumnNameDelimited ? "true" : "false";
	}

	/**
	 * Validate.
	 *
	 * @param errors    the errors
	 * @param tableName the table name
	 */
	public void validate(List<String> errors, String tableName) {
		if (!stringHasValue(columnName)) {
			errors.add(getString("ValidationError.22", tableName));
		}
	}
}
