package com.donkeycode.web;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回码
 *
 * @author yanjun.xue
 */
public enum HttpCode {

    OK(200, "操作成功"),
    WARING_MSG(201, "提醒信息"),
    ERR_PASSWORD(202, "密码或者用户错误"),
    REGISTERED(203, "已注册"),
    NOT_REGISTER(204, "未注册"),
    INVALID_AUTH_CODE(205, "无效验证码"),
    DELETED(206, "已删除或下线"),
    NOT_LOGIN(210, "未登录"),
    REDIRECT(301, "session失效，重定向"),
    ERROR(500, "错误"),
    ERR_INVALID_PARAM(501, "参数不正确"),
    NO_USER_INFO(502, "没有用户登陆");

    public final int code;
    public final String name;

    HttpCode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private final static Map<Integer, HttpCode> valueMap = new HashMap<>();

    static {
        for (HttpCode err : HttpCode.values()) {
            if (valueMap.put(err.code, err) != null) {
                System.out.println("Warnning! Code 重复定义的值, code=" + err.code);
            }
        }
    }

    public static HttpCode valueOf(int code) {
        HttpCode err = valueMap.get(code);
        if (err != null) {
            return err;
        }
        throw new RuntimeException("不存在code=" + code + "的NPConfigError错误");
    }
}
