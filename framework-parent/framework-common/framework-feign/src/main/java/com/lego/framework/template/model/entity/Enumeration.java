package com.lego.framework.template.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lego.framework.config.BaseModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@TableName(value = "tpl_enum_t")
public class Enumeration extends BaseModel {
    @ApiModelProperty("枚举编码")
    private String enumCode;

    @ApiModelProperty("枚举名称")
    private String enumName;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("枚举列表")
    @TableField(exist = false)
    private List<EnumerationItem> items;

    public String getEnumCode() {
        return enumCode;
    }

    public void setEnumCode(String enumCode) {
        this.enumCode = enumCode;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public List<EnumerationItem> getItems() {
        return items;
    }

    public void setItems(List<EnumerationItem> items) {
        this.items = items;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
