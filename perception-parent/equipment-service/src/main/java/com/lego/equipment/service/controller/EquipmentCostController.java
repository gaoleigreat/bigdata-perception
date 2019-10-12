package com.lego.equipment.service.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.equipment.service.service.IEquipmentCostService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.equipment.model.entity.EquipmentCost;
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
@RequestMapping("/tplequipmentcost")
public class EquipmentCostController {
    @Autowired
    private IEquipmentCostService equipmentCostService;


    /**
     * 分页查询数据
     */
    @ApiOperation(value = "查询维修费用", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "select_paged", desc = "查询维修费用")
    @RequestMapping(value = "/select_paged", method = RequestMethod.POST)
    public RespVO<PagedResult<EquipmentCost>> selectPaged(@ModelAttribute EquipmentCost equipmentCost,
                                                                @PathParam(value = "") Page page) {
        PagedResult<EquipmentCost> equipmentCostPagedResult = equipmentCostService.selectPaged(equipmentCost, page);
        return RespVOBuilder.success(equipmentCostPagedResult);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @ApiOperation(value = "查询维修费用", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "维修费用ID", dataType = "String", required = true, paramType = "query"),
    })
    @Operation(value = "select_by_id", desc = "查询维修费用")
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    public RespVO<EquipmentCost> selectByPrimaryKey(@RequestParam Long id) {
        EquipmentCost po = equipmentCostService.selectByPrimaryKey(id);
        return RespVOBuilder.success(po);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @ApiOperation(value = "删除维修费用", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "维修费用ID", dataType = "String", required = true, paramType = "query"),
    })
    @Operation(value = "delete_by_id", desc = "删除维修费用")
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.DELETE)
    public RespVO deleteByPrimaryKey(@RequestParam Long id) {
        Integer num = equipmentCostService.deleteByPrimaryKey(id);
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
    @ApiOperation(value = "新增维修费用", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "save_equipmentCost", desc = "新增维修费用")
    @RequestMapping(value = "/save_equipmentCost", method = RequestMethod.POST)
    public RespVO insert(@RequestBody EquipmentCost equipmentStopReason) {
        Integer num = equipmentCostService.insertSelective(equipmentStopReason);
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
    @ApiOperation(value = "修改维修费用", httpMethod = "PUT")
    @ApiImplicitParams({

    })
    @Operation(value = "update_equipmentCost", desc = "修改维修费用")
    @RequestMapping(value = "/update_equipmentCost", method = RequestMethod.PUT)
    public RespVO updateByPrimaryKeySelective(@RequestBody EquipmentCost equipmentCost) {
        Integer num = equipmentCostService.updateByPrimaryKeySelective(equipmentCost);
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
    @ApiOperation(value = "查询维修费用", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "query_list", desc = "查询维修费用")
    @RequestMapping(value = "/query_list", method = RequestMethod.POST)
    public RespVO<RespDataVO<EquipmentCost>> queryByCondition(@RequestBody EquipmentCost equipmentCost) {
        List<EquipmentCost> list = equipmentCostService.query(equipmentCost);
        return RespVOBuilder.success(list);
    }

}
