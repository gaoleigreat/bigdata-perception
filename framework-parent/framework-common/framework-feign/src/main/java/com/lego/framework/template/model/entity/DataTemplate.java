package com.lego.framework.template.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "数据模板")
public class DataTemplate extends Template {

    private static final long serialVersionUID = 3180377090477652192L;

    @ApiModelProperty("模板数据项")
    private List<DataTemplateItem> items;

    public List<DataTemplateItem> getItems() {
        return items;
    }

    public void setItems(List<DataTemplateItem> items) {
        this.items = items;
    }
}
