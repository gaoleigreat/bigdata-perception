package com.lego.framework.event.template;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Repository;

/**
 * @author yanglf
 * @description
 * @since 2019/8/30
 **/
@Repository
public interface TemplateSink {

    String CREATE_TEMPLATE = "input_create_template";

    /**
     * 创建模板
     *
     * @return
     */
    @Input(CREATE_TEMPLATE)
    SubscribableChannel createTemplate();
}
