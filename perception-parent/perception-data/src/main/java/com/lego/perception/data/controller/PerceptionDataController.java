package com.lego.perception.data.controller;

import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.data.model.entity.ShareData;
import com.lego.perception.data.service.IShareDataService;
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
    private IShareDataService iShareDataService;

    @ApiOperation(value = "通过批次号查询感知数据", notes = "通过批次号查询感知数据")
    @ApiImplicitParams({
    })
    @Operation(value = "selectPerceptionByBatchNum", desc = "通过批次号查询感知数据")
    @GetMapping(value = "/selectPerceptionByBatchNum")
    public RespVO<RespDataVO<ShareData>> selectPerceptionByBatchNum(@RequestParam(value = "batchNum") String batchNum) {
        List<String> batchNums = new ArrayList<>();
        batchNums.add(batchNum);
        List<ShareData> dataList = iShareDataService.selectDataByBatchNum(batchNums, null, 0);
        return RespVOBuilder.success(dataList);
    }
}
