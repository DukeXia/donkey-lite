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
package org.donkeycode.codegen.generator.mybatis3.javamapper;

import java.util.List;

import org.donkeycode.codegen.api.dom.java.CompilationUnit;
import org.donkeycode.codegen.config.PropertyRegistry;
import org.donkeycode.codegen.generator.AbstractXmlGenerator;
import org.donkeycode.codegen.internal.util.StringUtility;

public class AnnotatedClientGenerator extends JavaMapperGenerator {

	public AnnotatedClientGenerator() {
		super(false);
	}

	@Override
	public List<CompilationUnit> getExtraCompilationUnits() {
		boolean useLegacyBuilder = false;

		String prop = context.getJavaClientGeneratorConfiguration().getProperty(PropertyRegistry.CLIENT_USE_LEGACY_BUILDER);
		if (StringUtility.stringHasValue(prop)) {
			useLegacyBuilder = Boolean.valueOf(prop);
		}
		SqlProviderGenerator sqlProviderGenerator = new SqlProviderGenerator(useLegacyBuilder);
		sqlProviderGenerator.setContext(context);
		sqlProviderGenerator.setIntrospectedTable(introspectedTable);
		sqlProviderGenerator.setProgressCallback(progressCallback);
		sqlProviderGenerator.setWarnings(warnings);
		return sqlProviderGenerator.getCompilationUnits();
	}

	@Override
	public AbstractXmlGenerator getMatchedXMLGenerator() {
		// No XML required by the annotated client
		return null;
	}
}
