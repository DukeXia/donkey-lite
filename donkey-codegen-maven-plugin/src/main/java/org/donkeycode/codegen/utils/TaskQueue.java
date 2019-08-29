package org.donkeycode.codegen.utils;

import java.util.LinkedList;
import java.util.List;

import org.donkeycode.codegen.ContextConfiguration;
import org.donkeycode.codegen.entity.ColumnField;
import org.donkeycode.codegen.entity.TableClass;
import org.donkeycode.codegen.task.ControllerTask;
import org.donkeycode.codegen.task.MapperTask;
import org.donkeycode.codegen.task.EntityTask;
import org.donkeycode.codegen.task.InterfaceTask;
import org.donkeycode.codegen.task.MapperXmlTask;
import org.donkeycode.codegen.task.ServiceTask;
import org.donkeycode.codegen.task.base.AbstractTask;

/**
 * @author nanfeng
 */
public class TaskQueue {

    private LinkedList<AbstractTask> taskQueue = new LinkedList<>();

    private void initCommonTasks(TableClass tableClass, ContextConfiguration configuration) {
        taskQueue.add(new ControllerTask(configuration, tableClass));
        taskQueue.add(new ServiceTask(configuration, tableClass));
        taskQueue.add(new InterfaceTask(configuration, tableClass));
        taskQueue.add(new MapperTask(configuration, tableClass));
    }

    public void initTasks(TableClass tableClass, List<ColumnField> columnFields, ContextConfiguration configuration) {
        initCommonTasks(tableClass, configuration);
        taskQueue.add(new EntityTask(configuration, tableClass, columnFields));
        taskQueue.add(new MapperXmlTask(configuration, tableClass, columnFields));
    }

    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    public AbstractTask poll() {
        return taskQueue.poll();
    }

}
