package org.donkeycode.codegen.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author nanfeng
 * @data 2019年8月29日
 */
@Setter
@Getter
public class TableClass {

    private String tableName;
    private String lowerCaseName;
    private String shortClassName;
    private String fullClassName;
    private String packageName;
    private String description;
    private String alias;

    private List<ColumnField> pkFields;
}
