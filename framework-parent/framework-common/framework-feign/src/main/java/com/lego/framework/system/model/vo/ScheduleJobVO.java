package com.lego.framework.system.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ScheduleJobVO {
    @ApiModelProperty("定时任务名称")
    private String jobName;

    @ApiModelProperty("定时任务组名称")
    private String groupName;

    @ApiModelProperty("定时表达式")
    private String cron;

    @ApiModelProperty("访问链接")
    private String url;

    @ApiModelProperty("描述")
    private String description;
}
