package com.lego.equipment.service.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.equipment.service.service.IEquipmentMaintenanceService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.equipment.model.entity.EquipmentMaintenance;
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
 * @date 2019-10-06 09:29:09
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/equipmentMaintenance")
@Api(value = "equipmentMaintenance", description = "设备保养项管理")
@Resource(value = "equipmentMaintenance", desc = "设备保养项管理")
public class EquipmentMaintenanceController {

    @Autowired
    private IEquipmentMaintenanceService equipmentMaintenanceService;

    /**
     * 分页查询数据
     */
    @ApiOperation(value = "查询保养项", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "select_paged", desc = "查询保养项")
    @RequestMapping(value = "/select_paged/{pageSize}/{pageIndex}",method = RequestMethod.GET)
    public RespVO<PagedResult<EquipmentMaintenance>> selectPaged(@ModelAttribute EquipmentMaintenance equipmentMaintenance,
                                                                 @PathParam(value = "") Page page) {
        PagedResult<EquipmentMaintenance> selectPaged = equipmentMaintenanceService.selectPaged(equipmentMaintenance, page);
        return RespVOBuilder.success(selectPaged);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @ApiOperation(value = "查询保养项", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "保养项ID", dataType = "String", required = true, paramType = "query"),
    })
    @Operation(value = "select_by_id", desc = "查询保养项")
    @RequestMapping(value = "/select_by_id",method = RequestMethod.GET)
    public RespVO<EquipmentMaintenance> selectByPrimaryKey(@RequestParam Long id) {
        EquipmentMaintenance po = equipmentMaintenanceService.selectByPrimaryKey(id);
        return RespVOBuilder.success(po);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @ApiOperation(value = "删除保养项", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "保养项ID", dataType = "String", required = true, paramType = "query"),
    })
    @Operation(value = "delete_by_id", desc = "删除保养项")
    @RequestMapping(value = "/delete_by_id",method = RequestMethod.DELETE)
    public RespVO deleteByPrimaryKey(@RequestParam Long id) {
        Integer num = equipmentMaintenanceService.deleteByPrimaryKey(id);
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
    @ApiOperation(value = "新增保养项", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "save_equipmentMaintenance", desc = "新增保养项")
    @RequestMapping(value = "/save_equipmentMaintenance",method = RequestMethod.POST)
    public RespVO insert(@RequestBody EquipmentMaintenance equipmentMaintenance) {
        Integer num = equipmentMaintenanceService.insertSelective(equipmentMaintenance);
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
    @ApiOperation(value = "修改保养项", httpMethod = "PUT")
    @ApiImplicitParams({

    })
    @Operation(value = "update_equipmentMaintenance", desc = "修改保养项")
    @RequestMapping(value = "/update_equipmentMaintenance",method = RequestMethod.PUT)
    public RespVO updateByPrimaryKeySelective(@RequestBody EquipmentMaintenance equipmentMaintenance) {
        Integer num = equipmentMaintenanceService.updateByPrimaryKeySelective(equipmentMaintenance);
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
    @ApiOperation(value = "查询保养项", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "query_list", desc = "查询保养项")
    @RequestMapping(value = "/query_list",method = RequestMethod.POST)
    public RespVO<RespDataVO<EquipmentMaintenance>> queryByCondition(@RequestBody EquipmentMaintenance equipmentMaintenance) {
        List<EquipmentMaintenance> list = equipmentMaintenanceService.query(equipmentMaintenance);
        return RespVOBuilder.success(list);
    }

}
