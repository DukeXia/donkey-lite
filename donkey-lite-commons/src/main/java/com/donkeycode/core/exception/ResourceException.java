package com.donkeycode.core.exception;

/**
 * 资源异常
 *
 * @author donkey
 * @since 2019年5月16日
 */
public class ResourceException extends BaseException {

    private static final long serialVersionUID = -1L;

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(String message, Throwable cause) {
        super(message);
    }

}