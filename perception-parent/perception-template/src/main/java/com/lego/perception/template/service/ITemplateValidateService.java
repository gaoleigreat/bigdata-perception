package com.lego.perception.template.service;

import com.lego.framework.template.model.entity.Template;
import com.lego.framework.template.model.entity.ValidateResult;

/**
 * 模板接口
 * weihao 2019-02-26
 */
public interface ITemplateValidateService {

    /**
     * 空校验
     * @param template
     * @return
     */
    ValidateResult validateTemplate(Template template);
}
