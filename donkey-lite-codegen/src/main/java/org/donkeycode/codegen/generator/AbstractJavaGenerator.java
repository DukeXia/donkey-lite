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
package org.donkeycode.codegen.generator;

import static org.donkeycode.codegen.internal.util.JavaBeansUtil.getGetterMethodName;

import java.util.List;
import java.util.Properties;

import org.donkeycode.codegen.api.dom.java.CompilationUnit;
import org.donkeycode.codegen.api.dom.java.Field;
import org.donkeycode.codegen.api.dom.java.JavaVisibility;
import org.donkeycode.codegen.api.dom.java.Method;
import org.donkeycode.codegen.api.dom.java.TopLevelClass;
import org.donkeycode.codegen.config.PropertyRegistry;

public abstract class AbstractJavaGenerator extends AbstractGenerator {

	public abstract List<CompilationUnit> getCompilationUnits();

	public static Method getGetter(Field field) {
		Method method = new Method();
		method.setName(getGetterMethodName(field.getName(), field.getType()));
		method.setReturnType(field.getType());
		method.setVisibility(JavaVisibility.PUBLIC);
		StringBuilder sb = new StringBuilder();
		sb.append("return ");
		sb.append(field.getName());
		sb.append(';');
		method.addBodyLine(sb.toString());
		return method;
	}

	public String getRootClass() {
		String rootClass = introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_CLASS);
		if (rootClass == null) {
			Properties properties = context.getJavaModelGeneratorConfiguration().getProperties();
			rootClass = properties.getProperty(PropertyRegistry.ANY_ROOT_CLASS);
		}

		return rootClass;
	}

	protected void addDefaultConstructor(TopLevelClass topLevelClass) {
		topLevelClass.addMethod(getDefaultConstructor(topLevelClass));
	}

	protected Method getDefaultConstructor(TopLevelClass topLevelClass) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setConstructor(true);
		method.setName(topLevelClass.getType().getShortName());
		method.addBodyLine("super();");
		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
		return method;
	}
}
