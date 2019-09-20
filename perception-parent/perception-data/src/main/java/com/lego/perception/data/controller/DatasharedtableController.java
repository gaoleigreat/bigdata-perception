package com.lego.perception.data.controller;

import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.mybatis.annotation.DB;
import com.framework.mybatis.config.DataSourceType;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.data.model.entity.Datasharedtable;
import com.lego.perception.data.service.IDatasharedtableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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


    @ApiOperation(value = "shareList",httpMethod = "GET")
    @RequestMapping(value = "/shareList", method = RequestMethod.GET)
    @DB(value = "share")
    public RespVO<RespDataVO<Datasharedtable>> shareList() {
        List<Datasharedtable> list = datasharedtableService.queryList();
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "myList",httpMethod = "GET")
    @RequestMapping(value = "/myList", method = RequestMethod.GET)
    public RespVO<RespDataVO<Datasharedtable>> myList() {
        List<Datasharedtable> list = datasharedtableService.queryList();
        return RespVOBuilder.success(list);
    }


}
