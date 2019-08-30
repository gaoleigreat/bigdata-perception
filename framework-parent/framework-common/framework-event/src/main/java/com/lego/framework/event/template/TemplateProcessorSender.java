package com.lego.framework.event.template;

import com.lego.framework.template.model.entity.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author yanglf
 * @description
 * @since 2019/8/30
 **/
@Component
public class TemplateProcessorSender {

    @Autowired
    private TemplateProcessor templateProcessor;

    /**
     * 发送模板创建事件
     *
     * @param name
     * @param desc
     * @param code
     */
    public void sendTemplateCreateEvent(String name,
                                        String desc,
                                        String code) {
        Template template = new Template();
        template.setTemplateName(name);
        template.setDescription(desc);
        template.setTemplateCode(code);
        templateProcessor.createTemplate().send(MessageBuilder.withPayload(template).build());
    }

}
