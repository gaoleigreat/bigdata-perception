package com.lego.framework.equipment.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author itar
 * @mail wuhandzy@gmail.com
 * @date 2019-10-06 09:29:16
 * @since jdk1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentStopReason{
    /**
     * 设备停机原因id
     */
    @ApiModelProperty("设备停机原因id")
    private Long id;
    /**
     * 设备分类id
     */
    @ApiModelProperty("设备分类id")
    private Long equipmentId;
    /**
     * 设备号
     */
    @ApiModelProperty("设备号")
    private String equipmentCode;
    /**
     * 故障时间
     */
    @ApiModelProperty("故障时间")
    private Date malfunctionDate;
    /**
     * 当日故障时间总计(小时)
     */
    @ApiModelProperty("当日故障时间总计(小时)")
    private BigDecimal totalHours;
    /**
     * 当日故障现象描述
     */
    @ApiModelProperty("当日故障现象描述")
    private String malfunctionPhenomenon;
    /**
     * 当日故障原因描述
     */
    @ApiModelProperty("当日故障原因描述")
    private String malfunctionReason;
    /**
     * 当日故障处理措施
     */
    @ApiModelProperty("当日故障处理措施")
    private String handleWay;
    /**
     * 主管负责人
     */
    @ApiModelProperty("主管负责人")
    private String principal;
    /**
     * 负责人联系方式
     */
    @ApiModelProperty("负责人联系方式")
    private String principalPhone;
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
