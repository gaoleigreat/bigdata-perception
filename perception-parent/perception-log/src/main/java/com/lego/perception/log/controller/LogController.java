package com.lego.perception.log.controller;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.page.PagedResult;
import com.lego.framework.base.sdto.RespVO;
import com.lego.framework.log.model.vo.LogVo;
import com.lego.perception.log.service.ILogService;
import com.survey.lib.common.consts.DictConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yanglf
 * @description
 * @since 2018/12/21
 **/
@RestController
@RequestMapping(DictConstant.Path.LOG)
@Api(value = "LogController", description = "日志管理")
@Resource(value = "log",desc = "日志管理")
public class LogController {

    @Autowired
    private ILogService iLogService;


    @ApiOperation(value = "查询日志信息", httpMethod = "GET", notes = "查询日志信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "当前页", dataType = "int", required = true, example = "1", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", dataType = "int", defaultValue = "10",example = "10", paramType = "query"),
    })
    @Operation(value = "list",desc = "查询日志信息")
    @RequestMapping(value = "/list/{pageIndex}", method = RequestMethod.GET)
    public RespVO<PagedResult<LogVo>> query(@PathVariable(value = "pageIndex") int pageIndex,
                                            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return iLogService.list(pageIndex, pageSize);
    }


}
