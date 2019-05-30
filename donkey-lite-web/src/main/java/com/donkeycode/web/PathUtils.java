package com.donkeycode.web;


import org.apache.commons.lang3.StringUtils;

/**
 * Web Path tools
 *
 */
public final class PathUtils {

    private PathUtils() {
    }

    public static String normalizePath(String path) {
        if (StringUtils.isBlank(path)) {
            return path;
        }
        String normalizedPath = path;
        if (!normalizedPath.startsWith("/")) {
            normalizedPath = "/" + normalizedPath;
        }
        if (normalizedPath.endsWith("/")) {
            normalizedPath = normalizedPath.substring(0, normalizedPath.length() - 1);
        }
        return normalizedPath;
    }
}