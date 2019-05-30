package com.donkeycode.web;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回前端数据类型.
 */
@Setter
@Getter
public class ResponseCode {

    private int code;
    private String message;
    private Object data;

    public ResponseCode(HttpCode code, String message, Object data) {
        this.code = code.code;
        this.message = message;
        this.data = data;
    }

    public static ResponseCode returnResponse(HttpCode code, String message, Object data) {
        return new ResponseCode(code, message, data);
    }

    public static ResponseCode returnResponse(HttpCode code, String message) {
        return returnResponse(code, message, null);
    }
}
