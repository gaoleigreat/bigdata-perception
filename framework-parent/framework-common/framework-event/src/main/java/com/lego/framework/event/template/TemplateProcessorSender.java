package com.lego.framework.event.template;

import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanglf
 * @description
 * @since 2019/8/30
 **/
@Component
public class TemplateProcessorSender {

    @Autowired
    private TemplateSource templateSource;

    /**
     * 发送模板创建事件
     *
     * @param formTemplate
     * @param sourceType
     */
    public void sendTemplateCreateEvent(FormTemplate formTemplate,
                                        Integer sourceType) {
        Map<String, Object> map = new HashMap<>();
        map.put("formTemplate", formTemplate);
        map.put("sourceType", sourceType);
        templateSource.createTemplate().send(MessageBuilder.withPayload(map).build());
    }

}
