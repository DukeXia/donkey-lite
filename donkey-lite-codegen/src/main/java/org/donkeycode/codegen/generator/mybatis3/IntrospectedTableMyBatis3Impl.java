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

import java.util.ArrayList;
import java.util.List;

import org.donkeycode.codegen.api.GeneratedJavaFile;
import org.donkeycode.codegen.api.GeneratedXmlFile;
import org.donkeycode.codegen.api.IntrospectedTable;
import org.donkeycode.codegen.api.ProgressCallback;
import org.donkeycode.codegen.api.dom.java.CompilationUnit;
import org.donkeycode.codegen.api.dom.xml.Document;
import org.donkeycode.codegen.config.PropertyRegistry;
import org.donkeycode.codegen.generator.AbstractGenerator;
import org.donkeycode.codegen.generator.AbstractJavaClientGenerator;
import org.donkeycode.codegen.generator.AbstractJavaGenerator;
import org.donkeycode.codegen.generator.AbstractXmlGenerator;
import org.donkeycode.codegen.generator.mybatis3.javamapper.AnnotatedClientGenerator;
import org.donkeycode.codegen.generator.mybatis3.javamapper.JavaMapperGenerator;
import org.donkeycode.codegen.generator.mybatis3.model.PrimaryKeyGenerator;
import org.donkeycode.codegen.internal.ObjectFactory;

/**
 * @author Jeff Butler
 */
public class IntrospectedTableMyBatis3Impl extends IntrospectedTable {

	protected List<AbstractJavaGenerator> javaModelGenerators;

	protected List<AbstractJavaGenerator> clientGenerators;

	protected AbstractXmlGenerator xmlMapperGenerator;

	public IntrospectedTableMyBatis3Impl() {
		super(TargetRuntime.MYBATIS3);
		javaModelGenerators = new ArrayList<AbstractJavaGenerator>();
		clientGenerators = new ArrayList<AbstractJavaGenerator>();
	}

	@Override
	public void calculateGenerators(List<String> warnings, ProgressCallback progressCallback) {
		calculateJavaModelGenerators(warnings, progressCallback);
		AbstractJavaClientGenerator javaClientGenerator = calculateClientGenerators(warnings, progressCallback);
		calculateXmlMapperGenerator(javaClientGenerator, warnings, progressCallback);
	}

	protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator, List<String> warnings, ProgressCallback progressCallback) {

		initializeAbstractGenerator(xmlMapperGenerator, warnings, progressCallback);
	}

	protected AbstractJavaClientGenerator calculateClientGenerators(List<String> warnings, ProgressCallback progressCallback) {
		if (!rules.generateJavaClient()) {
			return null;
		}

		AbstractJavaClientGenerator javaGenerator = createJavaClientGenerator();
		if (javaGenerator == null) {
			return null;
		}

		initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
		clientGenerators.add(javaGenerator);

		return javaGenerator;
	}

	protected AbstractJavaClientGenerator createJavaClientGenerator() {
		if (context.getJavaClientGeneratorConfiguration() == null) {
			return null;
		}

		String type = context.getJavaClientGeneratorConfiguration().getConfigurationType();

		AbstractJavaClientGenerator javaGenerator;
		if ("XMLMAPPER".equalsIgnoreCase(type)) {
			javaGenerator = new JavaMapperGenerator();
		} else if ("ANNOTATEDMAPPER".equalsIgnoreCase(type)) {
			javaGenerator = new AnnotatedClientGenerator();
		} else if ("MAPPER".equalsIgnoreCase(type)) {
			javaGenerator = new JavaMapperGenerator();
		} else {
			javaGenerator = (AbstractJavaClientGenerator) ObjectFactory.createInternalObject(type);
		}

		return javaGenerator;
	}

	protected void calculateJavaModelGenerators(List<String> warnings, ProgressCallback progressCallback) {

		if (getRules().generatePrimaryKeyClass()) {
			AbstractJavaGenerator javaGenerator = new PrimaryKeyGenerator();
			initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
			javaModelGenerators.add(javaGenerator);
		}

	}

	protected void initializeAbstractGenerator(AbstractGenerator abstractGenerator, List<String> warnings, ProgressCallback progressCallback) {
		if (abstractGenerator == null) {
			return;
		}

		abstractGenerator.setContext(context);
		abstractGenerator.setIntrospectedTable(this);
		abstractGenerator.setProgressCallback(progressCallback);
		abstractGenerator.setWarnings(warnings);
	}

	@Override
	public List<GeneratedJavaFile> getGeneratedJavaFiles() {
		List<GeneratedJavaFile> answer = new ArrayList<GeneratedJavaFile>();

		for (AbstractJavaGenerator javaGenerator : javaModelGenerators) {
			List<CompilationUnit> compilationUnits = javaGenerator.getCompilationUnits();
			for (CompilationUnit compilationUnit : compilationUnits) {
				GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit, context.getJavaModelGeneratorConfiguration().getTargetProject(), context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING), context.getJavaFormatter());
				answer.add(gjf);
			}
		}

		for (AbstractJavaGenerator javaGenerator : clientGenerators) {
			List<CompilationUnit> compilationUnits = javaGenerator.getCompilationUnits();
			for (CompilationUnit compilationUnit : compilationUnits) {
				GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit, context.getJavaClientGeneratorConfiguration().getTargetProject(), context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING), context.getJavaFormatter());
				answer.add(gjf);
			}
		}

		return answer;
	}

	@Override
	public List<GeneratedXmlFile> getGeneratedXmlFiles() {
		List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>();

		if (xmlMapperGenerator != null) {
			Document document = xmlMapperGenerator.getDocument();
			GeneratedXmlFile gxf = new GeneratedXmlFile(document, getMyBatis3XmlMapperFileName(), getMyBatis3XmlMapperPackage(), context.getSqlMapGeneratorConfiguration().getTargetProject(),  context.getXmlFormatter());
			if (context.getPlugins().sqlMapGenerated(gxf, this)) {
				answer.add(gxf);
			}
		}

		return answer;
	}

	@Override
	public int getGenerationSteps() {
		return javaModelGenerators.size() + clientGenerators.size() + (xmlMapperGenerator == null ? 0 : 1);
	}

	@Override
	public boolean isJava5Targeted() {
		return true;
	}

	@Override
	public boolean requiresXMLGenerator() {
		AbstractJavaClientGenerator javaClientGenerator = createJavaClientGenerator();

		if (javaClientGenerator == null) {
			return false;
		} else {
			return javaClientGenerator.requiresXMLGenerator();
		}
	}
}
