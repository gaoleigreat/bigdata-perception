package com.lego.framework.template.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchParam {
    @ApiModelProperty("数据类型")
    private Integer dataType;
    @ApiModelProperty("绝对路径")
    private String absoluteField;
    @ApiModelProperty("值")
    private String value;
    @ApiModelProperty("符号")
    private String symbol;
}
