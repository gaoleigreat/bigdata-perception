package com.lego.framework.template.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class SearchCondition implements Serializable {

    private static final long serialVersionUID = 7012745545333909916L;

    @ApiModelProperty("字段名称")
    private String fieldName;

    @ApiModelProperty("绝对字段路径")
    private String absoluteField;

    @ApiModelProperty("数据类型")
    private Integer dataType;

    @ApiModelProperty("数据类型")
    private Long enumId;

    @ApiModelProperty("符号")
    private List<ConditionSymbol> symbols;

    @ApiModelProperty("子条件")
    private List<SearchCondition> children;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getAbsoluteField() {
        return absoluteField;
    }

    public void setAbsoluteField(String absoluteField) {
        this.absoluteField = absoluteField;
    }

    public List<SearchCondition> getChildren() {
        return children;
    }

    public void setChildren(List<SearchCondition> children) {
        this.children = children;
    }

    public List<ConditionSymbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<ConditionSymbol> symbols) {
        this.symbols = symbols;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Long getEnumId() {
        return enumId;
    }

    public void setEnumId(Long enumId) {
        this.enumId = enumId;
    }
}
