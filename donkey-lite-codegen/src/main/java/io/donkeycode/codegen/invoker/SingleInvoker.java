package io.donkeycode.codegen.invoker;

import java.sql.SQLException;

import io.donkeycode.codegen.invoker.base.AbstractBuilder;
import io.donkeycode.codegen.invoker.base.AbstractInvoker;
import io.donkeycode.codegen.invoker.base.Invoker;
import io.donkeycode.codegen.utils.GeneratorUtil;
import io.donkeycode.codegen.utils.StringUtil;

/**
 * Author GreedyStar
 * Date   2018/9/5
 */
public class SingleInvoker extends AbstractInvoker {

    @Override
    protected void getTableInfos() throws SQLException {
        tableInfos = connectionUtil.getMetaData(tableName);
    }

    @Override
    protected void initTasks() {
        taskQueue.initSingleTasks(className, tableName, entityDescription, tableInfos);
    }

    public static class Builder extends AbstractBuilder {

        private SingleInvoker invoker = new SingleInvoker();

        public Builder setTableName(String tableName) {
            invoker.setTableName(tableName);
            return this;
        }

        public Builder setClassName(String className) {
            invoker.setClassName(className);
            return this;
        }

        public Builder setEntityDescription(String entityDescription) {
            invoker.setEntityDescription(entityDescription);
            return this;
        }

        @Override
        public Invoker build() {
            if (!isParamtersValid()) {
                return null;
            }
            return invoker;
        }

        @Override
        public void checkBeforeBuild() throws Exception {
            if (StringUtil.isBlank(invoker.getTableName())) {
                throw new Exception("Expect table's name, but get a blank String.");
            }
            if (StringUtil.isBlank(invoker.getClassName())) {
                invoker.setClassName(GeneratorUtil.generateClassName(invoker.getTableName()));
            }
        }
    }

}
