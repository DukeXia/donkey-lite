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
package io.donkeycode.mybatis.codegen.mybatis3.xmlmapper.elements;

import java.util.Iterator;

import io.donkeycode.mybatis.api.IntrospectedColumn;
import io.donkeycode.mybatis.api.dom.xml.Attribute;
import io.donkeycode.mybatis.api.dom.xml.TextElement;
import io.donkeycode.mybatis.api.dom.xml.XmlElement;
import io.donkeycode.mybatis.codegen.mybatis3.MyBatis3FormattingUtilities;

/**
 *
 * @author Jeff Butler
 *
 */
public class BaseColumnListElementGenerator extends AbstractXmlElementGenerator {

    public BaseColumnListElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql");

        answer.addAttribute(new Attribute("id",
            introspectedTable.getBaseColumnListId()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        Iterator<IntrospectedColumn> iter = introspectedTable
            .getNonBLOBColumns().iterator();
        while (iter.hasNext()) {
            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(iter
                .next()));

            if (iter.hasNext()) {
                sb.append(", ");
            }

            if (sb.length() > 80) {
                answer.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
        }

        if (sb.length() > 0) {
            answer.addElement(new TextElement(sb.toString()));
        }

        if (context.getPlugins().sqlMapBaseColumnListElementGenerated(
            answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}