package org.donkeycode.codegen.utils;

import java.util.LinkedList;
import java.util.List;

import org.donkeycode.codegen.entity.ColumnField;
import org.donkeycode.codegen.entity.TableClass;
import org.donkeycode.codegen.task.ControllerTask;
import org.donkeycode.codegen.task.DaoTask;
import org.donkeycode.codegen.task.EntityTask;
import org.donkeycode.codegen.task.InterfaceTask;
import org.donkeycode.codegen.task.MapperTask;
import org.donkeycode.codegen.task.ServiceTask;
import org.donkeycode.codegen.task.base.AbstractTask;

/**
 * Author GreedyStar Date 2018-11-27
 */
public class TaskQueue {

	private LinkedList<AbstractTask> taskQueue = new LinkedList<>();

	private void initCommonTasks(TableClass tableClass) {
		if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getController())) {
			taskQueue.add(new ControllerTask(tableClass));
		}
		if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getService())) {
			taskQueue.add(new ServiceTask(tableClass));
		}
		if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getInterf())) {
			taskQueue.add(new InterfaceTask(tableClass));
		}
		if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getDao())) {
			taskQueue.add(new DaoTask(tableClass));
		}
	}

	public void initSingleTasks(TableClass tableClass, List<ColumnField> columnFields) {
		initCommonTasks(tableClass);
		if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getEntity())) {
			taskQueue.add(new EntityTask(tableClass));
		}
		if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getMapper())) {
			taskQueue.add(new MapperTask(tableClass,  columnFields));
		}
	}

	public boolean isEmpty() {
		return taskQueue.isEmpty();
	}

	public AbstractTask poll() {
		return taskQueue.poll();
	}

}
