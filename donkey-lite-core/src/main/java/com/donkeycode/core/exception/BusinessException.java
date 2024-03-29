package com.donkeycode.core.exception;

/**
 * @author donkey
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = -1276435753880524349L;

    /**
     * 无参构造函数
     */
    public BusinessException() {
        super();
    }

    /**
     * 指定消息
     *
     * @param message 异常内容
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * 指定消息 &amp; 异常
     *
     * @param message 异常内容
     * @param cause   异常
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 指定异常
     *
     * @param cause 异常
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }

}
