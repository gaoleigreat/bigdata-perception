package com.lego.framework.system.model.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Dictionary {
    private Long id;
    private Date creationDate;
    private Long createdBy;
    private Date lastUpdateDate;
    private Long lastUpdatedBy;
    private String lastUpdateUser;
    private String createUser;
    private String code;
    private String parentCode;
    private String value;
    private String description;
    private Integer deleteFlag;
    private List<Dictionary> children;

    public void setCreateInfo() {
        Date currentDate=new Date();
        this.creationDate = currentDate;
        this.lastUpdateDate = currentDate;
    }

    public void setUpdateInfo() {
        this.lastUpdateDate = new Date();
    }
}
