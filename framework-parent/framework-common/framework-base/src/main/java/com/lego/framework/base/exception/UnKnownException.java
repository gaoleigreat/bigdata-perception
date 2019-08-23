package com.lego.framework.base.exception;

import com.survey.lib.common.exception.BaseException;

/**
 * @author yanglf
 * @description
 * @since 2018/12/22
 **/
public class UnKnownException extends BaseException {

   public UnKnownException(String message) {
        super(message);
    }

    public UnKnownException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnKnownException(Throwable cause) {
        super(cause);
    }

    public UnKnownException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
