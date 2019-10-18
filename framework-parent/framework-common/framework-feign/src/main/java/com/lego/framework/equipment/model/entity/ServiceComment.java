package com.lego.framework.equipment.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author itar
 * @mail wuhandzy@gmail.com
 * @date 2019-10-18 03:51:45
 * @since jdk1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tpl_service_comment")
public class ServiceComment {
    /**
     * 维修评论id
     */
    @ApiModelProperty("维修评论id")
    private Long id;
    /**
     * 维修id
     */
    @ApiModelProperty("维修id")
    private Long serviceId;
    /**
     * 维修状态
     */
    @ApiModelProperty("维修状态")
    private Integer serviceStatus;
    /**
     * 评论内容
     */
    @ApiModelProperty("评论内容")
    private String body;
    /**
     * 是否删除
     */
    @ApiModelProperty("是否删除")
    private Integer deleteFlag;
    /**
     * 评论时间
     */
    @ApiModelProperty("评论时间")
    private Date creationDate;
    /**
     * 评论人id
     */
    @ApiModelProperty("评论人id")
    private Long creationBy;


    @ApiModelProperty("评论人昵称")
    @TableField(exist = false)
    private String creationName;


    @ApiModelProperty("评论人头像")
    @TableField(exist = false)
    private String creationImg;


}
