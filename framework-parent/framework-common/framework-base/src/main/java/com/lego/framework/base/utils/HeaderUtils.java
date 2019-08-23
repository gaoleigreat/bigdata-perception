package com.lego.framework.base.utils;

import com.survey.lib.common.vo.HeaderVo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

import static com.survey.lib.common.consts.HttpConsts.*;

/**
 * @author yanglf
 * @description
 * @since 2018/12/27
 **/
@Slf4j
public class HeaderUtils {

    public static HeaderVo parseHeader(HttpServletRequest request) {
        try {
            String deviceType = request.getHeader(DEVICE_TYPE);
            String sn = request.getHeader(HEADER_SN);
            String time = request.getHeader(HEADER_TIME);
            String token = request.getHeader(HEADER_TOKEN);
            String osVersion = request.getHeader(OS_VERSION);
            String fromName = request.getHeader(FROM_NAME);
            String userId = request.getHeader(USER_ID);
            String userName = request.getHeader(USER_NAME);
            return HeaderVo.builder()
                    .deviceType(deviceType)
                    .osVersion(osVersion)
                    .sn(sn)
                    .time(time)
                    .token(token)
                    .userId(userId)
                    .userName(userName)
                    .fromName(fromName)
                    .build();
        } catch (Exception ex) {
            log.error("header参数解析失败:{}",ex);
            return null;
        }
    }





}
