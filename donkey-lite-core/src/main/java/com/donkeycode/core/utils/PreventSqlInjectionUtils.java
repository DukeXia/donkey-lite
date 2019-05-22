package com.donkeycode.core.utils;

/**
 * Created by csf on 2015/5/16.
 *
 * @author csf
 */
public class PreventSqlInjectionUtils {

    private static String injectionStr = "exec|insert|select|delete|grant|update|count|chr|mid|master|truncate|char|declare|;";

    /**
     * 是否存在sql注入
     *
     * @param str 校验的字符串
     * @return 如果存在sql的关键字返回true
     */
    public static boolean isSqlInjection(String str) {
        if (StringSuperUtils.isNotEmpty(str)) {
            return isSqlInjection(str, injectionStr);
        } else {
            return false;
        }
    }

    public static boolean isSqlInjection(String str, String injectionString) {
        if (StringSuperUtils.isNotEmpty(str) && StringSuperUtils.isNotEmpty(injectionString)) {
            String[] injectionStrings = injectionString.split("\\|");
            for (String injection : injectionStrings) {
                if (str.contains(injection + " ")) {
                    return true;
                }
            }
        }
        return false;
    }
}
