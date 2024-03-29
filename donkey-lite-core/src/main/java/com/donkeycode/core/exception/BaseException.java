package com.donkeycode.core.exception;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * @author donkey
 */
@Slf4j
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -3028237343288012355L;

    /**
     * 异常对应的返回码
     */
    private Integer code;

    /**
     * 异常对应的描述信息
     */
    private String message;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
        log.error(" message:{}", message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message);
        this.message = message;
        log.error("message:{}, exception:{}", code, message, JSON.toJSON(cause));
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        log.error("code:{}, message:{}", code, message);
    }

    public BaseException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        log.error("code:{}, message:{}, exception:{}", code, message, JSON.toJSON(cause));
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
