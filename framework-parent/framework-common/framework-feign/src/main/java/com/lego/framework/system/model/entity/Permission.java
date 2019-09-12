package com.lego.framework.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tpl_permission_t")
public class Permission {
    private Long id;
    private String rId;
    private String rName;
    private String prId;
    private String prName;
    private String scope;
    private Date creationDate;
}
