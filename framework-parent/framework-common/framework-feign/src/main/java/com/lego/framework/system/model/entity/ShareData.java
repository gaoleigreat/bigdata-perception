package com.lego.framework.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("tpl_share_data")
public class ShareData extends BaseModel {

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


    public ShareData() {

    }

}