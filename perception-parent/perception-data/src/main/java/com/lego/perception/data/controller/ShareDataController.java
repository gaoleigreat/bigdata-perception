package com.lego.perception.data.controller;

import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.data.model.entity.RemoteSharedData;
import com.lego.framework.data.model.entity.ShareData;
import com.lego.framework.file.feign.ShareDataClient;
import com.lego.framework.template.feign.TemplateFeignClient;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.perception.data.config.HdfsProperties;
import com.lego.perception.data.config.MongoProperties;
import com.lego.perception.data.config.MysqlProperties;
import com.lego.perception.data.service.IRemoteShareDataService;
import com.lego.perception.data.service.IShareDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yanglf
 */
@RestController
@Api(value = "ShareDataController", description = "共享数据")
@RequestMapping("/sharedata/v1")
@Slf4j
public class ShareDataController {

    @Autowired
    private IShareDataService shareDataService;


    @Autowired
    private TemplateFeignClient templateFeignClient;

    @Autowired
    private MongoProperties mongoProperties;

    @Autowired
    private MysqlProperties mysqlProperties;

    @Autowired
    private HdfsProperties hdfsProperties;

    @Autowired
    private IRemoteShareDataService iRemoteShareDataService;


    @ApiOperation(value = "通过主键id查询共享数据", notes = "通过主键id查询共享数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", value = "id", required = true, dataType = "Long")
    })
    @GetMapping("/findById/{id}")
    public RespVO<ShareData> selectByPrimaryKey(@PathVariable(value = "id") Long id) {
        ShareData dataFile =
                shareDataService.selectByPrimaryKey(id);
        if (dataFile == null) {
            return RespVOBuilder.failure("当前DataFile不存在");
        } else {
            return RespVOBuilder.success(dataFile);
        }
    }

    @ApiOperation(value = "通过主键id删除共享数据", notes = "通过主键id删除共享数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", value = "id", required = true, dataType = "Long")
    })
    @DeleteMapping("/deleteById/{id}")
    public RespVO deleteByPrimaryKey(@PathVariable(value = "id") Long id) {
        Integer num = shareDataService.deleteByPrimaryKey(id);
        if (num == 0) {
            return RespVOBuilder.failure("删除共享数据失败");
        } else {
            return RespVOBuilder.success("删除共享数据成功");
        }
    }

    @ApiOperation(value = "新增共享数据", notes = "新增共享数据")
    @ApiImplicitParams({
    })
    @PostMapping("/insert")
    public RespVO insert(@RequestBody ShareData dataFile) {
        if (dataFile == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = shareDataService.insert(dataFile);
        if (num == 0) {
            return RespVOBuilder.failure("添加共享数据失败");
        } else {
            return RespVOBuilder.success("添加共享数据失败");
        }
    }

    @ApiOperation(value = "修改共享数据", notes = "修改共享数据")
    @ApiImplicitParams({
    })
    @PutMapping("/update")
    public RespVO updateByPrimaryKey(@RequestBody ShareData dataFile) {
        if (dataFile == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = shareDataService.updateByPrimaryKey(dataFile);
        if (num == 0) {
            return RespVOBuilder.failure("修改共享数据失败");
        } else {
            return RespVOBuilder.success("修改共享数据成功");
        }
    }


    @ApiOperation(value = "通过主键id批量删除共享数据", notes = "通过主键id批量删除共享数据")
    @ApiImplicitParams({
    })
    @DeleteMapping("/deleteBatchPrimaryKeys")
    public RespVO deleteBatchPrimaryKeys(@RequestBody List<Long> list) {
        if (CollectionUtils.isEmpty(list)) {
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = shareDataService.deleteBatchIds(list);
        if (num == 0) {
            return RespVOBuilder.failure("批量删除共享数据失败");
        } else {
            return RespVOBuilder.success("批量删除共享数据成功");
        }
    }


    @ApiOperation(value = "条件查询共享数据", notes = "条件查询共享数据")
    @ApiImplicitParams({
    })
    @PostMapping("/list")
    public RespVO query(@RequestBody ShareData dataFile) {
        if (dataFile == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        List<ShareData> list = shareDataService.query(dataFile);
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "条件查询共享数据", notes = "条件查询共享数据")
    @ApiImplicitParams({

    })
    @GetMapping("/queryPaged/{pageSize}/{pageIndex}")
    public RespVO<PagedResult<ShareData>> queryByListBatch(@ModelAttribute ShareData dataFile,
                                                           @PathParam(value = "") Page page) {
        if (dataFile == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        PagedResult<ShareData> listBatch = shareDataService.queryByListBatch(dataFile, page);
        return RespVOBuilder.success(listBatch);
    }


    @ApiOperation(value = "通过批次号查询", notes = "通过批次号查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "batchNums", value = "批次号，", paramType = "query", allowMultiple = true, required = true, dataType = "String"),
            @ApiImplicitParam(name = "tags", value = "标签(多标签用逗号隔开)", paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/selectByBatchNums", method = RequestMethod.GET)
    @Operation(value = "select", desc = "通过批次号查询")
    public RespVO<RespDataVO<ShareData>> selectByBatchNums(@RequestParam(value = "bathNums") List<String> batchNums,
                                                           @RequestParam(required = false) String tags) {
        return shareDataService.selectByBatchNums(batchNums, tags, 0);
    }


    @ApiOperation(value = "批量插入", notes = "批量插入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "batchNums", value = "批次号，", paramType = "query", allowMultiple = true, required = true, dataType = "String"),
            @ApiImplicitParam(name = "tags", value = "标签(多标签用逗号隔开)", paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/insertByBatchNums", method = RequestMethod.GET)
    @Operation(value = "insertByBatchNums", desc = "通过批次号查询")
    public RespVO insertByBatchNums(@RequestParam(value = "bathNums") List<String> batchNums,
                                    @RequestParam(required = false) String tags) {
        List<ShareData> dataList = shareDataService.selectDataByBatchNum(batchNums, tags, 0);
        if (!CollectionUtils.isEmpty(dataList)) {
            List<ShareData> shareDataList = new ArrayList<>();
            for (ShareData shareData : dataList) {
                //TODO  更新审核时间   审核人信息
                shareData.setIsRecall(0);
                shareData.setUpdateInfo();
                shareData.setDeleteFlag(0);
                shareDataList.add(shareData);
            }
            Integer batchInsert = shareDataService.batchInsert(shareDataList);
            if (batchInsert > 0) {
                return RespVOBuilder.success();
            }
        }
        return RespVOBuilder.failure();
    }


    @RequestMapping(value = "/recallByBatchAndTags", method = RequestMethod.GET)
    public RespVO recallByBatchAndTags(@RequestParam(value = "bathNums") List<String> batchNums,
                                       @RequestParam(required = false, value = "tags") String tags) {
        RespVO<RespDataVO<ShareData>> dataVORespVO = shareDataService.selectByBatchNums(batchNums, tags, 0);
        if (dataVORespVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure();
        }
        RespDataVO<ShareData> dataVO = dataVORespVO.getInfo();
        if (dataVO == null || CollectionUtils.isEmpty(dataVO.getList())) {
            return RespVOBuilder.failure();
        }
        List<ShareData> dataList = dataVO.getList();
        for (ShareData shareData : dataList) {
            shareData.setIsRecall(1);
            shareData.setUpdateInfo();
        }
        Integer batchUpdate = shareDataService.batchUpdate(dataList);
        if (batchUpdate > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }


    @ApiOperation(value = "撤回共享数据", httpMethod = "POST")
    @RequestMapping(value = "/recall", method = RequestMethod.POST)
    @Operation(value = "recall", desc = "撤回共享数据")
    public RespVO recall(@RequestParam String batchNum) {
        List<String> batchNums = new ArrayList<>();
        batchNums.add(batchNum);
        RespVO respVO = recallByBatchAndTags(batchNums, null);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return respVO;
        }
        int byBatchNum = shareDataService.updatePerceptionByBatchNum(batchNums, null, 0, 1);
        if (byBatchNum > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }


    @ApiOperation(value = "共享数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "batchNum", value = "批次号", paramType = "query", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/shareData", method = RequestMethod.POST)
    @Operation(value = "shareData", desc = "共享数据")
    public RespVO shareData(@RequestParam String batchNum) {
        //  获取所属数据源
        List<String> batchNums = new ArrayList<>();
        batchNums.add(batchNum);
        List<ShareData> dataList = shareDataService.selectDataByBatchNum(batchNums, null, 0);
        List<RemoteSharedData> remoteSharedDataList = new ArrayList<>();
        if (CollectionUtils.isEmpty(dataList)) {
            return RespVOBuilder.failure("共享数据不存在");
        }
        for (ShareData dataFile : dataList) {
            String dataType = "数据库类型";
            Integer sourcesType = null;
            Integer type = dataFile.getDataType();
            if (type == 2) {
                // HDFS
                dataType = "文件夹类型";
            } else {
                Long templateId = dataFile.getTemplateId();
                RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateById(templateId);
                if (RespConsts.SUCCESS_RESULT_CODE != respVO.getRetCode()) {
                    return RespVOBuilder.failure("获取模板失败");
                }
                FormTemplate info = respVO.getInfo();
                if (info == null) {
                    return RespVOBuilder.failure("获取不到模板信息");
                }
                sourcesType = info.getType();
            }
            String remark = dataFile.getRemark();
            RemoteSharedData remoteSharedData = getShareData(dataType, remark, dataFile.getName(), sourcesType);
            remoteSharedData.setFileId(dataFile.getId());
            remoteSharedDataList.add(remoteSharedData);
        }

        log.info("更新共享数据成功:{}", batchNums);
        RespVO respVO1 = insertByBatchNums(batchNums, null);
        if (respVO1.getRetCode() == RespConsts.SUCCESS_RESULT_CODE) {
            log.info("更新本地共享数据成功:{}", batchNums);
            Integer dataBatch = iRemoteShareDataService.saveRemoteDataBatch(remoteSharedDataList);
            if (dataBatch > 0) {
                log.info("更新共享数据成功:{}", batchNums);
                int byBatchNum = shareDataService.updatePerceptionByBatchNum(batchNums, null, 1, 0);
                if (byBatchNum > 0) {
                    log.info("更新数据表状态成功----------");
                    return RespVOBuilder.success();
                }
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
            getDbProperties(remoteSharedData, hdfsProperties.getSchema(), hdfsProperties.getServerIp(), hdfsProperties.getServerPort(), hdfsProperties.getServerType(), hdfsProperties.getPw(), hdfsProperties.getUsername());
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
