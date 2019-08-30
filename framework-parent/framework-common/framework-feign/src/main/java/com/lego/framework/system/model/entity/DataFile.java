package com.lego.framework.system.model.entity;

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
    private Long id;

    @ApiModelProperty("文件名称")
    private String name;

    @ApiModelProperty("工程Id")
    private Long projectId;

    @ApiModelProperty("文件类型")
    private String fileType;

    @ApiModelProperty("文件URl")
    private String fileUrl;


    @ApiModelProperty("文件URl")
    private String previewUrl;

    @ApiModelProperty("创建时间")
    private Date creationDate;

    @ApiModelProperty("创建人")
    private Long createdBy;

    @ApiModelProperty("最后更新时间")
    private Date lastUpdateDate;

    @ApiModelProperty("最后更新人")
    private Long lastUpdatedBy;

    @ApiModelProperty("是否删除(1-否;2-是)")
    private Integer deleteFlag;

    public void setCreateInfo() {
        Date currentTime = new Date();
        this.creationDate = currentTime;
        this.lastUpdateDate = currentTime;
    }

    public void setUpdateInfo() {
        this.lastUpdateDate = new Date();
    }
}
