package com.lego.framework.template.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/5/8
 **/
public class EnumerationTreeVo implements Serializable {
    @ApiModelProperty("枚举id")
    private Long id;
    @ApiModelProperty("枚举子项value")
    private Integer value;
    @ApiModelProperty("lable")
    private String label;
    private List<EnumerationTreeVo> enumerationTreeVos;

    public EnumerationTreeVo(Long id,Integer value,String label) {
        this.id = id;
        this.value=value;
        this.label=label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<EnumerationTreeVo> getEnumerationTreeVos() {
        return enumerationTreeVos;
    }

    public void setEnumerationTreeVos(List<EnumerationTreeVo> enumerationTreeVos) {
        this.enumerationTreeVos = enumerationTreeVos;
    }
}
