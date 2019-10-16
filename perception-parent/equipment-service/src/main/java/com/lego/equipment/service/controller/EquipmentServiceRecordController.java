package com.lego.equipment.service.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.equipment.service.service.IEquipmentServiceRecordService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.equipment.model.entity.EquipmentServiceRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;


/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-10-11 01:58:10
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/equipmentServiceRecord")
@Api(value = "设备维修记录管理", description = "设备维修记录管理")
public class EquipmentServiceRecordController {
    @Autowired
    private IEquipmentServiceRecordService equipmentServiceRecordService;


    /**
     * 分页查询数据
     */
    @ApiOperation(value = "查询维修记录", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "select_paged", desc = "查询维修记录")
    @RequestMapping(value = "/select_paged/{pageSize}/{pageIndex}", method = RequestMethod.POST)
    public RespVO<PagedResult<EquipmentServiceRecord>> selectPaged(@ModelAttribute EquipmentServiceRecord equipmentServiceRecord,
                                                                   @PathParam(value = "") Page page) {
        PagedResult<EquipmentServiceRecord> equipmentServiceRecordPagedResult = equipmentServiceRecordService.selectPaged(equipmentServiceRecord, page);
        return RespVOBuilder.success(equipmentServiceRecordPagedResult);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @ApiOperation(value = "查询维修记录", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "维修记录ID", dataType = "Long", required = true, paramType = "query"),
    })
    @Operation(value = "select_by_id", desc = "查询维修记录")
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    public RespVO<EquipmentServiceRecord> selectByPrimaryKey(@RequestParam Long id) {
        EquipmentServiceRecord po = equipmentServiceRecordService.selectByPrimaryKey(id);
        return RespVOBuilder.success(po);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @ApiOperation(value = "删除维修记录", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "维修记录ID", dataType = "Long", required = true, paramType = "query"),
    })
    @Operation(value = "delete_by_id", desc = "删除维修记录")
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.DELETE)
    public RespVO deleteByPrimaryKey(@RequestParam Long id) {
        Integer num = equipmentServiceRecordService.deleteByPrimaryKey(id);
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
    @ApiOperation(value = "新增维修记录", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "save_equipmentServiceRecord", desc = "新增维修记录")
    @RequestMapping(value = "/save_equipmentServiceRecord", method = RequestMethod.POST)
    public RespVO insert(@RequestBody EquipmentServiceRecord equipmentServiceRecord) {
        Integer num = equipmentServiceRecordService.insert(equipmentServiceRecord);
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
    @ApiOperation(value = "修改维修记录", httpMethod = "PUT")
    @ApiImplicitParams({

    })
    @Operation(value = "update_equipmentServiceRecord", desc = "修改维修记录")
    @RequestMapping(value = "/update_equipmentServiceRecord", method = RequestMethod.PUT)
    public RespVO updateByPrimaryKeySelective(@RequestBody EquipmentServiceRecord equipmentServiceRecord) {
        Integer num = equipmentServiceRecordService.updateByPrimaryKeySelective(equipmentServiceRecord);
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
    @ApiOperation(value = "查询维修记录", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "query_list", desc = "查询维修记录")
    @RequestMapping(value = "/query_list", method = RequestMethod.POST)
    public RespVO<RespDataVO<EquipmentServiceRecord>> queryByCondition(@RequestBody EquipmentServiceRecord equipmentServiceRecord) {
        List<EquipmentServiceRecord> list = equipmentServiceRecordService.query(equipmentServiceRecord);
        return RespVOBuilder.success(list);
    }

}
