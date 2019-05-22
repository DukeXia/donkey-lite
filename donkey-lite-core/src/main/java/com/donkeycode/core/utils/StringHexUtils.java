package com.donkeycode.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * StringHex操作
 *
 * @author LCX
 */
public class StringHexUtils {

    static final Logger logger = LoggerFactory.getLogger(StringHexUtils.class);

    // 转化十六进制编码为字符串
    public static String toStringHex(String s) throws IOException {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
        }
        return new String(baKeyword, "utf-8");
    }

}
