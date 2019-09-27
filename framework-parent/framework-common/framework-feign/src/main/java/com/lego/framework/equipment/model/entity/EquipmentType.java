package com.lego.framework.equipment.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lego.framework.config.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/24 10:41
 * @desc :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tpl_equipment_type")
public class EquipmentType extends BaseModel {
    @ApiModelProperty("设备类型id")
    private Long id;
    @ApiModelProperty("设备类型名称")
    private String name;
    @ApiModelProperty("设备类型编码")
    private String code;
    @ApiModelProperty("设备类型描述")
    private String description;
    @ApiModelProperty("业务类型对应表单模板")
    private Integer type;
    @ApiModelProperty("模板code")
    @TableField(exist = false)
    private String templateCode;
}
