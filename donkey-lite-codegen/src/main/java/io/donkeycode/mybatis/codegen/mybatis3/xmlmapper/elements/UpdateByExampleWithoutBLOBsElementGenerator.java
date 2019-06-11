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
import io.donkeycode.mybatis.api.dom.OutputUtilities;
import io.donkeycode.mybatis.api.dom.xml.Attribute;
import io.donkeycode.mybatis.api.dom.xml.TextElement;
import io.donkeycode.mybatis.api.dom.xml.XmlElement;
import io.donkeycode.mybatis.codegen.mybatis3.ListUtilities;
import io.donkeycode.mybatis.codegen.mybatis3.MyBatis3FormattingUtilities;

/**
 *
 * @author Jeff Butler
 *
 */
public class UpdateByExampleWithoutBLOBsElementGenerator extends
    AbstractXmlElementGenerator {

    public UpdateByExampleWithoutBLOBsElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("update");

        answer.addAttribute(new Attribute("id",
            introspectedTable.getUpdateByExampleStatementId()));

        answer.addAttribute(new Attribute("parameterType", "map"));  //$NON-NLS-2$

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(introspectedTable
            .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        // set up for first column
        sb.setLength(0);
        sb.append("set ");

        Iterator<IntrospectedColumn> iter = ListUtilities.removeGeneratedAlwaysColumns(introspectedTable
            .getNonBLOBColumns()).iterator();
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();

            sb.append(MyBatis3FormattingUtilities
                .getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(
                introspectedColumn, "record."));

            if (iter.hasNext()) {
                sb.append(',');
            }

            answer.addElement(new TextElement(sb.toString()));

            // set up for the next column
            if (iter.hasNext()) {
                sb.setLength(0);
                OutputUtilities.xmlIndent(sb, 1);
            }
        }

        answer.addElement(getUpdateByExampleIncludeElement());

        if (context.getPlugins()
            .sqlMapUpdateByExampleWithoutBLOBsElementGenerated(answer,
                introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
