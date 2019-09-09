package com.lego.framework.log.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author yanglf
 * @description 日志
 * @since 2018/12/21
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    @ApiModelProperty("日志id")
    private String id;

    @ApiModelProperty("业务系统id")
    private Long systemId;

    @ApiModelProperty("日志分类")
    private String type;

    @ApiModelProperty("标签")
    private String tag;

    @ApiModelProperty("所属服务")
    private String service;

    @ApiModelProperty("日志操作用户id")
    private Long userId;


    @ApiModelProperty("操作用户用户名")
    private String userName;


    @ApiModelProperty("日志操作时间")
    private Date operatingTime;

    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
     * 描述信息
     */
    @ApiModelProperty("日志描述")
    private String desc;
    /**
     * 操作IP
     */
    @ApiModelProperty("日志操作者ip")
    private String ip;

    @ApiModelProperty("日志内容")
    private String content;
}
