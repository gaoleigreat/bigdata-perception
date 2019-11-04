package com.lego.perception.log.controller;

import com.framework.common.consts.DictConstant;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.log.model.entity.Log;
import com.lego.perception.log.service.ILogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2018/12/21
 **/
@RestController
@RequestMapping(DictConstant.Path.LOG)
@Api(value = "LogController", description = "日志管理")
@Resource(value = "log", desc = "日志管理")
public class LogController {

    @Autowired
    private ILogService iLogService;


    @ApiOperation(value = "查询日志信息", httpMethod = "GET", notes = "查询日志信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "当前页", dataType = "int", required = true, example = "1", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", dataType = "int", defaultValue = "10", example = "10", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "分类", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tag", value = "标签", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "long", paramType = "query"),
    })
    @Operation(value = "list", desc = "查询日志信息")
    @RequestMapping(value = "/list/{pageIndex}", method = RequestMethod.GET)
    public RespVO<PagedResult<Log>> query(@PathVariable(value = "pageIndex") int pageIndex,
                                          @RequestParam(required = false, defaultValue = "10") int pageSize,
                                          @RequestParam(required = false) String type,
                                          @RequestParam(required = false) String tag,
                                          @RequestParam(required = false) Long startTime,
                                          @RequestParam(required = false) Long endTime) {
        return iLogService.list(pageIndex, pageSize, type, tag, startTime, endTime);
    }


    @ApiOperation(value = "查询日志信息", httpMethod = "GET", notes = "查询日志信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "分类", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tag", value = "标签", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", dataType = "int", paramType = "query"),
    })
    @Operation(value = "list", desc = "查询日志信息")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RespVO<RespDataVO<Log>> list(@RequestParam(required = false) String type,
                                        @RequestParam(required = false) String tag,
                                        @RequestParam(required = false) Long startTime,
                                        @RequestParam(required = false) Long endTime,
                                        @RequestParam(required = false) Integer limit) {
        List<Log> logs = iLogService.list(type, tag, startTime, endTime, limit);
        return RespVOBuilder.success(logs);
    }


    @ApiOperation(value = "导出日志信息", httpMethod = "GET", notes = "导出日志信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "分类", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tag", value = "标签", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "long", paramType = "query"),
    })
    @Operation(value = "exportList", desc = "导出日志信息")
    @RequestMapping(value = "/exportList", method = RequestMethod.GET)
    public RespVO exportList(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) Long startTime,
            @RequestParam(required = false) Long endTime,
            HttpServletResponse response) {
        return iLogService.exportLog(type, tag, startTime, endTime, response);
    }


    @ApiOperation(value = "新增日志", httpMethod = "POST", notes = "新增日志")
    @Operation(value = "save", desc = "新增日志")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RespVO save(@RequestBody Log log) {
        return iLogService.add(log);
    }

}
