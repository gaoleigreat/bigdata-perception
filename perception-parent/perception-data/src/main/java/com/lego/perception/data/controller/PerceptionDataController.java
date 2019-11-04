package com.lego.perception.data.controller;


import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.data.model.entity.PerceptionStructuredData;
import com.lego.framework.data.model.entity.PerceptionUnstructuredData;
import com.lego.framework.system.model.entity.ShareData;
import com.lego.perception.data.service.IPerceptionStructuredDataService;
import com.lego.perception.data.service.IPerceptionUnstructuredDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * PerceptionStructuredData
 *
 * @author ¸ßÀÚ
 * @email 513684652@qq.com
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/perceptionData")
@Api(value = "perceptionData", tags = "perceptionData管理")
@Validated
@Slf4j
public class PerceptionDataController {
    @Autowired
    private IPerceptionStructuredDataService perceptionStructuredDataService;

    @Autowired
    private IPerceptionUnstructuredDataService perceptionUnstructuredDataService;

    @ApiOperation(value = "通过批次号查询感知数据", notes = "通过批次号查询感知数据")
    @ApiImplicitParams({
    })
    @Operation(value = "select_perception_by_batchnum", desc = "通过批次号查询感知数据")
    @GetMapping(value = "/select_perception_by_batchnum")
    public RespVO<RespDataVO<ShareData>> selectPerceptionByBatchnum(@RequestParam(value = "batchnum") String batchnum) {
        List<ShareData> shareDataList = new ArrayList<>();
        PerceptionStructuredData perceptionStructuredData = new PerceptionStructuredData();
        perceptionStructuredData.setDeleteFlag(0);
        perceptionStructuredData.setPublishFlag(0);
        perceptionStructuredData.setBatchNum(batchnum);
        List<PerceptionStructuredData> perceptionStructuredDataList = perceptionStructuredDataService.query(perceptionStructuredData);
        perceptionStructuredDataList.stream().forEach(psd -> {
            shareDataList.add(convertPerceptionStructuredData2ShareData(psd));
        });


        PerceptionUnstructuredData perceptionUnstructuredData = new PerceptionUnstructuredData();

        perceptionUnstructuredData.setDeleteFlag(0);
        perceptionUnstructuredData.setPublishFlag(0);
        perceptionUnstructuredData.setBatchNum(batchnum);
        List<PerceptionUnstructuredData> perceptionUnstructuredDataList = perceptionUnstructuredDataService.query(perceptionUnstructuredData);
        perceptionUnstructuredDataList.stream().forEach(psd -> {
            shareDataList.add(convertPerceptionUnstructuredData2ShareData(psd));
        });
        return RespVOBuilder.success(shareDataList);
    }

    @ApiOperation(value = "通过批次号更新", notes = "通过批次号更新")
    @ApiImplicitParams({
    })
    @Operation(value = "update_perception_by_batchnum", desc = "通过批次号更新")
    @GetMapping(value = "/update_perception_by_batchnum")
    public RespVO updatePerceptionByBatchnum(@RequestParam(value = "batchnum") String batchnum) {
        PerceptionStructuredData perceptionStructuredData = new PerceptionStructuredData();
        perceptionStructuredData.setBatchNum(batchnum);
        perceptionStructuredData.setDeleteFlag(0);
        perceptionStructuredData.setPublishFlag(0);
        List<PerceptionStructuredData> perceptionStructuredDataList = perceptionStructuredDataService.query(perceptionStructuredData);
        perceptionStructuredDataList.stream().forEach(psd -> {
            psd.setPublishFlag(1);
        });
        int result = perceptionStructuredDataService.batchUpdate(perceptionStructuredDataList);


        PerceptionUnstructuredData perceptionUnstructuredData = new PerceptionUnstructuredData();
        perceptionUnstructuredData.setBatchNum(batchnum);
        perceptionUnstructuredData.setDeleteFlag(0);
        perceptionUnstructuredData.setPublishFlag(0);
        List<PerceptionUnstructuredData> perceptionUnstructuredDataList = perceptionUnstructuredDataService.query(perceptionUnstructuredData);
        perceptionUnstructuredDataList.stream().forEach(psd -> {
            psd.setPublishFlag(1);
        });

        result = result + perceptionUnstructuredDataService.batchUpdate(perceptionUnstructuredDataList);
        return RespVOBuilder.success(result);
    }


    public ShareData convertPerceptionStructuredData2ShareData(PerceptionStructuredData perceptionStructuredData) {
        ShareData shareData = new ShareData();
        shareData.setBatchNum(perceptionStructuredData.getBatchNum());
        shareData.setBusinessModule(perceptionStructuredData.getBusinessModule());
        shareData.setSourceModule(perceptionStructuredData.getSourceModule());
        shareData.setDataType(1);
        shareData.setCreationDate(perceptionStructuredData.getCreationDate());
        shareData.setLastUpdateDate(perceptionStructuredData.getLastUpdateDate());
        shareData.setCreatedBy(1L);
        shareData.setLastUpdatedBy(1L);
        shareData.setName(perceptionStructuredData.getName());
        shareData.setDataSize(perceptionStructuredData.getSize());
        shareData.setRemark(perceptionStructuredData.getRemark());
        shareData.setTags(perceptionStructuredData.getTags());
        shareData.setProjectId(perceptionStructuredData.getProjectId());
        return shareData;
    }

    public ShareData convertPerceptionUnstructuredData2ShareData(PerceptionUnstructuredData perceptionUnstructuredData) {
        ShareData shareData = new ShareData();
        shareData.setBatchNum(perceptionUnstructuredData.getBatchNum());
        shareData.setBusinessModule(perceptionUnstructuredData.getBusinessModule());
        shareData.setSourceModule(perceptionUnstructuredData.getSourceModule());
        shareData.setDataType(2);
        shareData.setCreationDate(perceptionUnstructuredData.getCreationDate());
        shareData.setLastUpdateDate(perceptionUnstructuredData.getLastUpdateDate());
        shareData.setCreatedBy(1L);
        shareData.setLastUpdatedBy(1L);
        shareData.setName(perceptionUnstructuredData.getName());
        shareData.setDataSize(perceptionUnstructuredData.getSize());
        shareData.setRemark(perceptionUnstructuredData.getRemark());
        shareData.setTags(perceptionUnstructuredData.getTags());
        shareData.setProjectId(perceptionUnstructuredData.getProjectId());
        return shareData;
    }

}
