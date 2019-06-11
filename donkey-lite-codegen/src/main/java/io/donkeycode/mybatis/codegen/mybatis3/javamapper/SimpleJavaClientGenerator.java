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
package io.donkeycode.mybatis.codegen.mybatis3.javamapper;

import static io.donkeycode.mybatis.internal.util.StringUtility.stringHasValue;
import static io.donkeycode.mybatis.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import io.donkeycode.mybatis.api.CommentGenerator;
import io.donkeycode.mybatis.api.dom.java.CompilationUnit;
import io.donkeycode.mybatis.api.dom.java.FullyQualifiedJavaType;
import io.donkeycode.mybatis.api.dom.java.Interface;
import io.donkeycode.mybatis.api.dom.java.JavaVisibility;
import io.donkeycode.mybatis.codegen.AbstractJavaClientGenerator;
import io.donkeycode.mybatis.codegen.AbstractXmlGenerator;
import io.donkeycode.mybatis.codegen.mybatis3.xmlmapper.SimpleXMLMapperGenerator;
import io.donkeycode.mybatis.config.PropertyRegistry;

/**
 * @author Jeff Butler
 *
 */
public class SimpleJavaClientGenerator extends AbstractJavaClientGenerator {

	public SimpleJavaClientGenerator() {
		super(true);
	}

	public SimpleJavaClientGenerator(boolean requiresMatchedXMLGenerator) {
		super(requiresMatchedXMLGenerator);
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		progressCallback.startTask(getString("Progress.17", introspectedTable.getFullyQualifiedTable().toString()));
		CommentGenerator commentGenerator = context.getCommentGenerator();

		FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
		Interface interfaze = new Interface(type);
		interfaze.setVisibility(JavaVisibility.PUBLIC);
		commentGenerator.addJavaFileComment(interfaze);

		String rootInterface = introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
		if (!stringHasValue(rootInterface)) {
			rootInterface = context.getJavaClientGeneratorConfiguration().getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
		}

		if (stringHasValue(rootInterface)) {
			FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
			interfaze.addSuperInterface(fqjt);
			interfaze.addImportedType(fqjt);
		}

		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
		if (context.getPlugins().clientGenerated(interfaze, null, introspectedTable)) {
			answer.add(interfaze);
		}

		List<CompilationUnit> extraCompilationUnits = getExtraCompilationUnits();
		if (extraCompilationUnits != null) {
			answer.addAll(extraCompilationUnits);
		}

		return answer;
	}

	public List<CompilationUnit> getExtraCompilationUnits() {
		return null;
	}

	@Override
	public AbstractXmlGenerator getMatchedXMLGenerator() {
		return new SimpleXMLMapperGenerator();
	}
}
