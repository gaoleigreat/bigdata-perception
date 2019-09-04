package com.lego.framework.template.model.entity;
import io.swagger.annotations.ApiModelProperty;

public class DataTemplateHistory extends DataTemplate{

    private static final long serialVersionUID = -3855147391958434822L;

    @ApiModelProperty("标签")
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
