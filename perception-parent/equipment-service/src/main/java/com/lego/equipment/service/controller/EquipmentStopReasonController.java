package com.lego.equipment.service.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.equipment.service.service.IEquipmentStopReasonService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.equipment.model.entity.EquipmentStopReason;
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
 * @date 2019-10-06 09:29:16
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/equipmentStopReason")
@Api(value = "equipmentStopReason", description = "停机原因管理")
@Resource(value = "equipmentStopReason", desc = "停机原因管理")
public class EquipmentStopReasonController {
    @Autowired
    private IEquipmentStopReasonService iEquipmentStopReasonService;

    /**
     * 分页查询数据
     */
    @ApiOperation(value = "查询停机原因", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "select_paged/{pageSize}/{pageIndex}", desc = "查询停机原因")
    @RequestMapping(value = "/select_paged", method = RequestMethod.GET)
    public RespVO<PagedResult<EquipmentStopReason>> selectPaged(@ModelAttribute EquipmentStopReason equipmentStopReason,
                                                                @PathParam(value = "") Page page) {
        PagedResult<EquipmentStopReason> stopReasonPagedResult = iEquipmentStopReasonService.selectPaged(equipmentStopReason, page);
        return RespVOBuilder.success(stopReasonPagedResult);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @ApiOperation(value = "查询停机原因", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "停机原因ID", dataType = "String", required = true, paramType = "query"),
    })
    @Operation(value = "select_by_id", desc = "查询停机原因")
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    public RespVO<EquipmentStopReason> selectByPrimaryKey(@RequestParam Long id) {
        EquipmentStopReason po = iEquipmentStopReasonService.selectByPrimaryKey(id);
        return RespVOBuilder.success(po);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @ApiOperation(value = "删除停机原因", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "停机原因ID", dataType = "String", required = true, paramType = "query"),
    })
    @Operation(value = "delete_by_id", desc = "删除停机原因")
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.DELETE)
    public RespVO deleteByPrimaryKey(@RequestParam Long id) {
        Integer num = iEquipmentStopReasonService.deleteByPrimaryKey(id);
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
    @ApiOperation(value = "新增停机原因", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "save_equipmentStopReason", desc = "新增停机原因")
    @RequestMapping(value = "/save_equipmentStopReason", method = RequestMethod.POST)
    public RespVO insert(@RequestBody EquipmentStopReason equipmentStopReason) {
        Integer num = iEquipmentStopReasonService.insertSelective(equipmentStopReason);
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
    @ApiOperation(value = "修改停机原因", httpMethod = "PUT")
    @ApiImplicitParams({

    })
    @Operation(value = "update_equipmentStopReason", desc = "修改停机原因")
    @RequestMapping(value = "/update_equipmentStopReason", method = RequestMethod.PUT)
    public RespVO updateByPrimaryKeySelective(@RequestBody EquipmentStopReason equipmentStopReason) {
        Integer num = iEquipmentStopReasonService.updateByPrimaryKeySelective(equipmentStopReason);
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
    @ApiOperation(value = "查询停机原因", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "query_list", desc = "查询停机原因")
    @RequestMapping(value = "/query_list", method = RequestMethod.POST)
    public RespVO<RespDataVO<EquipmentStopReason>> queryByCondition(@RequestBody EquipmentStopReason equipmentStopReason) {
        List<EquipmentStopReason> list = iEquipmentStopReasonService.query(equipmentStopReason);
        return RespVOBuilder.success(list);
    }

}
