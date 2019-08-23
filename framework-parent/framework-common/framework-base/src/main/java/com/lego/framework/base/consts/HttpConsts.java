package com.lego.framework.base.consts;
/**
 * @author yanglf
 * @description  header 定义
 * @since 2018/12/22
 **/
public class HttpConsts {

    /**
     * token
     */
    public static final String HEADER_TOKEN = "token";

    /**
     * 当前时间
     */
    public static final String HEADER_TIME="timeStamp";


    /**
     * 设备类型 (1- Android ; 2- web)
     */
    public  static final String DEVICE_TYPE="deviceType";


    /**
     * 设备系统版本
     */
    public static  final String OS_VERSION="osVersion";

    /**
     * 设备号
     */
    public static final String HEADER_SN="sn";

    public static final String FROM_NAME="fromName";

    public static final String USER_ID="userId";

    public static final String USER_NAME="userName";





    public  interface  DeviceType{
        String DEVICE_ANDROID="1";
        String DEVICE_WEB="2";
    }



}
