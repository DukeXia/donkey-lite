package com.donkeycode.web;

/**
 * 接口异常
 *
 * @author csf
 */
public class WebException extends RuntimeException {

    private static final long serialVersionUID = 3018409518277889187L;
    public final HttpCode error;

    public WebException(HttpCode error, String message) {
        super(message);
        this.error = error;
    }

    public WebException(HttpCode error, String message, Throwable throwable) {
        super(message, throwable);
        this.error = error;
    }

    public String name() {
        return error.name;
    }

    public int code() {
        return error.code;
    }
}
