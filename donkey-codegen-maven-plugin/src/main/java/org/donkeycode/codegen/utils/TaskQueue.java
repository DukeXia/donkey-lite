package org.donkeycode.codegen.utils;

import java.util.LinkedList;
import java.util.List;

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
 * 
 * @author nanfeng
 *
 */
public class TaskQueue {

	private LinkedList<AbstractTask> taskQueue = new LinkedList<>();

	private void initCommonTasks(TableClass tableClass) {
		taskQueue.add(new ControllerTask(tableClass));
		taskQueue.add(new ServiceTask(tableClass));
		taskQueue.add(new InterfaceTask(tableClass));
		taskQueue.add(new MapperTask(tableClass));
	}

	public void initTasks(TableClass tableClass, List<ColumnField> columnFields) {
		initCommonTasks(tableClass);
		taskQueue.add(new EntityTask(tableClass,columnFields));
		taskQueue.add(new MapperXmlTask(tableClass, columnFields));
	}

	public boolean isEmpty() {
		return taskQueue.isEmpty();
	}

	public AbstractTask poll() {
		return taskQueue.poll();
	}

}
