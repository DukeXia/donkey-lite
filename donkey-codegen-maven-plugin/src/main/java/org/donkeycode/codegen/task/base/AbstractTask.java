package org.donkeycode.codegen.task.base;

import java.io.IOException;
import java.util.List;

import org.donkeycode.codegen.ContextConfiguration;
import org.donkeycode.codegen.entity.ColumnField;
import org.donkeycode.codegen.entity.TableClass;

import freemarker.template.TemplateException;

/**
 * 任务节点定义
 *
 * @author nanfeng
 */
public abstract class AbstractTask {

    protected List<ColumnField> columnFields;
    protected TableClass tableClass;
    protected ContextConfiguration configuration;

    public AbstractTask(ContextConfiguration configuration, TableClass tableClass) {
        this.tableClass = tableClass;
        this.configuration = configuration;
    }

    public AbstractTask(ContextConfiguration configuration, TableClass tableClass, List<ColumnField> columnFields) {
        this.tableClass = tableClass;
        this.columnFields = columnFields;
        this.configuration = configuration;
    }

    public abstract void run() throws IOException, TemplateException;
}
