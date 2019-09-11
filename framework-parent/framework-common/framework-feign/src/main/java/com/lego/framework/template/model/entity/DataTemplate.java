package com.lego.framework.template.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "数据模板")
@TableName(value = "tpl_data_template_t")
public class DataTemplate extends Template {

    @ApiModelProperty("模板数据项")
    @TableField(exist = false)
    private List<DataTemplateItem> items;

    public List<DataTemplateItem> getItems() {
        return items;
    }

    public void setItems(List<DataTemplateItem> items) {
        this.items = items;
    }
}
