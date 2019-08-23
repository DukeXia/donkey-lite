package org.donkeycode.codegen.entity;

import java.io.Serializable;

import org.donkeycode.codegen.utils.StringUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author nanfeng
 *
 */
@Getter
@Setter
@SuppressWarnings("serial")
public class ColumnInfo implements Serializable {

	private String columnName; // 列名
	private int type; // 类型代码
	private String propertyName; // 属性名
	private boolean isPrimaryKey; // 是否主键

	public ColumnInfo() {

	}

	public ColumnInfo(String columnName, int type, boolean isPrimaryKey) {
		this.columnName = columnName;
		this.type = type;
		this.propertyName = StringUtil.columnName2PropertyName(columnName);
		this.isPrimaryKey = isPrimaryKey;
	}
}
