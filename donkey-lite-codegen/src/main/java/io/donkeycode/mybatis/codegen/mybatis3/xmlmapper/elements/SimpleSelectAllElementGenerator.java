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
import io.donkeycode.mybatis.config.PropertyRegistry;
import io.donkeycode.mybatis.internal.util.StringUtility;

/**
 *
 * @author Jeff Butler
 *
 */
public class SimpleSelectAllElementGenerator extends
    AbstractXmlElementGenerator {

    public SimpleSelectAllElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");

        answer.addAttribute(new Attribute(
            "id", introspectedTable.getSelectAllStatementId()));
        answer.addAttribute(new Attribute("resultMap",
            introspectedTable.getBaseResultMapId()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns()
            .iterator();
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

        sb.setLength(0);
        sb.append("from ");
        sb.append(introspectedTable
            .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        String orderByClause = introspectedTable.getTableConfigurationProperty(PropertyRegistry.TABLE_SELECT_ALL_ORDER_BY_CLAUSE);
        boolean hasOrderBy = StringUtility.stringHasValue(orderByClause);
        if (hasOrderBy) {
            sb.setLength(0);
            sb.append("order by ");
            sb.append(orderByClause);
            answer.addElement(new TextElement(sb.toString()));
        }

        if (context.getPlugins().sqlMapSelectAllElementGenerated(
            answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
