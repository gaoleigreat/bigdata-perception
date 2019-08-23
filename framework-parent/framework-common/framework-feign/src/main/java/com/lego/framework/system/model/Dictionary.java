package com.lego.framework.system.model;
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


    }

    public void setUpdateInfo() {

    }
}
