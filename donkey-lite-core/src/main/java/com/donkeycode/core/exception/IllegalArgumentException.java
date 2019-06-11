package com.donkeycode.core.exception;

/**
 * @author donkey
 */
public class IllegalArgumentException extends BaseException {

    private static final long serialVersionUID = -1276435753880524349L;

    public IllegalArgumentException() {
        super();
    }

    public IllegalArgumentException(String message) {
        super(message);
    }

    public IllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArgumentException(Throwable cause) {
        super(cause);
    }

}
