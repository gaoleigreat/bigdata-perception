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
 * @date 2019-10-08 08:42:13
 * @since jdk1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tpl_equipment_file")
public class EquipmentFile extends BaseModel {


    /**
     * 文件Id
     */
    @ApiModelProperty("文件Id")
    private Long fileId;
    /**
     * 文件名称
     */

    @ApiModelProperty("文件Id")
    private String fileName;
    /**
     * 文件类型
     */
    @ApiModelProperty("文件类型")
    private String fileType;
    /**
     * 文件路径
     */
    @ApiModelProperty("文件路径")
    private String fileUrl;
    /**
     * 预览url
     */
    @ApiModelProperty("预览url")
    private String previewUrl;
    /**
     * 数据类型(1-结构化数据;2-非结构化数据)
     */
    @ApiModelProperty("数据类型(1-结构化数据;2-非结构化数据)")
    private Integer dataType;
    /**
     * 是否删除
     */
    @ApiModelProperty("是否删除")
    private Integer deleteFlag;

    /**
     * 设备类型
     */
    @ApiModelProperty("设备类型")
    private Long equipmentId;

    /**
     * 设备编号
     */
    @ApiModelProperty("设备编号")
    private String equipmentCode;


    /**
     * 文件说明
     */
    @ApiModelProperty("文件说明")
    private String remark;

    /**
     * 标签
     */
    @ApiModelProperty("标签")
    private String tags;


}
