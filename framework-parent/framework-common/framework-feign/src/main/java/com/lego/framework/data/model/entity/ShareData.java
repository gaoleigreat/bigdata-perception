package com.lego.framework.data.model.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class ShareData {

    /**
     * 文件Id
     */
    @TableId
    @ApiModelProperty("数据Id")
    private Long id;


    @ApiModelProperty("数据名称")
    private String name;

    /**
     * 工程id
     */

    @ApiModelProperty("工程id")
    private Long projectId;

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


    @ApiModelProperty("是否撤回(0-否;1-是)")
    private Integer isRecall;


    /**
     * 数据说明
     */
    @ApiModelProperty("数据说明")
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


    @ApiModelProperty("业务模块")
    private String businessModule;


    @ApiModelProperty("数据来源")
    private String sourceModule;


    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createdBy;


    /**
     * 创建时间
     */

    @ApiModelProperty("创建时间")
    private Date creationDate;


    /**
     * 最后修改人
     */

    @ApiModelProperty("最后修改人")
    private String lastUpdatedBy;


    /**
     * 最后修改时间
     */

    @ApiModelProperty("最后修改时间")
    private Date lastUpdateDate;


    @ApiModelProperty("数据大小")
    private Long dataSize;


    @ApiModelProperty("模板id")
    private Long templateId;


    public ShareData() {

    }

    public void setUpdateInfo() {
        this.lastUpdateDate = new Date();
    }
}