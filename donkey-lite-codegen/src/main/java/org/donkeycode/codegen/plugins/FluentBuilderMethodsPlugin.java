/**
 * Copyright 2006-2018 the original author or authors.
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
package org.donkeycode.codegen.plugins;

import java.util.List;

import org.donkeycode.codegen.api.IntrospectedColumn;
import org.donkeycode.codegen.api.IntrospectedTable;
import org.donkeycode.codegen.api.PluginAdapter;
import org.donkeycode.codegen.api.IntrospectedTable.TargetRuntime;
import org.donkeycode.codegen.api.dom.java.JavaVisibility;
import org.donkeycode.codegen.api.dom.java.Method;
import org.donkeycode.codegen.api.dom.java.TopLevelClass;

/**
 * This plugin adds fluent builder methods to the generated model classes.
 *
 * <p>Example:
 *
 * <p>Given the domain class <code>MyDomainClass</code> with setter-method <code>setValue(Object v)</code>
 *
 * <p>The plugin will create the additional Method <code>public MyDomainClass withValue(Object v)</code>
 *
 *
 * @author Stefan Lack
 */
public class FluentBuilderMethodsPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {

        Method fluentMethod = new Method();
        fluentMethod.setVisibility(JavaVisibility.PUBLIC);
        fluentMethod.setReturnType(topLevelClass.getType());
        fluentMethod.setName("with" + method.getName().substring(3));
        fluentMethod.getParameters().addAll(method.getParameters());

        if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3_DSQL) {
            context.getCommentGenerator().addGeneralMethodAnnotation(fluentMethod, introspectedTable, topLevelClass.getImportedTypes());
        } else {
            context.getCommentGenerator().addGeneralMethodComment(fluentMethod, introspectedTable);
        }

        StringBuilder sb = new StringBuilder().append("this.")
            .append(method.getName()).append('(').append(introspectedColumn.getJavaProperty()).append(");");
        fluentMethod.addBodyLine(sb.toString());
        fluentMethod.addBodyLine("return this;");

        topLevelClass.addMethod(fluentMethod);

        return super.modelSetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }
}
