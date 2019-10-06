package com.lego.framework.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lego.framework.config.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * @author yanglf
 * @description
 * @since 2019/8/26
 **/
@Data
@TableName("tpl_data_file")
public class DataFile extends BaseModel {

    @ApiModelProperty("文件名称")
    private String name;

    @ApiModelProperty("文件类型")
    private String fileType;


    @ApiModelProperty("工程Id")
    private Long projectId;


    @ApiModelProperty("文件URl")
    private String fileUrl;


    @ApiModelProperty("预览URl")
    private String previewUrl;

    @ApiModelProperty("模板ID")
    private Long templateId;


    @ApiModelProperty("是否审核(0-待审核;1-审核通过;2-审核驳回")
    private int checkFlag;


    @ApiModelProperty("审核时间")
    private Date checkDate;

    @ApiModelProperty("审核人")
    private Long checkBy;

    @ApiModelProperty("是否发布(0-未发布;1-发布")
    private int publishFlag;

    @ApiModelProperty("发布时间")
    private Date publishDate;

    @ApiModelProperty("发布人")
    private Long publishBy;

    @ApiModelProperty("数据类型(1-结构化数据;2-非结构化数据")
    private int dataType;

    @ApiModelProperty("是否删除(1-否;2-是)")
    private Integer deleteFlag;

    @ApiModelProperty("文件说明")
    private String remark;

    @ApiModelProperty("文件标签")
    private String tags;


    @ApiModelProperty("批次号")
    private String batchNum;


    @ApiModelProperty("设备类型Id")
    private Long equipmentId;

    @ApiModelProperty("设备编号")
    private String equipmentCode;

    /**
     * @param name
     * @param fileType
     * @param projectId
     * @param fileUrl
     * @param previewUrl
     * @param templateId
     * @param checkFlag
     * @param dataType
     * @param deleteFlag
     */
    public DataFile(String name, String fileType, Long projectId, String fileUrl, String previewUrl, Long templateId, int checkFlag, int dataType, Integer deleteFlag,String remark,String tags,String batchNum) {
        Date currentTime = new Date();
        this.name = name;
        this.fileType = fileType;
        this.projectId = projectId;
        this.fileUrl = fileUrl;
        this.previewUrl = previewUrl;
        this.templateId = templateId;
        this.checkFlag = checkFlag;
        this.checkDate = currentTime;
        this.checkBy = 1L;
        this.publishFlag = 0;
        this.publishDate = currentTime;
        this.publishBy = 1L;
        this.dataType = dataType;
        this.deleteFlag = deleteFlag;
        this.remark = remark;
        this.tags = tags;
        this.batchNum = batchNum;
        super.setCreateInfo();
        super.setUpdateInfo();
    }

    public DataFile() {
    }
}
