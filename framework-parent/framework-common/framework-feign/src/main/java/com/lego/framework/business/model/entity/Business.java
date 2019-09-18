package com.lego.framework.business.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lego.framework.config.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

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
    @ApiModelProperty("业务名称")
    private String name;
    @ApiModelProperty("模板code")
    private String templateCode;
    @ApiModelProperty("类型")
    private Integer type;
    @ApiModelProperty("对应表名称")
    private String tableName;
    @ApiModelProperty("是否删除(0-否;1-是)")
    private Integer deletedFlag;
}
