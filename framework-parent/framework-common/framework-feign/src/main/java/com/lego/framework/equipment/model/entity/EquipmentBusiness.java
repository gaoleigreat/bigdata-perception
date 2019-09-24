package com.lego.framework.equipment.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lego.framework.config.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author itar
 * @mail wuhandzy@gmail.com
 * @date 2019-09-24 10:07:49
 * @since jdk1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tpl_equipment_business")
public class EquipmentBusiness extends BaseModel {
    @ApiModelProperty("id")
    private Long id;
    /**
     * 设备id
     */
    @ApiModelProperty("设备id")
    private Long equipmentId;
    /**
     * 业务id
     */
    @ApiModelProperty("业务id")
    private Long businessId;
    /**
     * (1-新增;2-删除;3-更新;4-查询;5-导入;6-导出)
     */
    @ApiModelProperty(value = "操作类型((1-新增;2-删除;3-更新;4-查询;5-导入;6-导出))", example = "1,2,3")
    private String operationType;
}
