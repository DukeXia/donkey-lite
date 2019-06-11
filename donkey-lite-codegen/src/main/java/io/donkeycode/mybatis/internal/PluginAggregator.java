package io.donkeycode.mybatis.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import io.donkeycode.mybatis.api.GeneratedJavaFile;
import io.donkeycode.mybatis.api.GeneratedXmlFile;
import io.donkeycode.mybatis.api.IntrospectedColumn;
import io.donkeycode.mybatis.api.IntrospectedTable;
import io.donkeycode.mybatis.api.Plugin;
import io.donkeycode.mybatis.api.dom.java.Field;
import io.donkeycode.mybatis.api.dom.java.Interface;
import io.donkeycode.mybatis.api.dom.java.Method;
import io.donkeycode.mybatis.api.dom.java.TopLevelClass;
import io.donkeycode.mybatis.api.dom.xml.Document;
import io.donkeycode.mybatis.config.Context;

/**
 * This class is for internal use only. It contains a list of plugins for the
 * current context and is used to aggregate plugins together. This class
 * implements the rule that if any plugin returns "false" from a method, then no
 * other plugin is called.
 *
 * <p>This class does not follow the normal plugin lifecycle and should not be
 * subclassed by clients.
 *
 * @author Jeff Butler
 *
 */
public final class PluginAggregator implements Plugin {
	private List<Plugin> plugins;

	public PluginAggregator() {
		plugins = new ArrayList<Plugin>();
	}

	public void addPlugin(Plugin plugin) {
		plugins.add(plugin);
	}

	@Override
	public void setContext(Context context) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setProperties(Properties properties) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean validate(List<String> warnings) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass tlc, IntrospectedTable introspectedTable) {
		boolean rc = true;

		for (Plugin plugin : plugins) {
			if (!plugin.modelBaseRecordClassGenerated(tlc, introspectedTable)) {
				rc = false;
				break;
			}
		}

		return rc;
	}

	@Override
	public boolean modelExampleClassGenerated(TopLevelClass tlc, IntrospectedTable introspectedTable) {
		boolean rc = true;

		for (Plugin plugin : plugins) {
			if (!plugin.modelExampleClassGenerated(tlc, introspectedTable)) {
				rc = false;
				break;
			}
		}

		return rc;
	}

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		List<GeneratedJavaFile> answer = new ArrayList<GeneratedJavaFile>();
		for (Plugin plugin : plugins) {
			List<GeneratedJavaFile> temp = plugin.contextGenerateAdditionalJavaFiles(introspectedTable);
			if (temp != null) {
				answer.addAll(temp);
			}
		}
		return answer;
	}

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
		List<GeneratedJavaFile> answer = new ArrayList<GeneratedJavaFile>();
		for (Plugin plugin : plugins) {
			List<GeneratedJavaFile> temp = plugin.contextGenerateAdditionalJavaFiles();
			if (temp != null) {
				answer.addAll(temp);
			}
		}
		return answer;
	}

	@Override
	public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
		List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>();
		for (Plugin plugin : plugins) {
			List<GeneratedXmlFile> temp = plugin.contextGenerateAdditionalXmlFiles(introspectedTable);
			if (temp != null) {
				answer.addAll(temp);
			}
		}
		return answer;
	}

	@Override
	public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {
		List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>();
		for (Plugin plugin : plugins) {
			List<GeneratedXmlFile> temp = plugin.contextGenerateAdditionalXmlFiles();
			if (temp != null) {
				answer.addAll(temp);
			}
		}
		return answer;
	}

	@Override
	public boolean modelPrimaryKeyClassGenerated(TopLevelClass tlc, IntrospectedTable introspectedTable) {
		boolean rc = true;

		for (Plugin plugin : plugins) {
			if (!plugin.modelPrimaryKeyClassGenerated(tlc, introspectedTable)) {
				rc = false;
				break;
			}
		}

		return rc;
	}

	@Override
	public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
		boolean rc = true;

		for (Plugin plugin : plugins) {
			if (!plugin.sqlMapGenerated(sqlMap, introspectedTable)) {
				rc = false;
				break;
			}
		}

		return rc;
	}

	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		boolean rc = true;

		for (Plugin plugin : plugins) {
			if (!plugin.clientGenerated(interfaze, topLevelClass, introspectedTable)) {
				rc = false;
				break;
			}
		}

		return rc;
	}

	@Override
	public boolean clientSelectAllMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		boolean rc = true;

		for (Plugin plugin : plugins) {
			if (!plugin.clientSelectAllMethodGenerated(method, topLevelClass, introspectedTable)) {
				rc = false;
				break;
			}
		}

		return rc;
	}

	@Override
	public boolean clientSelectAllMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
		boolean rc = true;

		for (Plugin plugin : plugins) {
			if (!plugin.clientSelectAllMethodGenerated(method, interfaze, introspectedTable)) {
				rc = false;
				break;
			}
		}

		return rc;
	}

	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
		boolean rc = true;

		for (Plugin plugin : plugins) {
			if (!plugin.sqlMapDocumentGenerated(document, introspectedTable)) {
				rc = false;
				break;
			}
		}

		return rc;
	}

	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, Plugin.ModelClassType modelClassType) {
		boolean rc = true;

		for (Plugin plugin : plugins) {
			if (!plugin.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType)) {
				rc = false;
				break;
			}
		}

		return rc;
	}

	@Override
	public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, Plugin.ModelClassType modelClassType) {
		boolean rc = true;

		for (Plugin plugin : plugins) {
			if (!plugin.modelGetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable, modelClassType)) {
				rc = false;
				break;
			}
		}

		return rc;
	}

	@Override
	public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, Plugin.ModelClassType modelClassType) {
		boolean rc = true;

		for (Plugin plugin : plugins) {
			if (!plugin.modelSetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable, modelClassType)) {
				rc = false;
				break;
			}
		}

		return rc;
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		for (Plugin plugin : plugins) {
			plugin.initialized(introspectedTable);
		}
	}

	@Override
	public boolean providerGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		boolean rc = true;

		for (Plugin plugin : plugins) {
			if (!plugin.providerGenerated(topLevelClass, introspectedTable)) {
				rc = false;
				break;
			}
		}

		return rc;
	}
}
