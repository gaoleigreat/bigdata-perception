package com.lego.framework.equipment.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author itar
 * @mail wuhandzy@gmail.com
 * @date 2019-10-06 03:57:48
 * @since jdk1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tpl_equipment_doc_trace")
public class EquipmentDocTrace {
    /**
     * 设备文档轨迹id
     */
    @ApiModelProperty("设备文档轨迹id")
    private Long id;
    /**
     * 设备类型id
     */
    @ApiModelProperty("设备类型id")
    private Long equipmentId;
    /**
     * 设备编号
     */
    @ApiModelProperty("设备编号")
    private String equipmentCode;
    @ApiModelProperty("文件id")
    private Long fileId;
    /**
     * 文件file_url
     */
    @ApiModelProperty("文件file_url")
    private String fileUrl;
    /**
     * 文件预览URL
     */
    @ApiModelProperty("文件预览URL")
    private String previewUrl;

    @ApiModelProperty("设备类型(0-机械图纸；1-电气图纸；2-液压图纸；3-维修方案；4-会议纪要；5-转场记录；6-维修合同)")
    private Integer type;

    @ApiModelProperty("备注")
    private String remark;
    /**
     * 变更时间
     */
    @ApiModelProperty("变更时间")
    private Date creationDate;
    /**
     * 变更人
     */
    @ApiModelProperty("变更人")
    private Long createdBy;
}
