/**
 * Copyright 2006-2016 the original author or authors.
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
package org.donkeycode.codegen.generator.mybatis3.xmlmapper;

import static org.donkeycode.codegen.internal.util.messages.Messages.getString;

import org.donkeycode.codegen.api.FullyQualifiedTable;
import org.donkeycode.codegen.api.dom.xml.Attribute;
import org.donkeycode.codegen.api.dom.xml.Document;
import org.donkeycode.codegen.api.dom.xml.XmlElement;
import org.donkeycode.codegen.generator.AbstractXmlGenerator;
import org.donkeycode.codegen.generator.XmlConstants;

/**
 * @author Jeff Butler
 */
public class XMLMapperGenerator extends AbstractXmlGenerator {

	public XMLMapperGenerator() {
		super();
	}

	protected XmlElement getSqlMapElement() {
		FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
		progressCallback.startTask(getString("Progress.12", table.toString()));
		XmlElement answer = new XmlElement("mapper");
		String namespace = introspectedTable.getMyBatis3SqlMapNamespace();
		answer.addAttribute(new Attribute("namespace", namespace));

		context.getCommentGenerator().addRootComment(answer);

		return answer;
	}

	@Override
	public Document getDocument() {
		Document document = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID, XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
		document.setRootElement(getSqlMapElement());

		if (!context.getPlugins().sqlMapDocumentGenerated(document, introspectedTable)) {
			document = null;
		}

		return document;
	}
}
