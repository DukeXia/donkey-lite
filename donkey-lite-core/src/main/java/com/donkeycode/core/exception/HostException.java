package com.donkeycode.core.exception;

import java.io.IOException;

/**
 * 网络主机异常.
 *
 * @author zhangliang
 */
public final class HostException extends RuntimeException {

    private static final long serialVersionUID = 3589264847881174997L;

    public HostException(final IOException cause) {
        super(cause);
    }
}
