package com.lego.framework.equipment.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author itar
 * @mail wuhandzy@gmail.com
 * @date 2019-10-06 09:29:09
 * @since jdk1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentMaintenance {
    /**
     * 设备保养id
     */
    @ApiModelProperty("设备保养id")
    private Long id;
    /**
     * 保养项目
     */
    @ApiModelProperty("保养项目")
    private String project;
    /**
     * 保养时间
     */
    @ApiModelProperty("保养时间")
    private Date operateDate;
    /**
     * 保养人
     */
    @ApiModelProperty("保养人")
    private Long operateBy;
    /**
     * 所在项目
     */
    @ApiModelProperty("所在项目")
    private Long projectId;
    /**
     * 主管领导
     */
    @ApiModelProperty("主管领导")
    private String supervisor;
    /**
     * 完成情况
     */
    @ApiModelProperty("完成情况")
    private String completeStatus;
    /**
     * 说明
     */
    @ApiModelProperty("说明")
    private String remark;
    /**
     * 设备类型
     */
    @ApiModelProperty("设备类型")
    private Long equipmentId;
    /**
     * 设备号
     */
    @ApiModelProperty("设备号")
    private String equipmentCode;
    /**
     * 录入时间
     */
    @ApiModelProperty("录入时间")
    private Date creationDate;
    /**
     * 录入人
     */
    @ApiModelProperty("录入人")
    private Long createdBy;
}
