package com.lego.perception.data.controller;

import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.data.model.entity.LocalSharedData;
import com.lego.framework.data.model.entity.RemoteSharedData;
import com.lego.framework.file.feign.FileClient;
import com.lego.framework.file.feign.ShareDataClient;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.framework.template.feign.TemplateFeignClient;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.SearchParam;
import com.lego.perception.data.config.HdfsProperties;
import com.lego.perception.data.config.MongoProperties;
import com.lego.perception.data.config.MysqlProperties;
import com.lego.perception.data.service.IRemoteShareDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.*;


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
    private IRemoteShareDataService iRemoteShareDataService;


    @ApiOperation(value = "查询共享数据配置信息", httpMethod = "GET")
    @RequestMapping(value = "/shareList", method = RequestMethod.GET)
    @Operation(value = "shareList", desc = "查询共享数据配置信息")
    public RespVO<RespDataVO<RemoteSharedData>> shareList(@ModelAttribute RemoteSharedData datasharedtable) {
        List<RemoteSharedData> list = iRemoteShareDataService.queryRemoteList(datasharedtable);
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "查询共享数据配置信息", httpMethod = "GET")
    @RequestMapping(value = "/shareListPaged/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    @Operation(value = "shareListPaged", desc = "查询共享数据配置信息")
    public RespVO<PagedResult<RemoteSharedData>> shareListPaged(@ModelAttribute RemoteSharedData datasharedtable,
                                                                @PathParam(value = "") Page page) {
        PagedResult<RemoteSharedData> list = iRemoteShareDataService.queryRemoteListPaged(datasharedtable, page);
        return RespVOBuilder.success(list);
    }

}
