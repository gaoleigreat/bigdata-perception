package com.lego.framework.base.sdto;
import com.lego.framework.base.consts.RespConsts;

import java.lang.reflect.Field;
import java.util.List;

import static com.lego.framework.base.consts.RespConsts.*;

/**
 * @author yanglf
 * @description
 * @since 2018/12/22
 **/
public class RespVOBuilder {

    /**
     * 成功
     *
     * @param <T>
     * @return
     */
    public static <T> RespVO<T> success() {
        return new RespVO<>(SUCCESS, SUCCESS_RESULT_CODE ,SUCCESS_RESULT_MSG);
    }

    /**
     * 成功
     *
     * @param info
     * @param <T> POJO
     * @return
     */
    public static <T> RespVO<T> success(T info) {
        return new RespVO<>(SUCCESS,SUCCESS_RESULT_CODE, SUCCESS_RESULT_MSG, info);
    }

    /**
     * 成功
     * @param info
     * @param <T> List
     * @return
     */
    public static <T> RespVO<RespDataVO<T>> success(List<T> info) {
        return new RespVO<>(SUCCESS,SUCCESS_RESULT_CODE, SUCCESS_RESULT_MSG, new RespDataVO<>(info));
    }


    /**
     * 成功
     *
     * @param <T> POJO
     * @return
     */
    public static <T> RespVO<T> success(int retCode, String msg) {
        return new RespVO<>(SUCCESS,retCode, msg);
    }


    /**
     * 失败
     *
     * @param <T>
     * @return
     */
    public static <T> RespVO<T> failure() {
        return failure(RespConsts.Failure.class);
    }

    /**
     * 失败
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> RespVO<T> failure(String msg) {
        return new RespVO<>(FAILURE, FAIL_RESULT_CODE,msg);
    }

    /**
     * 失败
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> RespVO<T> failure(int retCode, String msg) {
        return new RespVO<>(FAILURE,retCode ,msg);
    }

    /**
     * 失败并返回数据
     * @param msg
     * @param info
     * @param <T>
     * @return
     */
    public static <T> RespVO<T> failureData(String msg, T info) {
        return new RespVO<>(FAILURE,FAIL_RESULT_CODE, msg, info);
    }

    /**
     * 失败
     *
     * @param clazz *Consts中定义的接口
     * @param <T>
     * @return
     */
    public static <T> RespVO<T> failure(Class clazz) {
        return fail(clazz);
    }


    /**
     * 失败
     *
     * @param clazz *Consts中定义的接口
     * @param <T>
     * @return
     */
    public static <T> RespVO<T> fail(Class clazz) {
        String ret = null;
        String msg = null;
        int retCode=-5;
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                if (field.getName().equals("ret")) {
                    ret = String.valueOf(field.get(clazz));
                }
                if(field.getName().equals("retCode")){
                    retCode=Integer.valueOf(field.get(clazz)+"");
                }
                if (field.getName().equals("msg")) {
                    msg = String.valueOf(field.get(clazz));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RespVO<>(ret,retCode, msg);
    }
}
