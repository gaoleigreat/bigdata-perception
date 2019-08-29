package com.lego.framework.event.log;

import com.framework.common.utils.UuidUtils;
import com.lego.framework.log.model.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author yanglf
 * @description
 * @since 2019/1/4
 **/
@Component
public class LogSender {

    @Autowired
    private LogSource logSource;

    /**
     * @param ip            请求ID
     * @param userId        操作用户ID
     * @param desc          描述
     * @param content       日志内容
     * @param service       发送者所属服务
     * @param tag           日志 tag
     * @param type          日志类型
     * @param operatingTime 日志操作时间
     * @param userName      操作用户用户名
     */
    public void sendLogEvent(String ip,
                             Long userId,
                             String desc,
                             String content,
                             String service,
                             String tag,
                             String type,
                             Date operatingTime,
                             String userName) {
        Log log = Log.builder()
                .ip(ip)
                .userId(userId)
                .userName(userName)
                .desc(desc)
                .operatingTime(operatingTime)
                .content(content)
                .service(service)
                .tag(tag)
                .type(type)
                .build();
        logSource.printLog().send(MessageBuilder.withPayload(log).build());
    }

}
