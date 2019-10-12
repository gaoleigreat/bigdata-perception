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
import com.lego.framework.system.model.entity.DataFile;
import com.lego.framework.template.feign.TemplateFeignClient;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.perception.data.config.HdfsProperties;
import com.lego.perception.data.config.MongoProperties;
import com.lego.perception.data.config.MysqlProperties;
import com.lego.perception.data.service.ILocalShareDataService;
import com.lego.perception.data.service.IRemoteShareDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ILocalShareDataService iLocalShareDataService;

    @Autowired
    private TemplateFeignClient templateFeignClient;

    @Autowired
    private MongoProperties mongoProperties;

    @Autowired
    private MysqlProperties mysqlProperties;

    @Autowired
    private HdfsProperties hdfsProperties;

    @Autowired
    private FileClient fileClient;


    @ApiOperation(value = "查询共享数据库数据", httpMethod = "GET")
    @RequestMapping(value = "/shareList", method = RequestMethod.GET)
    @Operation(value = "shareList", desc = "查询共享数据库数据")
    public RespVO<RespDataVO<RemoteSharedData>> shareList(@ModelAttribute RemoteSharedData datasharedtable) {
        List<RemoteSharedData> list = iRemoteShareDataService.queryRemoteList(datasharedtable);
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "查询共享数据库数据", httpMethod = "GET")
    @RequestMapping(value = "/shareListPaged/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    @Operation(value = "shareListPaged", desc = "查询共享数据库数据")
    public RespVO<PagedResult<RemoteSharedData>> shareListPaged(@ModelAttribute RemoteSharedData datasharedtable,
                                                                @PathParam(value = "") Page page) {
        PagedResult<RemoteSharedData> list = iRemoteShareDataService.queryRemoteListPaged(datasharedtable, page);
        return RespVOBuilder.success(list);
    }




    @ApiOperation(value = "查询本地共享数据", httpMethod = "GET")
    @RequestMapping(value = "/myList", method = RequestMethod.GET)
    @Operation(value = "myList", desc = "查询本地共享数据")
    public RespVO<RespDataVO<LocalSharedData>> myList(@ModelAttribute LocalSharedData datasharedtable) {
        List<LocalSharedData> list = iLocalShareDataService.queryLocalList(datasharedtable);
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "查询本地共享数据", httpMethod = "GET")
    @RequestMapping(value = "/myListPaged/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    @Operation(value = "myListPaged", desc = "查询共享数据库数据")
    public RespVO<PagedResult<LocalSharedData>> myListPaged(@ModelAttribute LocalSharedData datasharedtable,
                                                            @PathParam(value = "") Page page) {
        PagedResult<LocalSharedData> list = iLocalShareDataService.queryLocalListPaged(datasharedtable, page);
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "删除本地共享数据", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteMyData", method = RequestMethod.DELETE)
    @Operation(value = "deleteMyData", desc = "删除本地共享数据")
    public RespVO deleteMyData(@ModelAttribute LocalSharedData datasharedtable) {
        return iLocalShareDataService.deleteLocalData(datasharedtable);
    }


    @ApiOperation(value = "共享数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "batchNums", value = "批次号", paramType = "query", allowMultiple = true, required = true, dataType = "String"),
    })
    @RequestMapping(value = "/shareData", method = RequestMethod.POST)
    @Operation(value = "shareData", desc = "共享数据")
    public RespVO shareData(@RequestParam List<String> batchNums) {
        //  获取所属数据源
        RespVO<RespDataVO<DataFile>> respDataVORespVO = fileClient.selectByBatchNums(batchNums,null);
        if (respDataVORespVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure();
        }
        List<DataFile> dataFiles = respDataVORespVO.getInfo().getList();
        Map<String, String> map = new HashMap<>();
        List<RemoteSharedData> remoteSharedDataList = new ArrayList<>();
        List<LocalSharedData> localSharedDataList = new ArrayList<>();
        if (CollectionUtils.isEmpty(dataFiles)) {
            return RespVOBuilder.failure("共享数据不存在");
        }
        for (DataFile dataFile : dataFiles) {
            String dataType = "数据库类型";
            Integer sourcesType;
            String batchNum = dataFile.getBatchNum();
            Long templateId = dataFile.getTemplateId();
            if (templateId == null) {
                // HDFS
                if (map.containsKey("HDFS")) {
                    continue;
                }
                map.put("HDFS", batchNum);
                dataType = "文件夹类型";
            }
            RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateById(templateId);
            if (RespConsts.SUCCESS_RESULT_CODE != respVO.getRetCode()) {
                return RespVOBuilder.failure("获取模板失败");
            }
            FormTemplate info = respVO.getInfo();
            if (info == null) {
                return RespVOBuilder.failure("获取不到模板信息");
            }
            sourcesType = info.getType();
            if (map.containsKey(String.valueOf(sourcesType))) {
                continue;
            }
            map.put(String.valueOf(sourcesType), batchNum);
            String templateName = info.getTemplateName();
            String remark = dataFile.getRemark();
            RemoteSharedData remoteSharedData = getShareData(dataType, remark, templateName, sourcesType);
            if ("文件夹类型".equals(dataType)) {
                remoteSharedData.setSchema(dataFile.getFileUrl());
            }
            List<RemoteSharedData> remoteList = iRemoteShareDataService.queryRemoteList(remoteSharedData);
            if (!CollectionUtils.isEmpty(remoteList)) {
                continue;
            }
            remoteSharedDataList.add(remoteSharedData);
            LocalSharedData localSharedData = remoteSharedData.remote2LocalSharedData();
            localSharedData.setBatchNum(batchNum);
            localSharedDataList.add(localSharedData);
        }
        Integer dataBatch = iRemoteShareDataService.saveRemoteDataBatch(remoteSharedDataList);
        if (dataBatch > 0) {
            log.info("更新共享共享数据成功:{}", batchNums);
            Integer saveLocalDataBatch = iLocalShareDataService.saveLocalDataBatch(localSharedDataList);
            if (saveLocalDataBatch > 0) {
                log.info("更新本地共享数据成功:{}", batchNums);
                return RespVOBuilder.success();
            }
        }
        return RespVOBuilder.failure();
    }


    private RemoteSharedData getShareData(String dataType,
                                          String desc,
                                          String name,
                                          Integer sourcesType) {
        RemoteSharedData remoteSharedData = new RemoteSharedData();
        remoteSharedData.setDesc(desc);
        remoteSharedData.setName(name);
        remoteSharedData.setType(dataType);
        remoteSharedData.setSharedtime(new Date());
        if (sourcesType == null) {
            getDbProperties(remoteSharedData, null, hdfsProperties.getServerIp(), hdfsProperties.getServerPort(), hdfsProperties.getServerType(), hdfsProperties.getPw(), hdfsProperties.getUsername());
        } else if (sourcesType == 0) {
            getDbProperties(remoteSharedData, mysqlProperties.getSchema(), mysqlProperties.getServerIp(), mysqlProperties.getServerPort(), mysqlProperties.getServerType(), mysqlProperties.getPw(), mysqlProperties.getUsername());
        } else {
            getDbProperties(remoteSharedData, mongoProperties.getSchema(), mongoProperties.getServerIp(), mongoProperties.getServerPort(), mongoProperties.getServerType(), mongoProperties.getPw(), mongoProperties.getUsername());
        }

        return remoteSharedData;
    }

    private void getDbProperties(RemoteSharedData remoteSharedData, String schema, String serverIp, String serverPort, String serverType, String pw, String username) {
        remoteSharedData.setSchema(schema);
        remoteSharedData.setServerIp(serverIp);
        remoteSharedData.setServerPort(serverPort);
        remoteSharedData.setServerType(serverType);
        remoteSharedData.setPw(pw);
        remoteSharedData.setUsername(username);
    }


}
