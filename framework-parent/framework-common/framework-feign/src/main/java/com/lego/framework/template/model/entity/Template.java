package com.lego.framework.template.model.entity;

import com.lego.framework.config.BaseModel;
import io.swagger.annotations.ApiModelProperty;

public class Template extends BaseModel {

    private static final long serialVersionUID = 3117406673485233512L;

    @ApiModelProperty("模板名称")
    private String templateName;

    @ApiModelProperty("模板编码，唯一")
    private String templateCode;

    @ApiModelProperty("模板名称")
    private String description;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
