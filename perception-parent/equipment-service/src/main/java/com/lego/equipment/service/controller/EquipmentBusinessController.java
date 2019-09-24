package com.lego.equipment.service.controller;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.equipment.service.service.EquipmentBusinessService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.equipment.model.entity.EquipmentBusiness;
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
 * @date 2019-09-24 10:07:49
 * @since jdk 1.8
 */
@Api(value = "equipmentBusiness",description = "设备业务管理")
@Resource(value = "equipmentBusiness", desc = "设备业务管理")
@RestController
@RequestMapping("/equipmentBusiness")
public class EquipmentBusinessController {
    @Autowired
    private EquipmentBusinessService equipmentBusinessService;

    /**
     * 分页查询数据
     */
    @ApiOperation(value = "查询设备业务信息", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "select_paged", desc = "查询设备业务信息")
    @RequestMapping(value = "/select_paged/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public RespVO<PagedResult<EquipmentBusiness>> selectPaged(EquipmentBusiness equipmentBusiness,
                                                              @PathParam(value = "") Page page) {
        PagedResult<EquipmentBusiness> pageResult = equipmentBusinessService.selectPaged(equipmentBusiness, page);
        return RespVOBuilder.success(pageResult);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @ApiOperation(value = "查询设备业务信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "设备信息id", dataType = "long", required = true, paramType = "query"),
    })
    @Operation(value = "select_by_id", desc = "查询设备业务信息")
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    public RespVO<EquipmentBusiness> selectByPrimaryKey(@RequestParam(value = "id") Long id) {
        EquipmentBusiness po = equipmentBusinessService.selectByPrimaryKey(id);
        return RespVOBuilder.success(po);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @ApiOperation(value = "删除设备业务信息", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "设备信息id", dataType = "long", required = true, paramType = "query"),
    })
    @Operation(value = "delete_by_id", desc = "删除设备业务信息")
    @RequestMapping(value = "/delete_by_id",method = RequestMethod.DELETE)
    public RespVO deleteByPrimaryKey(Long id) {
        Integer num = equipmentBusinessService.deleteByPrimaryKey(id);
        return RespVOBuilder.success();
    }

    /**
     * 新增数据
     *
     * @return
     */
    @ApiOperation(value = "新增设备业务信息", httpMethod = "POST")
    @ApiImplicitParams({
    })
    @Operation(value = "save_equipmentBusiness", desc = "新增设备业务信息")
    @RequestMapping(value = "/save_equipmentBusiness", method = RequestMethod.POST)
    public RespVO insert(@RequestBody EquipmentBusiness equipmentBusiness) {
        Integer num = equipmentBusinessService.insertSelective(equipmentBusiness);
        return RespVOBuilder.success();
    }

    /**
     * 修改数据
     *
     * @return
     */
    @ApiOperation(value = "修改设备业务信息", httpMethod = "PUT")
    @ApiImplicitParams({

    })
    @Operation(value = "update_equipmentBusiness", desc = "修改设备业务信息")
    @RequestMapping(value = "/update_equipmentBusiness",method = RequestMethod.PUT)
    public RespVO updateByPrimaryKeySelective(@RequestBody EquipmentBusiness equipmentBusiness) {
        Integer num = equipmentBusinessService.updateByPrimaryKeySelective(equipmentBusiness);
        return RespVOBuilder.success();
    }


    /**
     * 查询列表
     *
     * @return
     */
    @ApiOperation(value = "查询设备业务信息", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "query_list", desc = "查询设备业务信息")
    @RequestMapping(value = "/query_list", method = RequestMethod.GET)
    public RespVO<RespDataVO<EquipmentBusiness>> queryByCondition(@ModelAttribute EquipmentBusiness equipmentBusiness) {
        List<EquipmentBusiness> list = equipmentBusinessService.query(equipmentBusiness);
        return RespVOBuilder.success(list);
    }

}
