package com.lego.framework.base.consts;

/**
 * @author yanglf
 * @description 响应信息定义
 * @since 2018/12/22
 **/
public class RespConsts {


    public static final String SUCCESS = "success";
    public static final String FAILURE = "fail";


    public static final int SUCCESS_RESULT_CODE = 1;
    public static final String SUCCESS_RESULT_MSG = "请求成功";
    /**
     * 权限认证失败
     */
    public static final int FAIL_NOPRESSION_CODE = -1;
    public static final String FAIL_NOPRESSION_MSG = "权限校验失败，请联系管理员";

    /**
     * 登录失败  msg 为详细错误原因
     */
    public static final int FAIL_LOGIN_CODE = -2;

    /**
     * 服务内部错误
     */
    public static final int ERROR_SERVER_CODE = -3;
    /**
     * 调用超时
     */
    public static final int ERROR_CALLTIMEOUT_CODE = -4;

    /**
     * 其他错误
     */
    public static final int ERROR_OTHER_CODE = -5;

    /**
     * 结果错误
     */
    public static final int FAIL_RESULT_CODE = -6;

    /**
     * 数据异常
     */
    public static final int DATA_ERROR = -7;

    /**
     * 失败
     */
    public interface Failure {
        String ret = FAILURE;
        int retCode = FAIL_RESULT_CODE;
        String msg = "请求失败";
    }

    /**
     * 成功
     */
    public interface Success {
        String ret = SUCCESS;
        int retCode = SUCCESS_RESULT_CODE;
        String msg = "请求成功";
    }


    public interface DataErrorType {
        /**
         * id 重复  异常
         */
        int REPEAT_ID_ERROR = 1;
        /**
         * 数据类型异常
         */
        int DATA_TYPE_ERROR = 2;
    }

}
