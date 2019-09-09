package com.lego.perception.template.listener;

import com.alibaba.fastjson.JSONObject;
import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.lego.framework.event.template.TemplateSink;
import com.lego.framework.event.template.TemplateSource;
import com.lego.framework.template.model.entity.BusinessTemplate;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.perception.template.service.IBusinessTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.Date;
import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/6 17:33
 * @desc :
 */
@Slf4j
@EnableBinding({TemplateSink.class})
public class BusinessTableCreateListener {

    @Autowired
    private IBusinessTemplateService iBusinessTemplateService;


    @StreamListener(TemplateSink.CREATE_BUSINESS_TABLE)
    public void createTemplate(Message<String> message) {
        String payload = message.getPayload();
        log.info("接收到创建业务表事件:{}", payload);
        RespVO<Map<String, Object>> respVO = JSONObject.parseObject(payload, RespVO.class);
        if (respVO.getRetCode() == RespConsts.SUCCESS_RESULT_CODE) {
            Map<String, Object> info = respVO.getInfo();
            if (info != null) {
                // 新增  业务  模板关联表
                Integer sourceType = (Integer) info.get("sourceType");
                FormTemplate template = (FormTemplate) info.get("formTemplate");
                BusinessTemplate businessTemplate = new BusinessTemplate();
                businessTemplate.setSourceType(sourceType);
                businessTemplate.setTableName(template.getDescription());
                businessTemplate.setTemplateType(0);
                businessTemplate.setTemplateId(template.getId());
                businessTemplate.setCreationDate(new Date());
                iBusinessTemplateService.insert(businessTemplate);
            }
        }
    }

}
