package com.lego.framework.template.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCondition{
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
}
