package org.donkeycode.codegen.utils;

import java.io.File;

import com.donkeycode.core.utils.ColumnPropertyUtils;
import com.donkeycode.core.utils.StringUtils;

/**
 * Author GreedyStar
 * Date   2018-09-10
 */
public class StringUtil {

    public static boolean isBlank(String value) {
        return StringUtils.isBlank(value);
    }

    /**
     * 首字母大写
     */
    public static String firstToUpperCase(String string) {
        StringBuilder sb = new StringBuilder();
        sb.append(string.substring(0, 1).toUpperCase()).append(string.substring(1));
        return sb.toString();
    }

    /**
     * 首字母小写
     */
    public static String firstToLowerCase(String string) {
        StringBuilder sb = new StringBuilder();
        sb.append(string.substring(0, 1).toLowerCase()).append(string.substring(1));
        return sb.toString();
    }

    /**
     * 数据库列名转换为实体的属性名
     *
     * @param columnName 列名
     * @return 转换后的实体属性名
     */
    public static String columnName2PropertyName(String columnName) {
        return ColumnPropertyUtils.columnToProperty(columnName);
    }

    /**
     * 给定字符串除特定符号外的字符是否全部大写
     *
     * @param string
     * @return
     */
    public static boolean isAllUpperCase(String string) {
        for (Character c : string.replace("_", "").toCharArray()) {
            if (!Character.isUpperCase(c)) {
                return false;
            }
        }
        return true;
    }

    public static String package2Path(String packageName) {
        if (StringUtil.isBlank(packageName)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String[] packages = packageName.split("\\.");
        for (String str : packages) {
            sb.append(str).append(File.separator);
        }
        return sb.toString();
    }
}
