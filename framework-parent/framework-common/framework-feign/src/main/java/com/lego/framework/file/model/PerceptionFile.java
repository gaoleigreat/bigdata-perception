package com.lego.framework.file.model;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.io.Serializable;
@Data
@TableName("perception_file")
public class PerceptionFile  {


    /**
     *文件Id
     */
    @TableId
    @ApiModelProperty("文件Id")
    private Long id;


    /**
     *批次号
     */
    
    @ApiModelProperty("批次号")
    private String batchNum;


    /**
     *文件名称
     */
    
    @ApiModelProperty("文件名称")
    private String name;


    /**
     *文件后缀
     */
    
    @ApiModelProperty("文件后缀")
    private String extName;


    /**
     *大小/ Bytes
     */
    
    @ApiModelProperty("大小/ Bytes")
    private Long size;


    /**
     *业务模块
     */
    
    @ApiModelProperty("业务模块")
    private String businessModule;


    /**
     *结构化为0，非结构化为1
     */
    
    @ApiModelProperty("结构化为0，非结构化为1")
    private int isStructured;


    /**
     *工程id
     */
    
    @ApiModelProperty("工程id")
    private Long projectId;


    /**
     *文件路径
     */
    
    @ApiModelProperty("文件路径")
    private String fileUrl;


    /**
     *预览url
     */
    
    @ApiModelProperty("预览url")
    private String previewUrl;


    /**
     *是否删除
     */
    
    @ApiModelProperty("是否删除")
    private Integer deleteFlag;


    /**
     *文件说明
     */
    
    @ApiModelProperty("文件说明")
    private String remark;


    /**
     *标签
     */
    
    @ApiModelProperty("标签")
    private String tags;


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