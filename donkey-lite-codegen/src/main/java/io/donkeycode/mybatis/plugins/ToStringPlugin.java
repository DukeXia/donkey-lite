package io.donkeycode.mybatis.plugins;

import static io.donkeycode.mybatis.internal.util.StringUtility.isTrue;

import java.util.List;
import java.util.Properties;

import io.donkeycode.mybatis.api.IntrospectedTable;
import io.donkeycode.mybatis.api.PluginAdapter;
import io.donkeycode.mybatis.api.IntrospectedTable.TargetRuntime;
import io.donkeycode.mybatis.api.dom.java.Field;
import io.donkeycode.mybatis.api.dom.java.FullyQualifiedJavaType;
import io.donkeycode.mybatis.api.dom.java.JavaVisibility;
import io.donkeycode.mybatis.api.dom.java.Method;
import io.donkeycode.mybatis.api.dom.java.TopLevelClass;

public class ToStringPlugin extends PluginAdapter {

    private boolean useToStringFromRoot;

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        useToStringFromRoot = isTrue(properties.getProperty("useToStringFromRoot"));
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        generateToString(introspectedTable, topLevelClass);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        generateToString(introspectedTable, topLevelClass);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        generateToString(introspectedTable, topLevelClass);
        return true;
    }

    private void generateToString(IntrospectedTable introspectedTable, TopLevelClass topLevelClass) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.setName("toString");
        if (introspectedTable.isJava5Targeted()) {
            method.addAnnotation("@Override");
        }

        if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3_DSQL) {
            context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, topLevelClass.getImportedTypes());
        } else {
            context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        }

        method.addBodyLine("StringBuilder sb = new StringBuilder();");
        method.addBodyLine("sb.append(getClass().getSimpleName());");
        method.addBodyLine("sb.append(\" [\");");
        method.addBodyLine("sb.append(\"Hash = \").append(hashCode());");
        StringBuilder sb = new StringBuilder();
        for (Field field : topLevelClass.getFields()) {
            String property = field.getName();
            sb.setLength(0);
            sb.append("sb.append(\"").append(", ").append(property)  //$NON-NLS-2$
                .append("=\")").append(".append(").append(property)  //$NON-NLS-2$
                .append(");");
            method.addBodyLine(sb.toString());
        }

        method.addBodyLine("sb.append(\"]\");");
        if (useToStringFromRoot && topLevelClass.getSuperClass() != null) {
            method.addBodyLine("sb.append(\", from super class \");");
            method.addBodyLine("sb.append(super.toString());");
        }
        method.addBodyLine("return sb.toString();");

        topLevelClass.addMethod(method);
    }
}
