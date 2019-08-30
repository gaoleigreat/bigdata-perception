package com.lego.framework.template.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SearchParam implements Serializable {

    private static final long serialVersionUID = 7012745545333909916L;

    @ApiModelProperty("数据类型")
    private Integer dataType;

    @ApiModelProperty("绝对路径")
    private String absoluteField;

    @ApiModelProperty("值")
    private String value;

    @ApiModelProperty("符号")
    private String symbol;

    public String getAbsoluteField() {
        return absoluteField;
    }

    public void setAbsoluteField(String absoluteField) {
        this.absoluteField = absoluteField;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}
