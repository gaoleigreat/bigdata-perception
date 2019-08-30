package com.lego.framework.template.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/3/13
 **/
public class EnumerationAttribute implements Serializable {
    private boolean flag;
    private Date lastUpdateDate;
    private List<Enumeration> enumerations;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<Enumeration> getEnumerations() {
        return enumerations;
    }

    public void setEnumerations(List<Enumeration> enumerations) {
        this.enumerations = enumerations;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
