package com.lego.framework.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lego.framework.config.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yanglf
 * @description
 * @since 2019/8/26
 **/

@Data
@TableName("tpl_data_file")
public class DataFile extends BaseModel {

    /**
     * 文件Id
     */
    @TableId
    @ApiModelProperty("文件Id")
    private Long id;


    /**
     * 文件名称
     */

    @ApiModelProperty("文件名称")
    private String name;


    /**
     * 文件类型
     */

    @ApiModelProperty("文件类型")
    private String fileType;


    /**
     * 工程id
     */

    @ApiModelProperty("工程id")
    private Long projectId;


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
     * 业务模板id
     */

    @ApiModelProperty("业务模板id")
    private Long templateId;


    /**
     * 是否审核(0-待审核;1-审核通过;2-审核驳回)
     */

    @ApiModelProperty("是否审核(0-待审核;1-审核通过;2-审核驳回)")
    private Integer checkFlag;


    /**
     * 审核时间
     */

    @ApiModelProperty("审核时间")
    private Date checkDate;


    /**
     * 审核人
     */

    @ApiModelProperty("审核人")
    private Long checkBy;


    /**
     * 是否发布(0-否;1-是)
     */

    @ApiModelProperty("是否发布(0-否;1-是)")
    private Integer publishFlag;


    /**
     * 发布时间
     */

    @ApiModelProperty("发布时间")
    private Date publishDate;


    /**
     * 发布人
     */

    @ApiModelProperty("发布人")
    private Long publishBy;


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
     * 文件说明
     */

    @ApiModelProperty("文件说明")
    private String remark;


    /**
     * 标签
     */

    @ApiModelProperty("标签")
    private String tags;


    /**
     * 批次号
     */

    @ApiModelProperty("批次号")
    private String batchNum;


    /**
     * 创建人
     */

    @ApiModelProperty("创建人")
    private Long createdBy;


    /**
     * 创建时间
     */

    @ApiModelProperty("创建时间")
    private Date creationDate;


    /**
     * 最后修改人
     */

    @ApiModelProperty("最后修改人")
    private Long lastUpdatedBy;


    /**
     * 最后修改时间
     */

    @ApiModelProperty("最后修改时间")
    private Date lastUpdateDate;


    @ApiModelProperty("文件大小")
    private Long dataSize;


    public DataFile(String name, String fileType, Long projectId, String fileUrl, String previewUrl, Long templateId, int checkFlag, int dataType, Integer deleteFlag, String remark, String tags, String batchNum) {
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