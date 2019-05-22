package com.donkeycode.core.exception;

import com.donkeycode.core.exception.BaseException;

public class FactoryException extends BaseException {

    private static final long serialVersionUID = -1;

    public FactoryException(String message) {
        super(message);
    }

    public FactoryException(String message, Throwable cause) {
        super(message);
    }
}
