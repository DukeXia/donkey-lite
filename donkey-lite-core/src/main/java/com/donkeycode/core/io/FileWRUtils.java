package com.donkeycode.core.io;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Slf4j
public class FileWRUtils {

    public static void copyFile(String resFilePath, String distFolder) throws IOException {
        File resFile = new File(resFilePath);
        File distFile = new File(distFolder);
        if (resFile.isDirectory()) { // 目录时
            FileUtils.copyDirectoryToDirectory(resFile, distFile);
        } else if (resFile.isFile()) { // 文件时
            FileUtils.copyFileToDirectory(resFile, distFile);
        }
    }

    public static void deleteFile(String targetPath) throws IOException {
        File targetFile = new File(targetPath);
        if (targetFile.isDirectory()) {
            FileUtils.deleteDirectory(targetFile);
        } else if (targetFile.isFile()) {
            targetFile.delete();
        }
    }

    public static List<String> getContentFromFile(String filePath) throws IOException {
        try {
            if (!(new File(filePath).exists())) {
                log.error("file not found! file:" + filePath);
                throw new RuntimeException("file not found!");
            }
            return FileUtils.readLines(new File(filePath), Charset.defaultCharset().name());

        } catch (IOException ioException) {
            log.error("io error,error:" + ioException.getMessage(), ioException);
            throw ioException;
        }
    }

    /**
     * 给指定文件追加内容
     *
     * @param filePath
     * @param contents
     */
    public static void appendContent(String filePath, List<String> contents) throws IOException {
        try {
            FileUtils.writeLines(new File(filePath), contents);
        } catch (IOException ioException) {
            log.error("io error,error:" + ioException.getMessage(), ioException);
            throw ioException;
        }
    }
}