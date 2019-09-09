package com.lego.framework.event.template;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Repository;

/**
 * @author yanglf
 * @description
 * @since 2019/8/30
 **/
@Repository
public interface TemplateSource {

    String CREATE_TEMPLATE = "output_create_template";

    String CREATE_BUSINESS_TABLE = "output_create_table";

    /**
     * 创建模板
     *
     * @return
     */
    @Output(CREATE_TEMPLATE)
    MessageChannel createTemplate();


    /**
     * 创建业务表
     *
     * @return
     */
    @Output(CREATE_BUSINESS_TABLE)
    MessageChannel createBusinessTable();
}
