package com.lego.perception.log.listener;
import com.alibaba.fastjson.JSONObject;
import com.lego.framework.event.log.LogSink;
import com.lego.framework.log.model.entity.Log;
import com.lego.perception.log.service.ILogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

/**
 * @author yanglf
 * @description
 * @since 2018/12/27
 **/
@Slf4j
@EnableBinding(LogSink.class)
public class LogListener {

    @Autowired
    private ILogService iLogService;


    @StreamListener(LogSink.PRINT_LOG)
    public void logReceive(Message<String> message) {
        //  接收到日志事件
        log.info("接收到日志记录事件:{}", message.getPayload());
        Log log = JSONObject.parseObject(message.getPayload(), Log.class);
        iLogService.add(log);
    }


}
