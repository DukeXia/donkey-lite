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
package org.donkeycode.codegen.generator.mybatis3.model;

import static org.donkeycode.codegen.internal.util.JavaBeansUtil.getJavaBeansField;
import static org.donkeycode.codegen.internal.util.JavaBeansUtil.getJavaBeansGetter;
import static org.donkeycode.codegen.internal.util.JavaBeansUtil.getJavaBeansSetter;
import static org.donkeycode.codegen.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.donkeycode.codegen.api.CommentGenerator;
import org.donkeycode.codegen.api.FullyQualifiedTable;
import org.donkeycode.codegen.api.IntrospectedColumn;
import org.donkeycode.codegen.api.Plugin;
import org.donkeycode.codegen.api.dom.java.CompilationUnit;
import org.donkeycode.codegen.api.dom.java.Field;
import org.donkeycode.codegen.api.dom.java.FullyQualifiedJavaType;
import org.donkeycode.codegen.api.dom.java.JavaVisibility;
import org.donkeycode.codegen.api.dom.java.Method;
import org.donkeycode.codegen.api.dom.java.Parameter;
import org.donkeycode.codegen.api.dom.java.TopLevelClass;
import org.donkeycode.codegen.generator.AbstractJavaGenerator;
import org.donkeycode.codegen.generator.RootClassInfo;

/**
 *
 * @author Jeff Butler
 *
 */
public class PrimaryKeyGenerator extends AbstractJavaGenerator {

	public PrimaryKeyGenerator() {
		super();
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
		progressCallback.startTask(getString("Progress.7", table.toString()));
		Plugin plugins = context.getPlugins();
		CommentGenerator commentGenerator = context.getCommentGenerator();

		TopLevelClass topLevelClass = new TopLevelClass(introspectedTable.getPrimaryKeyType());
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		commentGenerator.addJavaFileComment(topLevelClass);

		String rootClass = getRootClass();
		if (rootClass != null) {
			topLevelClass.setSuperClass(new FullyQualifiedJavaType(rootClass));
			topLevelClass.addImportedType(topLevelClass.getSuperClass());
		}

		if (introspectedTable.isConstructorBased()) {
			addParameterizedConstructor(topLevelClass);

			if (!introspectedTable.isImmutable()) {
				addDefaultConstructor(topLevelClass);
			}
		}

		commentGenerator.addModelClassComment(topLevelClass, introspectedTable);

		for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
			if (RootClassInfo.getInstance(rootClass, warnings).containsProperty(introspectedColumn)) {
				continue;
			}

			Field field = getJavaBeansField(introspectedColumn, context, introspectedTable);
			if (plugins.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, Plugin.ModelClassType.PRIMARY_KEY)) {
				topLevelClass.addField(field);
				topLevelClass.addImportedType(field.getType());
			}

			Method method = getJavaBeansGetter(introspectedColumn, context, introspectedTable);
			if (plugins.modelGetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable, Plugin.ModelClassType.PRIMARY_KEY)) {
				topLevelClass.addMethod(method);
			}

			if (!introspectedTable.isImmutable()) {
				method = getJavaBeansSetter(introspectedColumn, context, introspectedTable);
				if (plugins.modelSetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable, Plugin.ModelClassType.PRIMARY_KEY)) {
					topLevelClass.addMethod(method);
				}
			}
		}

		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
		if (context.getPlugins().modelPrimaryKeyClassGenerated(topLevelClass, introspectedTable)) {
			answer.add(topLevelClass);
		}
		return answer;
	}

	private void addParameterizedConstructor(TopLevelClass topLevelClass) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setConstructor(true);
		method.setName(topLevelClass.getType().getShortName());
		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

		StringBuilder sb = new StringBuilder();
		for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
			method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), introspectedColumn.getJavaProperty()));
			sb.setLength(0);
			sb.append("this.");
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(" = ");
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(';');
			method.addBodyLine(sb.toString());
		}

		topLevelClass.addMethod(method);
	}
}
