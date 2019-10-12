package com.lego.framework.equipment.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tpl_equipment_service")
public class EquipmentService {
    /**
     * 设备维修id
     */
    @ApiModelProperty(value = "设备维修id")
    @TableId
    private Long id;
    /**
     * 设备编号
     */
    @ApiModelProperty(value = "设备编号")
    private String equipmentCode;
    /**
     * 状态(0-计划;1-进行;2-完成)
     */
    @ApiModelProperty(value = "状态(0-计划;1-进行;2-完成)")
    private Integer status;
    /**
     * 故障id
     */
    @ApiModelProperty(value = "故障id")
    private Long malfunctionId;
    /**
     * 维修附件文档，数组格式用，隔开
     */
    @ApiModelProperty(value = "维修附件文档")
    private String batchNumber;
    /**
     * 更换辅件，数组格式用，隔开
     */
    @ApiModelProperty(value = "更换辅件，数组格式用，隔开")
    private String centerMaterialsNums;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 记录时间
     */
    @ApiModelProperty(value = "记录时间")
    private Date creationDate;
    /**
     * 记录人
     */
    @ApiModelProperty(value = "记录人")
    private Long creationBy;
}
