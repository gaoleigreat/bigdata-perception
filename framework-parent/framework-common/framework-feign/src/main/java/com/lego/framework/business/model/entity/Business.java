package com.lego.framework.business.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lego.framework.config.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/17 18:05
 * @desc :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tpl_business")
public class Business extends BaseModel {
    private String name;
    private String templateCode;
    private Integer type;
    private String tableName;
}
