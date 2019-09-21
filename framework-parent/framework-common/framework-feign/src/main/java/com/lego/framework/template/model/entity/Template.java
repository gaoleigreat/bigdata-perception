package com.lego.framework.template.model.entity;

import com.lego.framework.config.BaseModel;
import io.swagger.annotations.ApiModelProperty;

public class Template extends BaseModel {

    @ApiModelProperty("模板名称")
    private String templateName;

    @ApiModelProperty("模板编码，唯一")
    private String templateCode;

    @ApiModelProperty("模板名称")
    private String description;

    @ApiModelProperty("数据源类型")
    private Integer type;


    @ApiModelProperty("数据类型")
    private Integer dataType;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}
