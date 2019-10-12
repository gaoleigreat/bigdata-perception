package com.lego.framework.equipment.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tpl_equipment_cost")
public class EquipmentCost {
    /**
     * 设备费用id
     */
    @ApiModelProperty("设备费用id")
    @TableId
    private Long id;
    /**
     * 设备编号
     */
    @ApiModelProperty("设备编号")
    private String equipmentCode;
    /**
     * 类型(1-采购费;2-开累使用费；3-开累折旧费；4-开累维修费；5-报废处置费；6-仓储费)
     */
    @ApiModelProperty("类型(1-采购费;2-开累使用费；3-开累折旧费；4-开累维修费；5-报废处置费；6-仓储费)")
    private String type;
    /**
     * 金额
     */
    @ApiModelProperty("金额")
    private BigDecimal amount;
    /**
     * 关联id
     */
    @ApiModelProperty("关联id")
    private Long relativeId;
    /**
     * 工程
     */
    @ApiModelProperty("工程")
    private Long projectId;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     * 录入人
     */
    @ApiModelProperty("录入人")
    private Long createdBy;
    /**
     * 录入时间
     */
    @ApiModelProperty("录入时间")
    private Date creationDate;
    /**
     * 最后修改人
     */
    @ApiModelProperty("最后修改人")
    private Long lastUpdatedBy;
    /**
     * 最后更新时间
     */
    @ApiModelProperty("最后更新时间")
    private Date lastUpdateDate;
}
