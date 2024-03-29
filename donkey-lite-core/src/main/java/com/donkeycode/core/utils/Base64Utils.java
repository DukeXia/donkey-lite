package com.donkeycode.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author donkey
 */
@Slf4j
public class Base64Utils {

    static final String DECODE = "UTF-8";

    /**
     * 加密
     *
     * @param value
     * @return
     */
    public static String encodeBase64(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return Base64.encodeBase64String(value.getBytes(DECODE));
        } catch (UnsupportedEncodingException e) {
            log.error("value encode base64 error,value:{}, message:{}, info:{}", value, e.getMessage(), e);
            return null;
        }
    }

    /**
     * 解密
     *
     * @param value
     * @return
     */
    public static String decoderBase64(String value) {

        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return new String(Base64.decodeBase64(value), DECODE);
        } catch (Exception e) {
            log.error("value encode base64 error,value:{}, message:{}, info:{}", value, e.getMessage(), e);
            return null;
        }
    }

    /**
     * 判断是否Base64
     *
     * @param value
     * @return
     */
    public static boolean isBase64(String value) {
        String deCodevalue = decoderBase64(value);
        String encodeValue = encodeBase64(deCodevalue);
        if (StringUtils.isBlank(deCodevalue) || StringUtils.isBlank(encodeValue)) {
            return false;
        }
        return encodeValue.equals(value) ? true : false;
    }
}