package com.lego.perception.data.controller;

import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.mybatis.annotation.DB;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.data.model.entity.Datasharedtable;
import com.lego.perception.data.service.IDatasharedtableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @ApiOperation(value = "shareList", httpMethod = "GET", notes = "查询共享数据库数据")
    @RequestMapping(value = "/shareList", method = RequestMethod.GET)
    @Operation(value = "shareList", desc = "查询共享数据库数据")
    public RespVO<RespDataVO<Datasharedtable>> shareList(@ModelAttribute Datasharedtable datasharedtable) {
        List<Datasharedtable> list = datasharedtableService.queryShareList(datasharedtable);
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "shareSave", httpMethod = "POST", notes = "新增共享数据库数据")
    @RequestMapping(value = "/shareSave", method = RequestMethod.POST)
    @Operation(value = "shareSave", desc = "新增共享数据库数据")
    public RespVO<RespDataVO<Datasharedtable>> shareSave(@RequestBody Datasharedtable datasharedtable) {
        Integer save = datasharedtableService.saveShareData(datasharedtable);
        if (save > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }


    @ApiOperation(value = "myList", httpMethod = "GET", notes = "查询本地共享数据")
    @RequestMapping(value = "/myList", method = RequestMethod.GET)
    @Operation(value = "myList", desc = "查询本地共享数据")
    public RespVO<RespDataVO<Datasharedtable>> myList(@ModelAttribute Datasharedtable datasharedtable) {
        List<Datasharedtable> list = datasharedtableService.queryMyList(datasharedtable);
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "myShareSave", httpMethod = "POST", notes = "新增本地共享数据")
    @RequestMapping(value = "/myShareSave", method = RequestMethod.POST)
    @Operation(value = "myShareSave", desc = "新增本地共享数据")
    public RespVO<RespDataVO<Datasharedtable>> myShareSave(@RequestBody Datasharedtable datasharedtable) {
        Integer save = datasharedtableService.saveMyData(datasharedtable);
        if (save > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }


}
