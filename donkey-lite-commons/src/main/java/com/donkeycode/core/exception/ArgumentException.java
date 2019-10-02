package com.donkeycode.core.exception;


public class ArgumentException extends BaseException {

    private static final long serialVersionUID = -1276435753880524349L;

    public ArgumentException() {
        super();
    }

    public ArgumentException(String message) {
        super(message);
    }

    public ArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentException(Throwable cause) {
        super(cause);
    }

}
