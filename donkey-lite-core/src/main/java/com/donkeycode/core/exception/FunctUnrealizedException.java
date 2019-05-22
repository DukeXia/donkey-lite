package com.donkeycode.core.exception;

public class FunctUnrealizedException extends RuntimeException {

    private static final long serialVersionUID = -8101702898478323573L;

    public FunctUnrealizedException() {
        super();
    }

    public FunctUnrealizedException(String message) {
        super(message);
    }

    public FunctUnrealizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FunctUnrealizedException(Throwable cause) {
        super(cause);
    }

    protected FunctUnrealizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
