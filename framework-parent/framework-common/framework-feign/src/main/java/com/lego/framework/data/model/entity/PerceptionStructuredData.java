package com.lego.framework.data.model.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.io.Serializable;
@Data
@TableName("perception_structured_data")
public class PerceptionStructuredData  {


    /**
     *Id
     */
    @TableId
    @ApiModelProperty("Id")
    private Long id;


    /**
     *批次号
     */
    
    @ApiModelProperty("批次号")
    private String batchNum;


    /**
     *业务模块
     */
    
    @ApiModelProperty("业务模块")
    private String businessModule;


    /**
     *模板Id
     */
    
    @ApiModelProperty("模板Id")
    private Long templateId;


    /**
     *文件來源
     */
    
    @ApiModelProperty("文件來源")
    private String sourceModule;


    /**
     *名称
     */
    
    @ApiModelProperty("名称")
    private String name;


    /**
     *大小/ Bytes
     */
    
    @ApiModelProperty("大小/ Bytes")
    private Long size;


    /**
     *工程id
     */
    
    @ApiModelProperty("工程id")
    private Long projectId;


    /**
     *是否发布(0-否;1-是)
     */
    
    @ApiModelProperty("是否发布(0-否;1-是)")
    private Integer publishFlag;


    /**
     *发布时间
     */
    
    @ApiModelProperty("发布时间")
    private Date publishDate;


    /**
     *发布人
     */
    
    @ApiModelProperty("发布人")
    private String publishBy;


    /**
     *是否删除
     */
    
    @ApiModelProperty("是否删除 0没有删除，1删除")
    private Integer deleteFlag;


    /**
     *标签
     */
    
    @ApiModelProperty("标签")
    private String tags;


    /**
     *文件说明
     */
    
    @ApiModelProperty("文件说明")
    private String remark;


    /**
     *创建人
     */
    
    @ApiModelProperty("创建人")
    private String createdBy;


    /**
     *创建时间
     */
    
    @ApiModelProperty("创建时间")
    private Date creationDate;


    /**
     *最后修改人
     */
    
    @ApiModelProperty("最后修改人")
    private String lastUpdatedBy;


    /**
     *最后修改时间
     */
    
    @ApiModelProperty("最后修改时间")
    private Date lastUpdateDate;


}