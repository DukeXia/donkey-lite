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

import static io.donkeycode.mybatis.internal.util.StringUtility.stringHasValue;

import java.util.List;

import io.donkeycode.mybatis.api.IntrospectedColumn;
import io.donkeycode.mybatis.api.dom.xml.Attribute;
import io.donkeycode.mybatis.api.dom.xml.XmlElement;
import io.donkeycode.mybatis.codegen.mybatis3.MyBatis3FormattingUtilities;

/**
 *
 * @author Jeff Butler
 *
 */
public class ResultMapWithoutBLOBsElementGenerator extends
    AbstractXmlElementGenerator {

    private boolean isSimple;

    public ResultMapWithoutBLOBsElementGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("resultMap");
        answer.addAttribute(new Attribute("id",
            introspectedTable.getBaseResultMapId()));

        String returnType;
        if (isSimple) {
            returnType = introspectedTable.getBaseRecordType();
        } else {
            if (introspectedTable.getRules().generateBaseRecordClass()) {
                returnType = introspectedTable.getBaseRecordType();
            } else {
                returnType = introspectedTable.getPrimaryKeyType();
            }
        }

        answer.addAttribute(new Attribute("type",
            returnType));

        context.getCommentGenerator().addComment(answer);

        if (introspectedTable.isConstructorBased()) {
            addResultMapConstructorElements(answer);
        } else {
            addResultMapElements(answer);
        }

        if (context.getPlugins().sqlMapResultMapWithoutBLOBsElementGenerated(
            answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }

    private void addResultMapElements(XmlElement answer) {
        for (IntrospectedColumn introspectedColumn : introspectedTable
            .getPrimaryKeyColumns()) {
            XmlElement resultElement = new XmlElement("id");

            resultElement
                .addAttribute(new Attribute(
                    "column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn)));
            resultElement.addAttribute(new Attribute(
                "property", introspectedColumn.getJavaProperty()));
            resultElement.addAttribute(new Attribute("jdbcType",
                introspectedColumn.getJdbcTypeName()));

            if (stringHasValue(introspectedColumn.getTypeHandler())) {
                resultElement.addAttribute(new Attribute(
                    "typeHandler", introspectedColumn.getTypeHandler()));
            }

            answer.addElement(resultElement);
        }

        List<IntrospectedColumn> columns;
        if (isSimple) {
            columns = introspectedTable.getNonPrimaryKeyColumns();
        } else {
            columns = introspectedTable.getBaseColumns();
        }
        for (IntrospectedColumn introspectedColumn : columns) {
            XmlElement resultElement = new XmlElement("result");

            resultElement
                .addAttribute(new Attribute(
                    "column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn)));
            resultElement.addAttribute(new Attribute(
                "property", introspectedColumn.getJavaProperty()));
            resultElement.addAttribute(new Attribute("jdbcType",
                introspectedColumn.getJdbcTypeName()));

            if (stringHasValue(introspectedColumn.getTypeHandler())) {
                resultElement.addAttribute(new Attribute(
                    "typeHandler", introspectedColumn.getTypeHandler()));
            }

            answer.addElement(resultElement);
        }
    }

    private void addResultMapConstructorElements(XmlElement answer) {
        XmlElement constructor = new XmlElement("constructor");

        for (IntrospectedColumn introspectedColumn : introspectedTable
            .getPrimaryKeyColumns()) {
            XmlElement resultElement = new XmlElement("idArg");

            resultElement
                .addAttribute(new Attribute(
                    "column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn)));
            resultElement.addAttribute(new Attribute("jdbcType",
                introspectedColumn.getJdbcTypeName()));
            resultElement.addAttribute(new Attribute("javaType",
                introspectedColumn.getFullyQualifiedJavaType()
                    .getFullyQualifiedName()));

            if (stringHasValue(introspectedColumn.getTypeHandler())) {
                resultElement.addAttribute(new Attribute(
                    "typeHandler", introspectedColumn.getTypeHandler()));
            }

            constructor.addElement(resultElement);
        }

        List<IntrospectedColumn> columns;
        if (isSimple) {
            columns = introspectedTable.getNonPrimaryKeyColumns();
        } else {
            columns = introspectedTable.getBaseColumns();
        }
        for (IntrospectedColumn introspectedColumn : columns) {
            XmlElement resultElement = new XmlElement("arg");

            resultElement
                .addAttribute(new Attribute(
                    "column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn)));
            resultElement.addAttribute(new Attribute("jdbcType",
                introspectedColumn.getJdbcTypeName()));
            resultElement.addAttribute(new Attribute("javaType",
                introspectedColumn.getFullyQualifiedJavaType()
                    .getFullyQualifiedName()));

            if (stringHasValue(introspectedColumn.getTypeHandler())) {
                resultElement.addAttribute(new Attribute(
                    "typeHandler", introspectedColumn.getTypeHandler()));
            }

            constructor.addElement(resultElement);
        }

        answer.addElement(constructor);
    }
}