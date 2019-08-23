package com.lego.framework.base.exception;

import com.survey.lib.common.exception.BaseException;

/**
 * @author yanglf
 * @description
 * @since 2018/12/22
 **/
public class ApiException extends BaseException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
