package com.lego.perception.system.controller;

import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.perception.system.service.IScheduleJobService;
import com.lego.framework.system.model.vo.ScheduleJobVO;
import com.survey.lib.common.vo.RespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheduleJob")
@Api(value = "ScheduleJobController",description = "定时任务")
@Resource(value = "scheduleJob", desc = "定时任务")
@Slf4j
public class ScheduleJobController {

    @Autowired
    private IScheduleJobService scheduleJobService;

    @RequestMapping(value = "/insert", method = RequestMethod.PUT)
    @Operation(value = "insert", desc = "新增")
    @ApiOperation("新增定时任务")
    public RespVO insert(@RequestBody ScheduleJobVO scheduleJobVO){

        return scheduleJobService.addJob(scheduleJobVO);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @Operation(value = "查询", desc = "find")
    @ApiOperation("查询定时任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobName", value = "任务名称", dataType = "String", required = false, example = "1", paramType = "query"),
            @ApiImplicitParam(name = "groupName", value = "任务组名称", dataType = "String", required = false, example = "1", paramType = "query"),
    })
    public RespVO findAllJob(@ModelAttribute ScheduleJobVO scheduleJobVO){

        return scheduleJobService.findAllJob(scheduleJobVO);
    }

    @RequestMapping(value = "/pause", method = RequestMethod.GET)
    @Operation(value = "pause", desc = "暂停")
    @ApiOperation("暂停定时任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobName", value = "任务名称", dataType = "String", required = true, example = "1", paramType = "query"),
            @ApiImplicitParam(name = "groupName", value = "任务组名称", dataType = "String", required = true, example = "1", paramType = "query"),
    })
    public RespVO pauseJob(@ModelAttribute ScheduleJobVO scheduleJobVO){

        return scheduleJobService.pauseJob(scheduleJobVO);
    }

    @RequestMapping(value = "/resume", method = RequestMethod.GET)
    @Operation(value = "resume", desc = "恢复")
    @ApiOperation("暂停定时任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobName", value = "任务名称", dataType = "String", required = true, example = "1", paramType = "query"),
            @ApiImplicitParam(name = "groupName", value = "任务组名称", dataType = "String", required = true, example = "1", paramType = "query"),
    })
    public RespVO resumeJob(@ModelAttribute ScheduleJobVO scheduleJobVO){

        return scheduleJobService.resumeJob(scheduleJobVO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @Operation(value = "delete", desc = "delete")
    @ApiOperation("删除定时任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobName", value = "任务名称", dataType = "String", required = true, example = "1", paramType = "query"),
            @ApiImplicitParam(name = "groupName", value = "任务组名称", dataType = "String", required = true, example = "1", paramType = "query"),
    })
    public RespVO delete(@ModelAttribute ScheduleJobVO scheduleJobVO){

        return scheduleJobService.deleteJob(scheduleJobVO);
    }

}
