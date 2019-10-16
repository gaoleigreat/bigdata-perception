package com.lego.framework.equipment.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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


    @ApiModelProperty("所在项目id")
    private Long projectId;


    @TableField(exist = false)
    @ApiModelProperty("所在项目名")
    private String projectName;

    /**
     * 设备编号
     */
    @ApiModelProperty(value = "设备编号")
    private String equipmentCode;

    @TableField(exist = false)
    @ApiModelProperty("设备名称")
    private String equipmentName;

    /**
     * 状态(0-计划;1-进行;2-审核;3-完成)
     */
    @ApiModelProperty(value = "状态(0-计划;1-进行;2-审核;3-完成)")
    private Integer status;

    /**
     * 设备类型Id
     */
    @ApiModelProperty(value = "设备类型Id")
    private Long equipmentTypeId;


    @TableField(exist = false)
    private Integer templateType;

    @TableField(exist = false)
    @ApiModelProperty("设备类型名称")
    private String typeName;

    /**
     * 故障id
     */
    @ApiModelProperty(value = "故障id")
    private Long malfunctionId;


    @TableField(exist = false)
    @ApiModelProperty("维修系统")
    private String sys;

    @TableField(exist = false)
    @ApiModelProperty("故障现象")
    private String phenomenon;

    @ApiModelProperty("开始时间")
    private Date startDate;

    @ApiModelProperty("负责人")
    private String principal;


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
