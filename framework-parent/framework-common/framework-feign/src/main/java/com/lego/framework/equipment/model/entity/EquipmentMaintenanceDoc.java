package com.lego.framework.equipment.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author itar
 * @mail wuhandzy@gmail.com
 * @date 2019-10-06 09:29:13
 * @since jdk1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentMaintenanceDoc{
    /**
     * 设备保养手册id
     */
    @ApiModelProperty("设备保养手册id")
    private Long id;

    @ApiModelProperty("设备类型id")
    private Long equipmentId;

    @TableField(exist = false)
    @ApiModelProperty("设备类型编码")
    private String typeCode;
    /**
     *
     */
    @ApiModelProperty("文件id")
    private Long fileId;
    /**
     * 保养系统
     */
    @ApiModelProperty("保养系统")
    private String sys;
    /**
     * 保养部位
     */
    @ApiModelProperty("保养部位")
    private String part;
    /**
     * 保养方法及措施
     */
    @ApiModelProperty("保养方法及措施")
    private String method;
    /**
     * 保养辅料
     */
    @ApiModelProperty("保养辅料")
    private String excipient;
    /**
     * 保养周期
     */
    @ApiModelProperty("保养周期")
    private String cycle;
    /**
     * 保养分类(1-初保;2-日保；3-周保；4-月保)
     */
    @ApiModelProperty("保养分类(1-初保;2-日保；3-周保；4-月保)")
    @TableField(exist = false)
    private String typeStr;
    /**
     * 保养分类(1-初保;2-日保；3-周保；4-月保)
     */
    @ApiModelProperty("保养分类(1-初保;2-日保；3-周保；4-月保)")
    private Integer type;
    /**
     * 上传时间
     */
    @ApiModelProperty("上传时间")
    private Date creationDate;
    /**
     * 上传人
     */
    @ApiModelProperty("上传人")
    private Long creationBy;
}
