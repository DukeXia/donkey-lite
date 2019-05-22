package com.donkeycode.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class StringJsonUtils {

    /**
     * 是否是 JSON
     *
     * @param json 字符串
     * @return 返回是否是正确JSON
     */
    public static boolean isGoodJson(String json) {
        if (StringUtils.isNotEmpty(json)) {
            try {
                JSON.parse(json);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 是否是 对象 JSON
     *
     * @param json 字符串
     * @return 返回是否是正确JSON
     */
    public static boolean isGoodObjectJson(String json) {
        if (StringUtils.isNotEmpty(json)) {
            try {
                JSON.parseObject(json);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 是否是 数组 JSON
     *
     * @param json 字符串
     * @return 返回是否是正确JSON
     */
    public static boolean isGoodArrayJson(String json) {
        if (StringUtils.isNotEmpty(json)) {
            try {
                JSON.parseArray(json);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else
            return false;

    }

    public static Map<String, String> toMap(String jsonStr) {
        return JSON.parseObject(jsonStr, new TypeReference<Map<String, String>>() {
        });
    }

}
