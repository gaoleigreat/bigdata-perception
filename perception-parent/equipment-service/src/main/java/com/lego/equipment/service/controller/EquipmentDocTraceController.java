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

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-06 03:57:48
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/equipmentDocTrace")
@Api(value = "equipmentDocTrace", description = "设备文档轨迹管理")
@Resource(value = "equipmentDocTrace", desc = "设备文档轨迹管理")
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
     * 通过ID删除
     *
     * @return
     */
    @ApiOperation(value = "删除设备文档轨迹", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "轨迹ID", dataType = "String", required = true, paramType = "query"),
    })
    @Operation(value = "delete_by_id", desc = "删除设备文档轨迹")
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.DELETE)
    public RespVO deleteByPrimaryKey(Long id) {
        Integer num = iEquipmentDocTraceService.deleteByPrimaryKey(id);
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
    @ApiOperation(value = "新增设备文档轨迹", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "insert", desc = "新增设备文档轨迹")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public RespVO insert(@RequestParam(value = "equipmentId", required = true) Long equipmentId,
                         @RequestParam(value = "equipmentCode", required = false) String equipmentCode,
                         @RequestParam(required = false) String remark,
                         @RequestParam MultipartFile file) {
        EquipmentDocTrace equipmentDocTrace = new EquipmentDocTrace();
        equipmentDocTrace.setEquipmentCode(equipmentCode);
        equipmentDocTrace.setEquipmentId(equipmentId);
        equipmentDocTrace.setType(1);
        RespVO<RespDataVO<DataFile>> stringRespVO = fileClient.upLoad(new MultipartFile[]{file}, remark, "");
        if (stringRespVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("文件上传失败");
        }
        RespDataVO<DataFile> dataVO = stringRespVO.getInfo();
        if (dataVO == null || CollectionUtils.isEmpty(dataVO.getList())) {
            return RespVOBuilder.failure("文件上传失败");
        }
        DataFile dataFile = dataVO.getList().get(0);
        equipmentDocTrace.setFileId(dataFile.getId());
        equipmentDocTrace.setFileUrl(dataFile.getFileUrl());
        equipmentDocTrace.setPreviewUrl(dataFile.getPreviewUrl());
        Integer num = iEquipmentDocTraceService.insertSelective(equipmentDocTrace);
        if (num > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }

    /**
     * 修改数据
     *
     * @return
     */
    @ApiOperation(value = "更新设备文档轨迹", httpMethod = "PUT")
    @ApiImplicitParams({

    })
    @Operation(value = "update_equipmentDocTrace", desc = "更新设备文档轨迹")
    @RequestMapping(value = "/update_equipmentDocTrace", method = RequestMethod.PUT)
    public RespVO updateByPrimaryKeySelective(@RequestBody EquipmentDocTrace equipmentDocTrace) {
        Integer num = iEquipmentDocTraceService.updateByPrimaryKeySelective(equipmentDocTrace);
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
