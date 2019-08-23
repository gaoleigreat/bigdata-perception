package com.lego.framework.base.exception;

import com.survey.lib.common.exception.ApiException;

/**
 * @author yanglf
 * @description
 * @since 2018/12/25
 **/
public class OperateFailException extends ApiException {

    public OperateFailException(String message) {
        super(message);
    }

    public OperateFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperateFailException(Throwable cause) {
        super(cause);
    }

    public OperateFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
