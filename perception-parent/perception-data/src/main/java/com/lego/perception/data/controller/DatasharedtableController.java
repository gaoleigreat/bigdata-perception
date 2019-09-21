package com.lego.perception.data.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.data.model.entity.Datasharedtable;
import com.lego.perception.data.config.HdfsProperties;
import com.lego.perception.data.config.MongoProperties;
import com.lego.perception.data.config.MysqlProperties;
import com.lego.perception.data.service.IDatasharedtableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
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

    @Autowired
    private MysqlProperties mysqlProperties;

    @Autowired
    private MongoProperties mongoProperties;

    @Autowired
    private HdfsProperties hdfsProperties;


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


    @ApiOperation(value = "查询本地共享数据", httpMethod = "GET")
    @RequestMapping(value = "/myList", method = RequestMethod.GET)
    @Operation(value = "myList", desc = "查询本地共享数据")
    public RespVO<RespDataVO<Datasharedtable>> myList(@ModelAttribute Datasharedtable datasharedtable) {
        List<Datasharedtable> list = datasharedtableService.queryMyList(datasharedtable);
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "新增本地共享数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourcesType", value = "数据源(0-MySql;1-Mongo;2-HDFS)", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "dataType", value = "共享类型：文件夹类型、数据库类型", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "desc", value = "数据描述", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "数据名称", paramType = "query", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/myShareSave", method = RequestMethod.POST)
    @Operation(value = "myShareSave", desc = "新增本地共享数据")
    public RespVO<RespDataVO<Datasharedtable>> myShareSave(Integer sourcesType,
                                                           String dataType,
                                                           String desc,
                                                           String name) {
        Datasharedtable datasharedtable = null;
        if (sourcesType == 0) {
            // MySql
            datasharedtable = getDbProperties(dataType, desc, name, mysqlProperties.getPw(), mysqlProperties.getSchema(), mysqlProperties.getServerIp(), mysqlProperties.getServerPort(), mysqlProperties.getServerType(), mysqlProperties.getUsername());
        } else if (sourcesType == 1) {
            // mongo
            datasharedtable = getDbProperties(dataType, desc, name, mongoProperties.getPw(), mongoProperties.getSchema(), mongoProperties.getServerIp(), mongoProperties.getServerPort(), mongoProperties.getServerType(), mongoProperties.getUsername());
        } else if (sourcesType == 2) {
            datasharedtable = getDbProperties(dataType, desc, name, hdfsProperties.getPw(), hdfsProperties.getSchema(), hdfsProperties.getServerIp(), hdfsProperties.getServerPort(), hdfsProperties.getServerType(), hdfsProperties.getUsername());
        } else {
            ExceptionBuilder.operateFailException("无效的数据源 ");
        }
        Integer save = datasharedtableService.saveMyData(datasharedtable);
        if (save > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }

    private Datasharedtable getDbProperties(String dataType, String desc, String name, String pw, String schema, String serverIp, String serverPort, String serverType, String username) {
        Datasharedtable datasharedtable = new Datasharedtable();
        datasharedtable.setDesc(desc);
        datasharedtable.setName(name);
        datasharedtable.setPw(pw);
        datasharedtable.setSchema(schema);
        datasharedtable.setServerIp(serverIp);
        datasharedtable.setServerPort(serverPort);
        datasharedtable.setServerType(serverType);
        datasharedtable.setType(dataType);
        datasharedtable.setUsername(username);
        datasharedtable.setSharedtime(new Date());
        return datasharedtable;
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
