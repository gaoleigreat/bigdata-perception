package com.lego.equipment.service.controller;

import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.equipment.service.service.IEquipmentDocTraceService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.context.ContextMap;
import com.lego.framework.equipment.model.entity.EquipmentDocTrace;
import com.lego.framework.file.feign.FileClient;
import com.lego.framework.system.model.entity.DataFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-06 03:57:48
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/equipmentDocTrace")
@Api(value = "equipmentDocTrace", description = "设备文档管理")
@Resource(value = "equipmentDocTrace", desc = "设备文档管理")
public class EquipmentDocTraceController {
    @Autowired
    private IEquipmentDocTraceService iEquipmentDocTraceService;

    @Autowired
    private FileClient fileClient;

    /**
     * 分页查询数据
     */
    @ApiOperation(value = "查询设备文档轨迹", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "select_paged", desc = "查询设备文档轨迹")
    @RequestMapping(value = "/select_paged/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public RespVO<PagedResult<EquipmentDocTrace>> selectPaged(@ModelAttribute EquipmentDocTrace equipmentDocTrace,
                                                              @PathParam(value = "") Page page) {
        PagedResult<EquipmentDocTrace> selectPaged = iEquipmentDocTraceService.selectPaged(equipmentDocTrace, page);
        return RespVOBuilder.success(selectPaged);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @ApiOperation(value = "查询设备文档轨迹", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "轨迹ID", dataType = "String", required = true, paramType = "query"),
    })
    @Operation(value = "select_by_id", desc = "查询设备文档轨迹")
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    public RespVO<EquipmentDocTrace> selectByPrimaryKey(Long id) {
        EquipmentDocTrace po = iEquipmentDocTraceService.selectByPrimaryKey(id);
        return RespVOBuilder.success(po);
    }

    /**
     * 新增数据
     *
     * @return
     */
    @ApiOperation(value = "新增设备文档", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "equipmentId", value = "设备类型id", dataType = "long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "equipmentCode", value = "设备编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "文档类型(0-机械图纸；1-电气图纸；2-液压图纸；3-维修方案；4-会议纪要；5-转场记录；6-维修合同;7-其他)", dataType = "int", required = true, paramType = "query"),

    })
    @Operation(value = "insert", desc = "新增设备文档")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public RespVO insert(@RequestParam Long equipmentId,
                         @RequestParam(required = false) String equipmentCode,
                         @RequestParam(required = false) String remark,
                         @RequestParam Integer type,
                         @RequestParam MultipartFile[] files) {
        Map<Integer, String> docTypes = ContextMap.getDocTypes();
        RespVO<RespDataVO<DataFile>> stringRespVO = fileClient.upLoad(files, remark, docTypes.get(type) + ",", null);
        if (stringRespVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("文件上传失败");
        }
        RespDataVO<DataFile> dataVO = stringRespVO.getInfo();
        if (dataVO == null || CollectionUtils.isEmpty(dataVO.getList())) {
            return RespVOBuilder.failure("文件上传失败");
        }
        List<EquipmentDocTrace> equipmentDocTraceList = new ArrayList<>();
        List<DataFile> dataFiles = dataVO.getList();
        for (DataFile dataFile : dataFiles) {
            EquipmentDocTrace equipmentDocTrace = new EquipmentDocTrace();
            equipmentDocTrace.setEquipmentCode(equipmentCode);
            equipmentDocTrace.setEquipmentId(equipmentId);
            equipmentDocTrace.setType(type);
            equipmentDocTrace.setFileId(dataFile.getId());
            equipmentDocTrace.setFileUrl(dataFile.getFileUrl());
            equipmentDocTrace.setPreviewUrl(dataFile.getPreviewUrl());
            equipmentDocTraceList.add(equipmentDocTrace);
        }
        Integer num = iEquipmentDocTraceService.batchInsert(equipmentDocTraceList);
        if (num > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }

    /**
     * 新增数据
     *
     * @return
     */
    @ApiOperation(value = "更新设备文档", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "update", desc = "更新设备文档")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RespVO update(@RequestParam Long fileId,
                         @RequestParam(required = false) String remark,
                         @RequestParam MultipartFile file) {
        RespVO<RespDataVO<DataFile>> stringRespVO = fileClient.upLoad(new MultipartFile[]{file}, remark, "设备文档,", fileId);
        if (stringRespVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("文件上传失败");
        }
        RespDataVO<DataFile> dataVO = stringRespVO.getInfo();
        if (dataVO == null || CollectionUtils.isEmpty(dataVO.getList())) {
            return RespVOBuilder.failure("文件上传失败");
        }
        List<EquipmentDocTrace> equipmentDocTraceList = new ArrayList<>();
        List<DataFile> dataFiles = dataVO.getList();
        EquipmentDocTrace queryEquipmentDocTrace = new EquipmentDocTrace();
        queryEquipmentDocTrace.setFileId(fileId);
        List<EquipmentDocTrace> list = iEquipmentDocTraceService.query(queryEquipmentDocTrace);
        if (CollectionUtils.isEmpty(list)) {
            return RespVOBuilder.failure("更新的设备文档不存在");
        }
        for (DataFile dataFile : dataFiles) {
            EquipmentDocTrace equipmentDocTrace = new EquipmentDocTrace();
            equipmentDocTrace.setEquipmentCode(list.get(0).getEquipmentCode());
            equipmentDocTrace.setEquipmentId(list.get(0).getId());
            equipmentDocTrace.setType(list.get(0).getType());
            equipmentDocTrace.setFileId(dataFile.getId());
            equipmentDocTrace.setFileUrl(dataFile.getFileUrl());
            equipmentDocTrace.setPreviewUrl(dataFile.getPreviewUrl());
            equipmentDocTraceList.add(equipmentDocTrace);
        }
        Integer num = iEquipmentDocTraceService.batchInsert(equipmentDocTraceList);
        if (num > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }


    /**
     * 查询列表
     *
     * @return
     */
    @ApiOperation(value = "查询设备文档轨迹", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "queryByCondition", desc = "查询设备文档轨迹")
    @RequestMapping(value = "/query_list", method = RequestMethod.POST)
    public RespVO<RespDataVO<EquipmentDocTrace>> queryByCondition(@RequestBody EquipmentDocTrace equipmentDocTrace) {
        List<EquipmentDocTrace> list = iEquipmentDocTraceService.query(equipmentDocTrace);
        return RespVOBuilder.success(list);
    }

}
