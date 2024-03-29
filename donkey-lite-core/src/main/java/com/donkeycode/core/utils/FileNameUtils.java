package com.donkeycode.core.utils;

/**
 * 对文件名称处理
 *
 * @author donkey
 * @since 2019年5月12日
 */
public class FileNameUtils {

    /**
     * 获取文件后缀
     *
     * @param filePath 文件相对路径或文件名
     * @return Suffix 后缀
     */
    public static String getFileSuffix(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return "";
        }
        int index = filePath.lastIndexOf(".");
        return index + 1 < filePath.length() ? filePath.substring(filePath.lastIndexOf(".") + 1) : "";

    }

    /**
     * 获取文件名 不带后缀
     *
     * @param filePath 文件相对路径或文件名
     * @return Suffix 后缀
     */
    public static String getFileName(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return "";
        }
        int index = filePath.lastIndexOf(".");
        return index > 10 ? filePath.substring(0, filePath.lastIndexOf(".")) : "";
    }
}
