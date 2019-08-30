package com.lego.framework.template.model.entity;

import io.swagger.annotations.ApiModelProperty;

public class FormTemplateHistory extends FormTemplate{

    private static final long serialVersionUID = 7675873664085739041L;

    @ApiModelProperty("标签")
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
