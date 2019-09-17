package com.lego.perception.business.listener;

import com.alibaba.fastjson.JSONObject;
import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.event.template.TemplateSink;
import com.lego.framework.event.template.TemplateSource;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.perception.business.service.IBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/6 17:48
 * @desc :
 */
@Slf4j
@EnableBinding({TemplateSink.class, TemplateSource.class})
public class TemplateCreateListener {

    @Autowired
    @Qualifier(value = "mySqlBusinessServiceImpl")
    private IBusinessService mySqlBusinessService;


    @Autowired
    @Qualifier(value = "mongoBusinessServiceImpl")
    private IBusinessService mongoBusinessService;


    @StreamListener(TemplateSink.CREATE_TEMPLATE)
    @SendTo(TemplateSource.CREATE_BUSINESS_TABLE)
    public RespVO<Map> createTemplate(Message<String> message) {
        String payload = message.getPayload();
        log.info("接收到模板创建事件:{}", payload);
        Map map = JSONObject.parseObject(payload, Map.class);
        Integer sourceType = (Integer) map.get("sourceType");
        FormTemplate formTemplate = (FormTemplate) map.get("formTemplate");
        RespVO respVO;
        if (sourceType == 0) {
            respVO = mySqlBusinessService.createBusinessTable(formTemplate);
        } else {
            respVO = mongoBusinessService.createBusinessTable(formTemplate);
        }
        if (respVO.getRetCode() == RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.success(map);
        }
        return RespVOBuilder.failure();
    }

}
