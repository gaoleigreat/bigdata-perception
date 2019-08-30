package com.lego.framework.template.model.entity;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EnumerationModifyInfo implements Serializable {
    private boolean flag;
    @ApiModelProperty("枚举最后更新时间")
    private Date lastUpdateDate;
    private List<TemplateModifyInfo> templateModifyInfos;

    public List<TemplateModifyInfo> getTemplateModifyInfos() {
        return templateModifyInfos;
    }

    public void setTemplateModifyInfos(List<TemplateModifyInfo> templateModifyInfos) {
        this.templateModifyInfos = templateModifyInfos;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
