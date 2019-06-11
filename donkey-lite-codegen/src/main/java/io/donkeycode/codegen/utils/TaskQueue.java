package io.donkeycode.codegen.utils;

import java.util.LinkedList;
import java.util.List;

import io.donkeycode.codegen.entity.ColumnInfo;
import io.donkeycode.codegen.task.*;
import io.donkeycode.codegen.task.base.AbstractTask;

/**
 * Author GreedyStar
 * Date   2018-11-27
 */
public class TaskQueue {

    private LinkedList<AbstractTask> taskQueue = new LinkedList<>();

    private void initCommonTasks(String className, String entityDescription) {
        if (StringUtil.isNotBlank(ConfigUtil.getConfiguration().getPath().getController())) {
            taskQueue.add(new ControllerTask(className));
        }
        if (StringUtil.isNotBlank(ConfigUtil.getConfiguration().getPath().getCommEditBiz())) {
            taskQueue.add(new CommEditBizTask(className, entityDescription));
        }
        if (StringUtil.isNotBlank(ConfigUtil.getConfiguration().getPath().getCommQueryBiz())) {
            taskQueue.add(new CommQueryBizTask(className, entityDescription));
        }
        if (StringUtil.isNotBlank(ConfigUtil.getConfiguration().getPath().getService())) {
            taskQueue.add(new ServiceTask(className));
        }
        if (StringUtil.isNotBlank(ConfigUtil.getConfiguration().getPath().getInterf())) {
            taskQueue.add(new InterfaceTask(className));
        }
        if (StringUtil.isNotBlank(ConfigUtil.getConfiguration().getPath().getMapper())) {
            taskQueue.add(new MapperTask(className));
        }
    }

    public void initSingleTasks(String className, String tableName, String entityDescription, List<ColumnInfo> tableInfos) {
        initCommonTasks(className, entityDescription);
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getEntity())) {
            taskQueue.add(new EntityTask(className, tableName, tableInfos));
        }
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getMapperXml())) {
            taskQueue.add(new MapperXmlTask(className, tableName, tableInfos));
        }
    }

    public void initOne2ManyTasks(String tableName, String className, String entityDescription, String parentTableName, String parentClassName, String foreignKey, List<ColumnInfo> tableInfos, List<ColumnInfo> parentTableInfos) {
        initCommonTasks(className, entityDescription);
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getEntity())) {
            taskQueue.add(new EntityTask(className, parentClassName, foreignKey, tableInfos));
            taskQueue.add(new EntityTask(parentClassName, parentTableInfos));
        }
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getMapper())) {
            taskQueue.add(new MapperXmlTask(tableName, className, parentTableName, parentClassName, foreignKey, tableInfos, parentTableInfos));
        }
    }

    public void initMany2ManyTasks(String tableName, String entityDescription, String className, String parentTableName, String parentClassName, String foreignKey, String parentForeignKey, String relationalTableName, List<ColumnInfo> tableInfos, List<ColumnInfo> parentTableInfos) {
        initCommonTasks(className, entityDescription);
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getEntity())) {
            taskQueue.add(new EntityTask(className, parentClassName, foreignKey, parentForeignKey, tableInfos));
            taskQueue.add(new EntityTask(parentClassName, parentTableInfos));
        }
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getMapper())) {
            taskQueue.add(new MapperXmlTask(tableName, className, parentTableName, parentClassName, foreignKey, parentForeignKey, relationalTableName, tableInfos, parentTableInfos));
        }
    }

    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    public AbstractTask poll() {
        return taskQueue.poll();
    }
}
