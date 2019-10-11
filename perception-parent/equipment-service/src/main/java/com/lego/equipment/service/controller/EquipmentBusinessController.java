package com.lego.equipment.service.controller;

import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.equipment.service.service.IEquipmentBusinessService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.business.feign.BusinessClient;
import com.lego.framework.business.feign.CrudClient;
import com.lego.framework.business.model.entity.Business;
import com.lego.framework.equipment.model.entity.EquipmentBusiness;
import com.lego.framework.template.feign.TemplateFeignClient;
import com.lego.framework.template.model.entity.FormTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;


/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-24 10:07:49
 * @since jdk 1.8
 */
@Api(value = "equipmentBusiness", description = "设备业务管理")
@Resource(value = "equipmentBusiness", desc = "设备业务管理")
@RestController
@RequestMapping("/equipmentBusiness")
@Slf4j
@Validated
public class EquipmentBusinessController {

    @Autowired
    private IEquipmentBusinessService equipmentBusinessService;

    @Autowired
    private CrudClient crudClient;

    @Autowired
    private BusinessClient businessClient;

    @Autowired
    private TemplateFeignClient templateFeignClient;

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
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.DELETE)
    public RespVO deleteByPrimaryKey(Long id) {
        Integer num = equipmentBusinessService.deleteByPrimaryKey(id);
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
    @ApiOperation(value = "新增设备业务信息", httpMethod = "POST")
    @ApiImplicitParams({
    })
    @Operation(value = "save_equipmentBusiness", desc = "新增设备业务信息")
    @RequestMapping(value = "/save_equipmentBusiness", method = RequestMethod.POST)
    @Transactional(rollbackFor = RuntimeException.class)
    public RespVO insert(@RequestBody EquipmentBusiness equipmentBusiness) {
        Long businessId = equipmentBusiness.getBusinessId();
        Long equipmentId = equipmentBusiness.getEquipmentId();
        EquipmentBusiness queryEquipmentBusiness = new EquipmentBusiness();
        queryEquipmentBusiness.setBusinessId(businessId);
        queryEquipmentBusiness.setEquipmentId(equipmentId);
        List<EquipmentBusiness> query = equipmentBusinessService.query(queryEquipmentBusiness);
        if (!CollectionUtils.isEmpty(query)) {
            return RespVOBuilder.failure("设备业务信息已经存在");
        }
        RespVO<Business> respVO = businessClient.selectById(businessId);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("获取业务失败");
        }
        Business info = respVO.getInfo();
        if (info == null) {
            return RespVOBuilder.failure("获取业务失败");
        }
        String templateCode = info.getTemplateCode();
        RespVO<FormTemplate> formTemplateRespVO = templateFeignClient.findFormTemplateByCode(templateCode);

        if (formTemplateRespVO == null) {
            return RespVOBuilder.failure("模板服务报错");
        }
        if (formTemplateRespVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("模板服务报错");
        }
        FormTemplate formTemplate = formTemplateRespVO.getInfo();

        if (formTemplate == null) {
            return RespVOBuilder.failure("模板不存在");
        }
        Integer num = equipmentBusinessService.insertSelective(equipmentBusiness);
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
    @ApiOperation(value = "修改设备业务信息", httpMethod = "PUT")
    @ApiImplicitParams({

    })
    @Operation(value = "update_equipmentBusiness", desc = "修改设备业务信息")
    @RequestMapping(value = "/update_equipmentBusiness", method = RequestMethod.PUT)
    public RespVO updateByPrimaryKeySelective(@RequestBody EquipmentBusiness equipmentBusiness) {
        Integer num = equipmentBusinessService.updateByPrimaryKeySelective(equipmentBusiness);
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
