package com.lego.framework.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName(value = "tpl_dictionary_t")
public class Dictionary {
    private Long id;
    private Date creationDate;
    private Long createdBy;
    private Date lastUpdateDate;
    private Long lastUpdatedBy;
    @TableField(exist = false)
    private String lastUpdateUser;
    @TableField(exist = false)
    private String createUser;
    private String code;
    private String parentCode;
    private String value;
    private String description;
    private Integer deleteFlag;
    @TableField(exist = false)
    private List<Dictionary> children;

    public void setCreateInfo() {
        Date currentDate = new Date();
        this.creationDate = currentDate;
        this.lastUpdateDate = currentDate;
    }

    public void setUpdateInfo() {
        this.lastUpdateDate = new Date();
    }
}
