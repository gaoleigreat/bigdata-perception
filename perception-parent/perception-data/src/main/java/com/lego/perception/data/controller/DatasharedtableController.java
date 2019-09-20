package com.lego.perception.data.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.mybatis.config.DataSourceContextHolder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.data.model.entity.Datasharedtable;
import com.lego.perception.data.service.IDatasharedtableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;


/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-19 12:36:38
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/datasharedtable")
@Api(value = "datasharedtable", description = "数据共享")
@Resource(value = "datasharedtable", desc = "数据共享")
@Slf4j
public class DatasharedtableController {

    @Autowired
    private IDatasharedtableService datasharedtableService;


    @ApiOperation(value = "查询共享数据库数据", httpMethod = "GET")
    @RequestMapping(value = "/shareList", method = RequestMethod.GET)
    @Operation(value = "shareList", desc = "查询共享数据库数据")
    public RespVO<RespDataVO<Datasharedtable>> shareList(@ModelAttribute Datasharedtable datasharedtable) {
        List<Datasharedtable> list = datasharedtableService.queryShareList(datasharedtable);
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "查询共享数据库数据", httpMethod = "GET")
    @RequestMapping(value = "/shareListPaged/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    @Operation(value = "shareListPaged", desc = "查询共享数据库数据")
    public RespVO<PagedResult<Datasharedtable>> shareListPaged(@ModelAttribute Datasharedtable datasharedtable,
                                                               @PathParam(value = "") Page page) {
        PagedResult<Datasharedtable> list = datasharedtableService.queryShareListPaged(datasharedtable, page);
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "新增共享数据库数据", httpMethod = "POST")
    @RequestMapping(value = "/shareSave", method = RequestMethod.POST)
    @Operation(value = "shareSave", desc = "新增共享数据库数据")
    public RespVO<RespDataVO<Datasharedtable>> shareSave(@RequestBody Datasharedtable datasharedtable) {
        Integer save = datasharedtableService.saveShareData(datasharedtable);
        if (save > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }


    @ApiOperation(value = "删除共享库数据", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteShareData", method = RequestMethod.DELETE)
    @Operation(value = "deleteShareData", desc = "删除共享库数据")
    public RespVO deleteShareData(@ModelAttribute Datasharedtable datasharedtable) {
        return datasharedtableService.deleteShareData(datasharedtable);
    }


    @ApiOperation(value = "查询本地共享数据", httpMethod = "GET")
    @RequestMapping(value = "/myList", method = RequestMethod.GET)
    @Operation(value = "myList", desc = "查询本地共享数据")
    public RespVO<RespDataVO<Datasharedtable>> myList(@ModelAttribute Datasharedtable datasharedtable) {
        List<Datasharedtable> list = datasharedtableService.queryMyList(datasharedtable);
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "新增本地共享数据", httpMethod = "POST")
    @RequestMapping(value = "/myShareSave", method = RequestMethod.POST)
    @Operation(value = "myShareSave", desc = "新增本地共享数据")
    public RespVO<RespDataVO<Datasharedtable>> myShareSave(@RequestBody Datasharedtable datasharedtable) {
        Integer save = datasharedtableService.saveMyData(datasharedtable);
        if (save > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }


    @ApiOperation(value = "查询本地共享数据", httpMethod = "GET")
    @RequestMapping(value = "/myListPaged/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    @Operation(value = "myListPaged", desc = "查询共享数据库数据")
    public RespVO<PagedResult<Datasharedtable>> myListPaged(@ModelAttribute Datasharedtable datasharedtable,
                                                            @PathParam(value = "") Page page) {
        PagedResult<Datasharedtable> list = datasharedtableService.querymyListPaged(datasharedtable, page);
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "删除本地共享数据", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteMyData", method = RequestMethod.DELETE)
    @Operation(value = "deleteMyData", desc = "删除本地共享数据")
    public RespVO deleteMyData(@ModelAttribute Datasharedtable datasharedtable) {
        return datasharedtableService.deleteMyData(datasharedtable);
    }


    @ApiOperation(value = "共享数据", httpMethod = "POST")
    @RequestMapping(value = "/shareData", method = RequestMethod.POST)
    @Operation(value = "shareData", desc = "共享数据")
    public RespVO shareData(@ModelAttribute Datasharedtable datasharedtable) {
        List<Datasharedtable> myList = datasharedtableService.queryMyList(datasharedtable);
        if (!CollectionUtils.isEmpty(myList)) {
            Integer dataBatch = datasharedtableService.saveShareDataBatch(myList);
            if (dataBatch > 0) {
                return RespVOBuilder.success();
            }
        }
        return RespVOBuilder.failure();
    }


}
