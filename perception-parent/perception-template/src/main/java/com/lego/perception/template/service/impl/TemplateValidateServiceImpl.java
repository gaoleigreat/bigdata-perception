package com.lego.perception.template.service.impl;
import com.lego.framework.template.model.entity.Template;
import com.lego.framework.template.model.entity.ValidateResult;
import com.lego.perception.template.service.ITemplateValidateService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TemplateValidateServiceImpl implements ITemplateValidateService {

    @Override
    public ValidateResult validateTemplate(Template template) {
        ValidateResult v = new ValidateResult();
        if(StringUtils.isEmpty(template.getTemplateCode())){
            v.setMsg("模板编码不能为空");
            v.setResult(false);
            return v;
        }

        if(StringUtils.isEmpty(template.getTemplateName())){
            v.setMsg("模板名称不能为空");
            v.setResult(false);
            return v;
        }
        v.setResult(true);
        return v;
    }
}
