package com.donkeycode.boot.file;

public class ExcelHandleException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExcelHandleException() {
    }

    public ExcelHandleException(String message) {
        super(message);
    }

    public ExcelHandleException(Throwable cause) {
        super(cause);
    }

    public ExcelHandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelHandleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
