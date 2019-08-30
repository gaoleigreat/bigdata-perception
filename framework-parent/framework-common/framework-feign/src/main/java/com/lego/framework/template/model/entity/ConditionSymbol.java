package com.lego.framework.template.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ConditionSymbol implements Serializable {

    private static final long serialVersionUID = -8984268092391046021L;

    @ApiModelProperty("显示值")
    private String label;

    @ApiModelProperty("值")
    private String value;

    public ConditionSymbol(String label, String value){
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
