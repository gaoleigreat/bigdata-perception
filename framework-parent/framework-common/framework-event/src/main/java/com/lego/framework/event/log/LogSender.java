package com.lego.framework.event.log;

import com.framework.common.consts.HttpConsts;
import com.framework.common.utils.HttpUtils;
import com.lego.framework.log.model.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
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
     * @param request request
     * @param desc    描述信息
     * @param content 日志内容
     * @param service 所属服务
     * @param tag     标签
     * @param type    类型
     */
    public void sendLogEvent(HttpServletRequest request,
                             String desc,
                             String content,
                             String service,
                             String tag,
                             String type) {
        String userId = request.getHeader(HttpConsts.USER_ID);
        String userName = request.getHeader(HttpConsts.USER_NAME);
        Log log = Log.builder()
                .ip(HttpUtils.getClientIp(request))
                .desc(desc)
                .operatingTime(new Date())
                .content(content)
                .service(service)
                .tag(tag)
                .type(type)
                .build();
        if (StringUtils.isEmpty(userId)) {
            log.setUserId(Long.valueOf(userId));
        }
        if (StringUtils.isEmpty(userName)) {
            log.setUserName(userName);
        }
        logSource.printLog().send(MessageBuilder.withPayload(log).build());
    }

}
