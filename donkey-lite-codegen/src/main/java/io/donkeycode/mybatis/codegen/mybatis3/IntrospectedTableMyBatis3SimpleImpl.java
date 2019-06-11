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
package io.donkeycode.mybatis.codegen.mybatis3;

import java.util.List;

import io.donkeycode.mybatis.api.ProgressCallback;
import io.donkeycode.mybatis.codegen.AbstractJavaClientGenerator;
import io.donkeycode.mybatis.codegen.AbstractJavaGenerator;
import io.donkeycode.mybatis.codegen.mybatis3.javamapper.SimpleAnnotatedClientGenerator;
import io.donkeycode.mybatis.codegen.mybatis3.javamapper.SimpleJavaClientGenerator;
import io.donkeycode.mybatis.codegen.mybatis3.model.SimpleModelGenerator;
import io.donkeycode.mybatis.codegen.mybatis3.xmlmapper.SimpleXMLMapperGenerator;
import io.donkeycode.mybatis.internal.ObjectFactory;

/**
 *
 * @author Jeff Butler
 *
 */
public class IntrospectedTableMyBatis3SimpleImpl extends IntrospectedTableMyBatis3Impl {
	public IntrospectedTableMyBatis3SimpleImpl() {
		super();
	}

	@Override
	protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator, List<String> warnings, ProgressCallback progressCallback) {
		if (javaClientGenerator == null) {
			if (context.getSqlMapGeneratorConfiguration() != null) {
				xmlMapperGenerator = new SimpleXMLMapperGenerator();
			}
		} else {
			xmlMapperGenerator = javaClientGenerator.getMatchedXMLGenerator();
		}

		initializeAbstractGenerator(xmlMapperGenerator, warnings, progressCallback);
	}

	@Override
	protected AbstractJavaClientGenerator createJavaClientGenerator() {
		if (context.getJavaClientGeneratorConfiguration() == null) {
			return null;
		}

		String type = context.getJavaClientGeneratorConfiguration().getConfigurationType();

		AbstractJavaClientGenerator javaGenerator;
		if ("XMLMAPPER".equalsIgnoreCase(type)) {
			javaGenerator = new SimpleJavaClientGenerator();
		} else if ("ANNOTATEDMAPPER".equalsIgnoreCase(type)) {
			javaGenerator = new SimpleAnnotatedClientGenerator();
		} else if ("MAPPER".equalsIgnoreCase(type)) {
			javaGenerator = new SimpleJavaClientGenerator();
		} else {
			javaGenerator = (AbstractJavaClientGenerator) ObjectFactory.createInternalObject(type);
		}

		return javaGenerator;
	}

	@Override
	protected void calculateJavaModelGenerators(List<String> warnings, ProgressCallback progressCallback) {

		AbstractJavaGenerator javaGenerator = new SimpleModelGenerator();
		initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
		javaModelGenerators.add(javaGenerator);
	}
}
