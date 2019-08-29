package org.donkeycode.codegen.invoker.base;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.donkeycode.codegen.ContextConfiguration;
import org.donkeycode.codegen.db.JDBCConnectionFactory;
import org.donkeycode.codegen.entity.ColumnField;
import org.donkeycode.codegen.entity.TableClass;
import org.donkeycode.codegen.task.base.AbstractTask;
import org.donkeycode.codegen.utils.TaskQueue;

import freemarker.template.TemplateException;
import lombok.Getter;
import lombok.Setter;

/**
 * Author GreedyStar Date 2018/9/5
 */
@Setter
@Getter
public abstract class AbstractInvoker implements Invoker {

    private ContextConfiguration configuration;
    protected TableClass tableClass;
    protected List<ColumnField> columnFields;
    protected JDBCConnectionFactory jDBCConnectionFactory = new JDBCConnectionFactory();
    protected TaskQueue taskQueue = new TaskQueue();
    private ExecutorService executorPool = Executors.newFixedThreadPool(6);

    private void initDataSource() throws Exception {
        if (!this.jDBCConnectionFactory.initConnection(configuration.getDatasources())) {
            throw new Exception("Failed to connect to database at url:" + configuration.getDatasources().getUrl());
        }
        getTableInfos();
        jDBCConnectionFactory.close();
    }

    protected abstract void getTableInfos() throws SQLException;

    protected abstract void initTasks();

    @Override
    public void execute() {
        try {
            initDataSource();
            initTasks();
            while (!taskQueue.isEmpty()) {
                AbstractTask task = taskQueue.poll();
                executorPool.execute(() -> {
                    try {
                        task.run();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TemplateException e) {
                        e.printStackTrace();
                    }
                });
            }
            executorPool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
