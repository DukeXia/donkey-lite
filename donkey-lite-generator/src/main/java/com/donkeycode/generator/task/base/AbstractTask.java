package com.donkeycode.generator.task.base;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.donkeycode.generator.entity.ColumnInfo;
import com.donkeycode.generator.utils.ConfigUtil;
import com.donkeycode.generator.utils.StringUtil;

import freemarker.template.TemplateException;

/**
 * Author GreedyStar
 * Date   2018/4/20
 */
public abstract class AbstractTask {

    protected String tableName;
    protected String className;
    protected String entityDescription;
    protected String parentTableName;
    protected String parentClassName;
    protected String foreignKey;
    protected String relationalTableName;
    protected String parentForeignKey;
    protected List<ColumnInfo> tableInfos;
    protected List<ColumnInfo> parentTableInfos;

    /**
     * Controller、Service、Dao
     *
     * @param className
     */
    public AbstractTask(String className) {
        this.className = className;
    }

    /**
     * Entity
     *
     * @param className
     * @param parentClassName
     * @param foreignKey
     * @param tableInfos
     */
    public AbstractTask(String className, String parentClassName, String foreignKey, String parentForeignKey, List<ColumnInfo> tableInfos) {
        this.className = className;
        this.parentClassName = parentClassName;
        this.foreignKey = foreignKey;
        this.parentForeignKey = parentForeignKey;
        this.tableInfos = tableInfos;
    }

    /**
     * Mapper
     *
     * @param tableName
     * @param className
     * @param parentTableName
     * @param parentClassName
     * @param foreignKey
     * @param parentForeignKey
     * @param tableInfos
     * @param parentTableInfos
     */
    public AbstractTask(String tableName, String className, String parentTableName, String parentClassName, String foreignKey, String parentForeignKey, String relationalTableName, List<ColumnInfo> tableInfos, List<ColumnInfo> parentTableInfos) {
        this.tableName = tableName;
        this.className = className;
        this.parentTableName = parentTableName;
        this.parentClassName = parentClassName;
        this.foreignKey = foreignKey;
        this.parentForeignKey = parentForeignKey;
        this.relationalTableName = relationalTableName;
        this.tableInfos = tableInfos;
        this.parentTableInfos = parentTableInfos;
    }

    public abstract void run() throws IOException, TemplateException;

    @Deprecated
    protected void createFilePathIfNotExists(String filePath) {
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPackageName())) { // 用户配置了包名，不进行检测
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) { // 检测文件路径是否存在，不存在则创建
            file.mkdir();
        }
    }

}
