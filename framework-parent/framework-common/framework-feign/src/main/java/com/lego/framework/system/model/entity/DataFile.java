package com.lego.framework.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author yanglf
 * @description
 * @since 2019/8/26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tpl_data_file")
public class DataFile {
    @ApiModelProperty("id")
    @TableId
    private Long id;

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
    private Long TemplateId;


    @ApiModelProperty("是否审核(0-待审核;1-审核通过;2-审核驳回")
    private Long checkFlag;


    @ApiModelProperty("审核时间")
    private Date checkDate;

    @ApiModelProperty("审核人")
    private Long checkBy;

    @ApiModelProperty("是否发布(0-未发布;1-发布")
    private Long publishFlag;

    @ApiModelProperty("发布时间")
    private Date publishDate;

    @ApiModelProperty("发布人")
    private Long publishBy;

    @ApiModelProperty("数据类型(1-结构化数据;2-非结构化数据")
    private int dataType;

    @ApiModelProperty("是否删除(1-否;2-是)")
    private Integer deleteFlag;

    @ApiModelProperty("创建时间")
    private Date creationDate;

    @ApiModelProperty("创建人")
    private Long createdBy;

    @ApiModelProperty("最后更新时间")
    private Date lastUpdateDate;

    @ApiModelProperty("最后更新人")
    private Long lastUpdatedBy;


    public void setCreateInfo() {
        Date currentTime = new Date();
        this.creationDate = currentTime;
        this.lastUpdateDate = currentTime;
    }

    public void setUpdateInfo() {
        this.lastUpdateDate = new Date();
    }
}
