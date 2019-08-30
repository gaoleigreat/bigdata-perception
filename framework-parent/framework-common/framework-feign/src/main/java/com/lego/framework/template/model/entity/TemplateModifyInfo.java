package com.lego.framework.template.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yanglf
 * @description
 * @since 2019/4/4
 **/
public class TemplateModifyInfo implements Serializable {
    @ApiModelProperty("模板编码")
    private String templateCode;
    @ApiModelProperty("最后更新时间")
    private Date lastUpdateDate;
    @ApiModelProperty("类型(1-表单;2-数据)")
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
