package com.lego.perception.template.controller;

import com.framework.common.sdto.RespVO;
import com.lego.framework.base.annotation.Resource;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther xiaodao
 * @date 2019/9/3 18:20
 */
@RestController
@RequestMapping("/templateData/v1")
@Resource(value = "templateData", desc = "模板数据管理")
@Api(tags = "templateData", description = "模板数据管理")
@Slf4j
public class TemplateDataController {
    public RespVO uploadData(HttpServletRequest request, Long templateId) {

        return null;
    }

    public RespVO downloadData(Long templateId) {

        return null;
    }

}
