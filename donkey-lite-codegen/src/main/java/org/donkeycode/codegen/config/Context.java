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

import static org.donkeycode.codegen.internal.util.StringUtility.composeFullyQualifiedTableName;
import static org.donkeycode.codegen.internal.util.StringUtility.isTrue;
import static org.donkeycode.codegen.internal.util.StringUtility.stringHasValue;
import static org.donkeycode.codegen.internal.util.messages.Messages.getString;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import org.donkeycode.codegen.api.CommentGenerator;
import org.donkeycode.codegen.api.ConnectionFactory;
import org.donkeycode.codegen.api.GeneratedJavaFile;
import org.donkeycode.codegen.api.GeneratedXmlFile;
import org.donkeycode.codegen.api.IntrospectedTable;
import org.donkeycode.codegen.api.JavaFormatter;
import org.donkeycode.codegen.api.JavaTypeResolver;
import org.donkeycode.codegen.api.Plugin;
import org.donkeycode.codegen.api.ProgressCallback;
import org.donkeycode.codegen.api.XmlFormatter;
import org.donkeycode.codegen.api.dom.xml.Attribute;
import org.donkeycode.codegen.api.dom.xml.XmlElement;
import org.donkeycode.codegen.internal.JDBCConnectionFactory;
import org.donkeycode.codegen.internal.ObjectFactory;
import org.donkeycode.codegen.internal.PluginAggregator;
import org.donkeycode.codegen.internal.db.DatabaseIntrospector;

/**
 * The Class Context.
 *
 * @author Jeff Butler
 */
@Setter
@Getter
public class Context extends PropertyHolder {

	private String id;

	private JDBCConnectionConfiguration jdbcConnectionConfiguration;

	private ConnectionFactoryConfiguration connectionFactoryConfiguration;

	private SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration;

	private JavaTypeResolverConfiguration javaTypeResolverConfiguration;

	private JavaModelGeneratorConfiguration javaModelGeneratorConfiguration;

	private JavaClientGeneratorConfiguration javaClientGeneratorConfiguration;

	private ArrayList<TableConfiguration> tableConfigurations;

	private ModelType defaultModelType;

	private String beginningDelimiter = "\"";

	private String endingDelimiter = "\"";

	private CommentGeneratorConfiguration commentGeneratorConfiguration;

	private CommentGenerator commentGenerator;

	private PluginAggregator pluginAggregator;

	private List<PluginConfiguration> pluginConfigurations;

	private String targetRuntime;

	private String introspectedColumnImpl;

	private Boolean autoDelimitKeywords;

	private JavaFormatter javaFormatter;

	private XmlFormatter xmlFormatter;

	public Context(ModelType defaultModelType) {
		super();

		if (defaultModelType == null) {
			this.defaultModelType = ModelType.CONDITIONAL;
		} else {
			this.defaultModelType = defaultModelType;
		}

		tableConfigurations = new ArrayList<TableConfiguration>();
		pluginConfigurations = new ArrayList<PluginConfiguration>();
	}

	public void addTableConfiguration(TableConfiguration tc) {
		tableConfigurations.add(tc);
	}

	public void addPluginConfiguration(PluginConfiguration pluginConfiguration) {
		pluginConfigurations.add(pluginConfiguration);
	}

	/**
	 * This method does a simple validate, it makes sure that all required fields have been filled in. It does not do
	 * any more complex operations such as validating that database tables exist or validating that named columns exist
	 *
	 * @param errors the errors
	 */
	public void validate(List<String> errors) {
		if (!stringHasValue(id)) {
			errors.add(getString("ValidationError.16"));
		}

		if (jdbcConnectionConfiguration == null && connectionFactoryConfiguration == null) {
			// must specify one
			errors.add(getString("ValidationError.10", id));
		} else if (jdbcConnectionConfiguration != null && connectionFactoryConfiguration != null) {
			// must not specify both
			errors.add(getString("ValidationError.10", id));
		} else if (jdbcConnectionConfiguration != null) {
			jdbcConnectionConfiguration.validate(errors);
		} else {
			connectionFactoryConfiguration.validate(errors);
		}

		if (javaModelGeneratorConfiguration == null) {
			errors.add(getString("ValidationError.8", id));
		} else {
			javaModelGeneratorConfiguration.validate(errors, id);
		}

		if (javaClientGeneratorConfiguration != null) {
			javaClientGeneratorConfiguration.validate(errors, id);
		}

		IntrospectedTable it = null;
		try {
			it = ObjectFactory.createIntrospectedTableForValidation(this);
		} catch (Exception e) {
			errors.add(getString("ValidationError.25", id));
		}

		if (it != null && it.requiresXMLGenerator()) {
			if (sqlMapGeneratorConfiguration == null) {
				errors.add(getString("ValidationError.9", id));
			} else {
				sqlMapGeneratorConfiguration.validate(errors, id);
			}
		}

		if (tableConfigurations.size() == 0) {
			errors.add(getString("ValidationError.3", id));
		} else {
			for (int i = 0; i < tableConfigurations.size(); i++) {
				TableConfiguration tc = tableConfigurations.get(i);

				tc.validate(errors, i);
			}
		}

		for (PluginConfiguration pluginConfiguration : pluginConfigurations) {
			pluginConfiguration.validate(errors, id);
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ModelType getDefaultModelType() {
		return defaultModelType;
	}

	/**
	 * Builds an XmlElement representation of this context. Note that the XML
	 * may not necessarily validate if the context is invalid. Call the
	 * <code>validate</code> method to check validity of this context.
	 *
	 * @return the XML representation of this context
	 */
	public XmlElement toXmlElement() {
		XmlElement xmlElement = new XmlElement("context");

		xmlElement.addAttribute(new Attribute("id", id));

		if (defaultModelType != ModelType.CONDITIONAL) {
			xmlElement.addAttribute(new Attribute("defaultModelType", defaultModelType.getModelType()));
		}

		if (stringHasValue(introspectedColumnImpl)) {
			xmlElement.addAttribute(new Attribute("introspectedColumnImpl", introspectedColumnImpl));
		}

		if (stringHasValue(targetRuntime)) {
			xmlElement.addAttribute(new Attribute("targetRuntime", targetRuntime));
		}

		addPropertyXmlElements(xmlElement);

		for (PluginConfiguration pluginConfiguration : pluginConfigurations) {
			xmlElement.addElement(pluginConfiguration.toXmlElement());
		}

		if (commentGeneratorConfiguration != null) {
			xmlElement.addElement(commentGeneratorConfiguration.toXmlElement());
		}

		if (jdbcConnectionConfiguration != null) {
			xmlElement.addElement(jdbcConnectionConfiguration.toXmlElement());
		}

		if (connectionFactoryConfiguration != null) {
			xmlElement.addElement(connectionFactoryConfiguration.toXmlElement());
		}

		if (javaTypeResolverConfiguration != null) {
			xmlElement.addElement(javaTypeResolverConfiguration.toXmlElement());
		}

		if (javaModelGeneratorConfiguration != null) {
			xmlElement.addElement(javaModelGeneratorConfiguration.toXmlElement());
		}

		if (sqlMapGeneratorConfiguration != null) {
			xmlElement.addElement(sqlMapGeneratorConfiguration.toXmlElement());
		}

		if (javaClientGeneratorConfiguration != null) {
			xmlElement.addElement(javaClientGeneratorConfiguration.toXmlElement());
		}

		for (TableConfiguration tableConfiguration : tableConfigurations) {
			xmlElement.addElement(tableConfiguration.toXmlElement());
		}

		return xmlElement;
	}

	public List<TableConfiguration> getTableConfigurations() {
		return tableConfigurations;
	}

	@Override
	public void addProperty(String name, String value) {
		super.addProperty(name, value);

		if (PropertyRegistry.CONTEXT_BEGINNING_DELIMITER.equals(name)) {
			beginningDelimiter = value;
		} else if (PropertyRegistry.CONTEXT_ENDING_DELIMITER.equals(name)) {
			endingDelimiter = value;
		} else if (PropertyRegistry.CONTEXT_AUTO_DELIMIT_KEYWORDS.equals(name) && stringHasValue(value)) {
			autoDelimitKeywords = isTrue(value);
		}
	}

	public CommentGenerator getCommentGenerator() {
		if (commentGenerator == null) {
			commentGenerator = ObjectFactory.createCommentGenerator(this);
		}

		return commentGenerator;
	}

	public JavaFormatter getJavaFormatter() {
		if (javaFormatter == null) {
			javaFormatter = ObjectFactory.createJavaFormatter(this);
		}

		return javaFormatter;
	}

	public XmlFormatter getXmlFormatter() {
		if (xmlFormatter == null) {
			xmlFormatter = ObjectFactory.createXmlFormatter(this);
		}

		return xmlFormatter;
	}

	// methods related to code generation.
	//
	// Methods should be called in this order:
	//
	// 1. getIntrospectionSteps()
	// 2. introspectTables()
	// 3. getGenerationSteps()
	// 4. generateFiles()
	//

	private List<IntrospectedTable> introspectedTables;

	public int getIntrospectionSteps() {
		int steps = 0;
		steps++;
		steps += tableConfigurations.size() * 1;
		return steps;
	}

	/**
	 * Introspect tables based on the configuration specified in the
	 * constructor. This method is long running.
	 *
	 * @param callback                 a progress callback if progress information is desired, or
	 *                                 <code>null</code>
	 * @param warnings                 any warning generated from this method will be added to the
	 *                                 List. Warnings are always Strings.
	 * @param fullyQualifiedTableNames a set of table names to generate. The elements of the set must
	 *                                 be Strings that exactly match what's specified in the
	 *                                 configuration. For example, if table name = "foo" and schema =
	 *                                 "bar", then the fully qualified table name is "foo.bar". If
	 *                                 the Set is null or empty, then all tables in the configuration
	 *                                 will be used for code generation.
	 * @throws SQLException         if some error arises while introspecting the specified
	 *                              database tables.
	 * @throws InterruptedException if the progress callback reports a cancel
	 */
	public void introspectTables(ProgressCallback callback, List<String> warnings, Set<String> fullyQualifiedTableNames) throws SQLException, InterruptedException {

		introspectedTables = new ArrayList<IntrospectedTable>();
		JavaTypeResolver javaTypeResolver = ObjectFactory.createJavaTypeResolver(this, warnings);

		Connection connection = null;

		try {
			callback.startTask(getString("Progress.0"));
			connection = getConnection();

			DatabaseIntrospector databaseIntrospector = new DatabaseIntrospector(this, connection.getMetaData(), javaTypeResolver, warnings);

			for (TableConfiguration tc : tableConfigurations) {
				String tableName = composeFullyQualifiedTableName(tc.getCatalog(), tc.getSchema(), tc.getTableName(), '.');

				if (fullyQualifiedTableNames != null && fullyQualifiedTableNames.size() > 0 && !fullyQualifiedTableNames.contains(tableName)) {
					continue;
				}

				callback.startTask(getString("Progress.1", tableName));
				List<IntrospectedTable> tables = databaseIntrospector.introspectTables(tc);

				if (tables != null) {
					introspectedTables.addAll(tables);
				}

				callback.checkCancel();
			}
		} finally {
			closeConnection(connection);
		}
	}

	public int getGenerationSteps() {
		int steps = 0;

		if (introspectedTables != null) {
			for (IntrospectedTable introspectedTable : introspectedTables) {
				steps += introspectedTable.getGenerationSteps();
			}
		}

		return steps;
	}

	public void generateFiles(ProgressCallback callback, List<GeneratedJavaFile> generatedJavaFiles, List<GeneratedXmlFile> generatedXmlFiles, List<String> warnings) throws InterruptedException {

		pluginAggregator = new PluginAggregator();
		for (PluginConfiguration pluginConfiguration : pluginConfigurations) {
			Plugin plugin = ObjectFactory.createPlugin(this, pluginConfiguration);
			if (plugin.validate(warnings)) {
				pluginAggregator.addPlugin(plugin);
			} else {
				warnings.add(getString("Warning.24", pluginConfiguration.getConfigurationType(), id));
			}
		}

		if (introspectedTables != null) {
			for (IntrospectedTable introspectedTable : introspectedTables) {
				callback.checkCancel();

				introspectedTable.initialize();
				introspectedTable.calculateGenerators(warnings, callback);
				generatedJavaFiles.addAll(introspectedTable.getGeneratedJavaFiles());
				generatedXmlFiles.addAll(introspectedTable.getGeneratedXmlFiles());

				generatedJavaFiles.addAll(pluginAggregator.contextGenerateAdditionalJavaFiles(introspectedTable));
				generatedXmlFiles.addAll(pluginAggregator.contextGenerateAdditionalXmlFiles(introspectedTable));
			}
		}

		generatedJavaFiles.addAll(pluginAggregator.contextGenerateAdditionalJavaFiles());
		generatedXmlFiles.addAll(pluginAggregator.contextGenerateAdditionalXmlFiles());
	}

	private Connection getConnection() throws SQLException {
		ConnectionFactory connectionFactory;
		if (jdbcConnectionConfiguration != null) {
			connectionFactory = new JDBCConnectionFactory(jdbcConnectionConfiguration);
		} else {
			connectionFactory = ObjectFactory.createConnectionFactory(this);
		}

		return connectionFactory.getConnection();
	}

	private void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// ignore
			}
		}
	}

	public boolean autoDelimitKeywords() {
		return autoDelimitKeywords != null && autoDelimitKeywords.booleanValue();
	}

	public Plugin getPlugins() {
		return pluginAggregator;
	}
}
