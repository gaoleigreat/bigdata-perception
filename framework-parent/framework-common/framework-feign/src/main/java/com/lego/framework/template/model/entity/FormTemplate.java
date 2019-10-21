package com.lego.framework.template.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@TableName(value = "tpl_form_template_t")
public class FormTemplate extends Template implements Serializable {

    @ApiModelProperty("数据模板id")
    private Long dataTemplateId;

    @ApiModelProperty("类型，1：查询，2：采集")
    private Long category;

    @ApiModelProperty("权限")
    private String permission;

    @ApiModelProperty("模板项")
    @TableField(exist = false)
    private List<FormTemplateItem> items;

    public Long getDataTemplateId() {
        return dataTemplateId;
    }

    public void setDataTemplateId(Long dataTemplateId) {
        this.dataTemplateId = dataTemplateId;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public List<FormTemplateItem> getItems() {
        return items;
    }

    public void setItems(List<FormTemplateItem> items) {
        this.items = items;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
