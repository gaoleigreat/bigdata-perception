package com.lego.equipment.service.controller;

import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.equipment.service.service.IEquipmentServiceRecordService;
import com.lego.equipment.service.service.IEquipmentServiceService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.equipment.model.entity.EquipmentDocTrace;
import com.lego.framework.equipment.model.entity.EquipmentService;
import com.lego.framework.equipment.model.entity.EquipmentServiceRecord;
import com.lego.framework.file.feign.FileClient;
import com.lego.framework.system.model.entity.DataFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/equipmentservice")
@Api(value = "设备维修管理", description = "设备维修管理")
public class EquipmentServiceController {

    @Autowired
    private IEquipmentServiceService equipmentServiceService;

    @Autowired
    private IEquipmentServiceRecordService equipmentServiceRecordService;

    @Autowired
    private FileClient fileClient;

    /**
     * 分页查询数据
     */
    @ApiOperation(value = "查询维修设备", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "select_paged", desc = "查询维修设备")
    @RequestMapping(value = "/select_paged/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public RespVO<PagedResult<EquipmentService>> selectPaged(@ModelAttribute EquipmentService equipmentService,
                                                             @PathParam(value = "") Page page) {

        PagedResult<EquipmentService> equipmentCostPagedResult = equipmentServiceService.selectPaged(equipmentService, page);
        return RespVOBuilder.success(equipmentCostPagedResult);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @ApiOperation(value = "删除维修项", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "维修Id", required = true, dataType = "long", paramType = "query"),
    })
    @Operation(value = "delete_by_id", desc = "删除维修项")
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.DELETE)
    public RespVO<Integer> deleteByPrimaryKey(@RequestParam Long id) {

        Integer num = equipmentServiceService.deleteByPrimaryKey(id);
        if (num > 0) {
            return RespVOBuilder.success(num);
        }
        return RespVOBuilder.failure("删除维修项失败");
    }


    /**
     * 新增数据
     *
     * @return
     */
    @ApiOperation(value = "新增维修项", httpMethod = "POST")
    @ApiImplicitParams({
    })
    @Operation(value = "save_tplEquipmentService", desc = "新增维修项")
    @RequestMapping("/save_tplEquipmentService")
    public RespVO<Integer> insert(@RequestBody EquipmentService equipmentService) {

        Integer num = equipmentServiceService.insert(equipmentService);
        if (num > 0) {
            return RespVOBuilder.success(num);
        }
        return RespVOBuilder.failure("新增维修项失败");
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @ApiOperation(value = "查询维修项", httpMethod = "GET")
    @ApiImplicitParam(value = "维修Id", name = "id", dataType = "long", required = true, paramType = "query")
    @Operation(value = "select_by_id", desc = "查询维修项")
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    public RespVO<EquipmentService> selectByPrimaryKey(@RequestParam("id") Long id) {
        EquipmentService po = equipmentServiceService.selectByPrimaryKey(id);
        return RespVOBuilder.success(po);
    }

    /**
     * 修改数据
     *
     * @return
     */
    @ApiOperation(value = "修改维修项", httpMethod = "PUT")
    @ApiImplicitParams({

    })
    @Operation(value = "update_tplEquipmentService", desc = "修改维修项")
    @RequestMapping("/update_tplEquipmentService")
    public RespVO<Integer> updateByPrimaryKeySelective(@RequestBody EquipmentService tplEquipmentService) {

        Integer num = equipmentServiceService.updateByPrimaryKeySelective(tplEquipmentService);
        if (num > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure("修改维修项失败");
    }


    /**
     * 查询列表
     *
     * @return
     */
    @ApiOperation(value = "查询维修项", httpMethod = "POST")
    @ApiImplicitParams({})
    @Operation(value = "query_list", desc = "查询列表")
    @RequestMapping("/query_list")
    public RespVO<RespDataVO<EquipmentService>> queryByCondition(@RequestBody EquipmentService tplEquipmentService) {

        List<EquipmentService> list = equipmentServiceService.query(tplEquipmentService);
        return RespVOBuilder.success(list);
    }

    /**
     * 上传附件
     */
    @ApiOperation(value = "设备维修上传附件", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "设备维修Id", name = "id", required = true, dataType = "long", paramType = "query")
    })
    @Operation(value = "uploadFile", desc = "上传附件")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public RespVO<Object> uploadFile(@RequestParam("id") Long id, @RequestParam MultipartFile file) {
        RespVO<RespDataVO<DataFile>> stringRespVO = fileClient.upLoadFile(new MultipartFile[]{file}, null, "设备维修附件");
        if (stringRespVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("文件上传失败");
        }
        RespDataVO<DataFile> dataVO = stringRespVO.getInfo();
        if (dataVO == null || CollectionUtils.isEmpty(dataVO.getList())) {
            return RespVOBuilder.failure("文件上传失败");
        }
        List<DataFile> dataFiles = dataVO.getList();
        EquipmentServiceRecord equipmentServiceRecord = new EquipmentServiceRecord();
        EquipmentService equipmentService = new EquipmentService();
        for (DataFile dataFile : dataFiles) {
            equipmentServiceRecord.setBatchNumber(dataFile.getBatchNum());
            equipmentService.setBatchNumber(dataFile.getBatchNum());
            break;
        }
        equipmentServiceRecord.setEquipmentServiceId(id);
        equipmentService.setId(id);
        Integer equipmentServiceNum = equipmentServiceService.updateByPrimaryKeySelective(equipmentService);
        Integer num = equipmentServiceRecordService.update(equipmentServiceRecord);
        if (num > 0 && equipmentServiceNum > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }

}
