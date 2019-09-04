package com.lego.framework.base.utils;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Version : 1.0
 * Author  : yanglf
 * Date    : 2019/9/2 17:25
 * Desc    :  日志链路
 */
public class TraceUtil {

    public static void traceStart() {
        String traceId = generateTraceId();
        MDC.put("traceId", traceId);
    }

    public static void traceEnd() {
        MDC.clear();
    }

    public static String getTraceId(){
        return String.valueOf(MDC.get("traceId"));
    }


    /**
     * 生成跟踪ID
     */
    private static String generateTraceId() {
        return UUID.randomUUID().toString();
    }

}
