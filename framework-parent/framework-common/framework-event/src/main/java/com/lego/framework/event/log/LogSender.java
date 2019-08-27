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

    public void  sendLogEvent(String ip,String userId,String desc){
        Log log= Log.builder()
                .id(UuidUtils.generateShortUuid())
                .time(new Date())
                .ip(ip)
                .userId(userId)
                .desc(desc).build();
        logSource.printLog().send(MessageBuilder.withPayload(log).build());
    }

}
