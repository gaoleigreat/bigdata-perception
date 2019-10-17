package com.lego.framework.equipment.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author itar
 * @mail wuhandzy@gmail.com
 * @date 2019-10-08 06:16:21
 * @since jdk1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tpl_fitting")
public class Fitting {
    /**
     * 配件id
     */
    @ApiModelProperty("配件id")
    private Long id;
    /**
     * 所属系统
     */
    @ApiModelProperty("所属系统")
    private String sys;
    /**
     * 中心物料号
     */
    @ApiModelProperty("中心物料号")
    private String centerMaterialsNum;
    /**
     * 出厂物料号
     */
    @ApiModelProperty("出厂物料号")
    private String factoryMaterialNum;
    /**
     * 物料名称
     */
    @ApiModelProperty("物料名称")
    private String materialName;
    /**
     * 物料描述
     */
    @ApiModelProperty("物料描述")
    private String materialDesc;
    /**
     * 单位
     */
    @ApiModelProperty("单位")
    private String unit;
    /**
     * 数量
     */
    @ApiModelProperty("数量")
    private Integer count;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
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
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date lastUpdateDate;
    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private Long lastUpdatedBy;
}
