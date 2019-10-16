package com.lego.framework.equipment.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author itar
 * @mail wuhandzy@gmail.com
 * @date 2019-10-12 02:51:23
 * @since jdk1.8
 */
@Data
@TableName("tpl_equipment_service_record")
public class EquipmentServiceRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 维修记录Id
     */
    @ApiModelProperty("维修记录Id")
    @TableId
    private Long id;


    @ApiModelProperty("工程id")
    private Long projectId;

    @TableField(exist = false)
    @ApiModelProperty("工程名称")
    private String projectName;

    /**
     * 设备编号
     */
    @ApiModelProperty("设备编号")
    private String equipmentCode;

    @TableField(exist = false)
    @ApiModelProperty("设备名称")
    private String euipmentName;


    @ApiModelProperty(value = "设备类型Id")
    private Long equipmentTypeId;

    @ApiModelProperty("设备类型名称")
    @TableField(exist = false)
    private String equipmentTypeName;

    /**
     * 状态(0-计划;1-进行;2-审核;3-完成)
     */
    @ApiModelProperty("状态(0-计划;1-进行;2-审核;3-完成)")
    private Integer status;
    /**
     * 故障id
     */
    @ApiModelProperty("故障id")
    private Long malfunctionId;
    /**
     * 维修附件文档批次号
     */
    @ApiModelProperty("维修附件文档批次号")
    private String batchNumber;
    /**
     * 更换辅件，数组格式用，隔开
     */
    @ApiModelProperty("更换辅件，数组格式用，隔开")
    private String centerMaterialsNums;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;


    @ApiModelProperty("负责人")
    private String principal;

    @ApiModelProperty("开始时间")
    private Date startDate;


    @ApiModelProperty("结束时间")
    private Date endDate;


    /**
     * 维修Id
     */
    private Long equipmentServiceId;
    /**
     * 记录时间
     */
    @ApiModelProperty("记录时间")
    private Date creationDate;
    /**
     * 记录人
     */
    @ApiModelProperty("记录人")
    private Long creationBy;

}
