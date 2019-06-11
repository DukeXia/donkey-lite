package io.donkeycode.codegen.application;

import io.donkeycode.codegen.invoker.Many2ManyInvoker;
import io.donkeycode.codegen.invoker.One2ManyInvoker;
import io.donkeycode.codegen.invoker.SingleInvoker;
import io.donkeycode.codegen.invoker.base.Invoker;

/**
 * Author GreedyStar
 * Date   2018/9/5
 */
public class Main {

    public static void main(String[] args) {
        single("course", "Course", "课程");
        single("goods", "Goods", "商品");
        single("question", "Question", "试题");
        single("question_option", "QuestionOption", "试题选项");
        single("examination", "Examination", "问卷");
        single("coustomer", "Coustomer", "客户");
    }

    public static void many2many() {
        Invoker invoker = new Many2ManyInvoker.Builder()
            .setTableName("user")
            .setClassName("User")
            .setParentTableName("role")
            .setParentClassName("Role")
            .setRelationTableName("user_role")
            .setForeignKey("userId")
            .setParentForeignKey("roleId")
            .build();
        invoker.execute();
    }

    public static void one2many() {
        Invoker invoker = new One2ManyInvoker.Builder()
            .setTableName("user")
            .setClassName("User")
            .setParentTableName("office")
            .setParentClassName("Office")
            .setForeignKey("officeId")
            .build();
        invoker.execute();
    }

    public static void single(String tableName, String domainName, String entityDescription) {
        Invoker invoker = new SingleInvoker.Builder()
            .setTableName(tableName)
            .setClassName(domainName)
            .setEntityDescription(entityDescription)
            .build();
        invoker.execute();
    }

}
