package com.lego.framework.template.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class EnumerationItem implements Serializable {

    private static final long serialVersionUID = 1566709955039967982L;

    @ApiModelProperty("枚举值 必填")
    private Integer value;

    @ApiModelProperty("枚举名称 必填")
    private String label;

    @ApiModelProperty("枚举值")
    private String description;

    @ApiModelProperty("枚举Id")
    private Long enumId;

    private List<Long> enumIds;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEnumId() {
        return enumId;
    }

    public void setEnumId(Long enumId) {
        this.enumId = enumId;
    }

    public List<Long> getEnumIds() {
        return enumIds;
    }

    public void setEnumIds(List<Long> enumIds) {
        this.enumIds = enumIds;
    }
}
