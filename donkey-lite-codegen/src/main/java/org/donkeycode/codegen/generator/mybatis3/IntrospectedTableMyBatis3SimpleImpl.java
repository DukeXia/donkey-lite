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
package org.donkeycode.codegen.generator.mybatis3;

import java.util.List;

import org.donkeycode.codegen.api.ProgressCallback;
import org.donkeycode.codegen.generator.AbstractJavaClientGenerator;
import org.donkeycode.codegen.generator.AbstractJavaGenerator;
import org.donkeycode.codegen.generator.mybatis3.javamapper.AnnotatedClientGenerator;
import org.donkeycode.codegen.generator.mybatis3.javamapper.SimpleJavaClientGenerator;
import org.donkeycode.codegen.generator.mybatis3.model.SimpleModelGenerator;
import org.donkeycode.codegen.internal.ObjectFactory;

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
		}  else if ("MAPPER".equalsIgnoreCase(type)) {
			javaGenerator = new SimpleJavaClientGenerator();
		} else if ("ANNOTATEDMAPPER".equalsIgnoreCase(type)) {
			javaGenerator = new AnnotatedClientGenerator();
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
