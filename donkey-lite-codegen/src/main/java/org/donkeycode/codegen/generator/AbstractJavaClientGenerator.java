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

/**
 * This class exists to that Java client generators can specify whether
 * an XML generator is required to match the methods in the
 * Java client.  For example, a Java client built entirely with
 * annotations does not need matching XML.
 *
 * @author Jeff Butler
 *
 */
public abstract class AbstractJavaClientGenerator extends AbstractJavaGenerator {

	private boolean requiresXMLGenerator;

	public AbstractJavaClientGenerator(boolean requiresXMLGenerator) {
		super();
		this.requiresXMLGenerator = requiresXMLGenerator;
	}

	/**
	 * Returns true is a matching XML generator is required.
	 *
	 * @return true if matching XML is generator required
	 */
	public boolean requiresXMLGenerator() {
		return requiresXMLGenerator;
	}

}
