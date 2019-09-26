package com.lego.framework.equipment.model.entity;

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
@TableName(value = "tpl_equipment")
public class Equipment extends BaseModel {
    @ApiModelProperty("设备id")
    private Long id;
    @ApiModelProperty("设备名称")
    private String name;
    @ApiModelProperty("设备编码")
    private String code;
    @ApiModelProperty("设备描述")
    private String description;
    @ApiModelProperty("业务模板类型")
    private Integer type;
}
