package com.lego.perception.file.controller;

import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.utils.UuidUtils;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.framework.system.model.entity.ShareData;
import com.lego.perception.file.service.IDataFileService;
import com.lego.perception.file.service.IShareDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.*;

@RestController
@Api(value = "ShareDataController", description = "共享数据")
@RequestMapping("/sharedata/v1")
@Slf4j
public class ShareDataController {

    @Autowired
    private IShareDataService shareDataService;


    @Autowired
    private IDataFileService iDataFileService;


    @ApiOperation(value = "通过主键id查询DataFile", notes = "通过主键id查询DataFile")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", value = "id", required = true, dataType = "Long")
    })
    @GetMapping("/{id}")
    public RespVO<ShareData> selectByPrimaryKey(@PathVariable(value = "id") Long id) {
        ShareData dataFile =
                shareDataService.selectByPrimaryKey(id);
        if (dataFile == null) {
            return RespVOBuilder.failure("当前DataFile不存在");
        } else {
            return RespVOBuilder.success(dataFile);
        }
    }

    @ApiOperation(value = "通过主键id删除DataFile", notes = "通过主键id删除DataFile")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", value = "id", required = true, dataType = "Long")
    })
    @DeleteMapping("/{id}")
    public RespVO deleteByPrimaryKey(@PathVariable(value = "id") Long id) {
        Integer num = shareDataService.deleteByPrimaryKey(id);
        if (num == 0) {
            return RespVOBuilder.failure("删除DataFile失败");
        } else {
            return RespVOBuilder.success("删除DataFile成功");
        }
    }

    @ApiOperation(value = "新增DataFile", notes = "新增DataFile")
    @ApiImplicitParams({
    })
    @PostMapping("/")
    public RespVO insert(@RequestBody ShareData dataFile) {
        if (dataFile == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = shareDataService.insert(dataFile);
        if (num == 0) {
            return RespVOBuilder.failure("添加DataFile失败");
        } else {
            return RespVOBuilder.success("添加DataFile成功");
        }
    }

    @ApiOperation(value = "修改DataFile", notes = "修改DataFile")
    @ApiImplicitParams({
    })
    @PutMapping("/")
    public RespVO updateByPrimaryKey(@RequestBody ShareData dataFile) {
        if (dataFile == null) {
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = shareDataService.updateByPrimaryKey(dataFile);
        if (num == 0) {
            return RespVOBuilder.failure("修改DataFile失败");
        } else {
            return RespVOBuilder.success("修改DataFile成功");
        }
    }


    @ApiOperation(value = "通过主键id批量删除DataFile", notes = "通过主键id批量删除DataFile")
    @ApiImplicitParams({
    })
    @DeleteMapping("/deleteBatchPrimaryKeys")
    public RespVO deleteBatchPrimaryKeys(@RequestBody List<Long> list) {
        if (CollectionUtils.isEmpty(list)) {
            return RespVOBuilder.failure("参数不能为空");
        }
        Integer num = shareDataService.deleteBatchIds(list);
        if (num == 0) {
            return RespVOBuilder.failure("批量删除DataFile失败");
        } else {
            return RespVOBuilder.success("批量删除DataFile成功");
        }
    }


    @ApiOperation(value = "条件查询DataFile", notes = "条件查询DataFile")
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


    @ApiOperation(value = "条件查询DataFile", notes = "条件查询DataFile")
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
        return shareDataService.selectBybatchNums(batchNums, tags);
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
        RespVO<RespDataVO<DataFile>> respVO = iDataFileService.selectBybatchNums(batchNums, tags);
        if (respVO.getRetCode() == RespConsts.SUCCESS_RESULT_CODE) {
            RespDataVO<DataFile> dataVO = respVO.getInfo();
            if (dataVO != null && !CollectionUtils.isEmpty(dataVO.getList())) {
                List<ShareData> shareDatas = new ArrayList<>();
                for (DataFile dataFile : dataVO.getList()) {
                    ShareData sd = new ShareData();
                    BeanUtils.copyProperties(dataFile, sd);
                    //TODO  更新审核时间   审核人信息
                    sd.setLastUpdateDate(new Date());
                    sd.setLastUpdatedBy(1L);
                    shareDatas.add(sd);
                }
                Integer batchInsert = shareDataService.batchInsert(shareDatas);
                if (batchInsert > 0) {
                    return RespVOBuilder.success();
                }
            }
        }
        return RespVOBuilder.failure();
    }


}
