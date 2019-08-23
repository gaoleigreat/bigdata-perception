package com.lego.framework.base.exception;

import com.survey.lib.common.exception.ApiException;

/**
 * @author yanglf
 * @description
 * @since 2018/12/22
 **/
public class CallTimeoutException extends ApiException {

    public CallTimeoutException(String message) {
        super(message);
    }

    public CallTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public CallTimeoutException(Throwable cause) {
        super(cause);
    }

    public CallTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
