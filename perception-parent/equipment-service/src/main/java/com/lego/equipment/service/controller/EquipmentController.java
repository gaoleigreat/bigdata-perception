package com.lego.equipment.service.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.equipment.service.service.IEquipmentService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.equipment.model.entity.Equipment;
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
 * @date 2019-09-24 07:43:07
 * @since jdk 1.8
 */
@Api(value = "tplequipment", description = "设备管理")
@RestController
@RequestMapping("/tplequipment")
@Resource(value = "tplequipment", desc = "设备管理")
public class EquipmentController {


    @Autowired
    private IEquipmentService iEquipmentService;

    /**
     * 分页查询数据
     */
    @ApiOperation(value = "查询设备信息", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "select_paged", desc = "查询设备信息")
    @RequestMapping(value = "/select_paged/{pageSize}/{pageIndex}",method = RequestMethod.GET)
    public RespVO<PagedResult<Equipment>> selectPaged(@PathParam(value = "") Page page,
                                                      @ModelAttribute Equipment equipment) {
        PagedResult<Equipment> pagedResult = iEquipmentService.selectPaged(equipment, page);
        return RespVOBuilder.success(pagedResult);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @ApiOperation(value = "查询设备信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "设备信息id", dataType = "long", required = true, paramType = "query"),
    })
    @Operation(value = "select_by_id", desc = "查询设备信息")
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    public RespVO<Equipment> selectByPrimaryKey(@RequestParam(value = "id") Long id) {
        Equipment po = iEquipmentService.selectByPrimaryKey(id);
        return RespVOBuilder.success(po);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @ApiOperation(value = "删除设备信息", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "设备信息id", dataType = "long", required = true, paramType = "query"),
    })
    @Operation(value = "delete_by_id", desc = "删除设备信息")
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.DELETE)
    public RespVO<Integer> deleteByPrimaryKey(Long id) {
        Integer num = iEquipmentService.deleteByPrimaryKey(id);
        return RespVOBuilder.success();
    }

    /**
     * 新增数据
     *
     * @return
     */
    @ApiOperation(value = "新增设备信息", httpMethod = "POST")
    @ApiImplicitParams({
    })
    @Operation(value = "save_tplEquipment", desc = "新增设备信息")
    @RequestMapping(value = "/save_tplEquipment", method = RequestMethod.POST)
    public RespVO<Integer> insert(@RequestBody Equipment tplEquipment) {
        Integer num = iEquipmentService.insertSelective(tplEquipment);
        return RespVOBuilder.success();
    }

    /**
     * 修改数据
     *
     * @return
     */
    @ApiOperation(value = "修改设备信息", httpMethod = "PUT")
    @ApiImplicitParams({

    })
    @Operation(value = "update_tplEquipment", desc = "修改设备信息")
    @RequestMapping(value = "/update_tplEquipment", method = RequestMethod.PUT)
    public RespVO<Integer> updateByPrimaryKeySelective(@RequestBody Equipment tplEquipment) {
        Integer num = iEquipmentService.updateByPrimaryKeySelective(tplEquipment);
        return RespVOBuilder.success();
    }


    /**
     * 查询列表
     *
     * @return
     */
    @ApiOperation(value = "查询设备信息", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "query_list", desc = "查询设备信息")
    @RequestMapping(value = "/query_list", method = RequestMethod.GET)
    public RespVO<RespDataVO<Equipment>> queryByCondition(@ModelAttribute Equipment tplEquipment) {
        List<Equipment> list = iEquipmentService.query(tplEquipment);
        return RespVOBuilder.success(list);
    }

}
