package org.donkeycode.codegen.entity;

import java.io.Serializable;

import org.donkeycode.codegen.utils.StringUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * @author nanfeng
 * @data 2019年8月29日
 */
@Getter
@Setter
@SuppressWarnings("serial")
public class ColumnField implements Serializable {

    private String columnName; // 列名
    private int type; // 类型代码
    private String propertyName; // 属性名
    private boolean isPrimaryKey; // 是否主键

    public ColumnField() {

    }

    public ColumnField(String columnName, int type, boolean isPrimaryKey) {
        this.columnName = columnName;
        this.type = type;
        this.propertyName = StringUtil.columnName2PropertyName(columnName);
        this.isPrimaryKey = isPrimaryKey;
    }
}