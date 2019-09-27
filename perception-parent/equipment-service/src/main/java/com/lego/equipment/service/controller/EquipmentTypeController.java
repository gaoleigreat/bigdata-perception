package com.lego.equipment.service.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.framework.common.consts.HttpConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.equipment.service.service.EquipmentBusinessService;
import com.lego.equipment.service.service.IEquipmentService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.business.feign.BusinessClient;
import com.lego.framework.business.feign.CurdClient;
import com.lego.framework.business.model.entity.Business;
import com.lego.framework.equipment.model.entity.Equipment;
import com.lego.framework.equipment.model.entity.EquipmentBusiness;
import com.lego.framework.template.feign.TemplateFeignClient;
import com.lego.framework.template.model.entity.FormTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-24 07:43:07
 * @since jdk 1.8
 */
@Api(value = "equipment", description = "具体设备管理")
@RestController
@RequestMapping("/equipmentType")
@Resource(value = "equipment", desc = "具体设备管理")
public class EquipmentTypeController {


    @Autowired
    private TemplateFeignClient templateFeignClient;

    @Autowired
    private CurdClient curdClient;

    @Autowired
    private EquipmentBusinessService equipmentBusinessService;

    @Autowired
    private BusinessClient businessClient;


    /**
     * 通过id查询设备类型下面的业务
     *
     * @return
     */
    @ApiOperation(value = "查询设备类型下面的业务", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "设备类型", dataType = "String", required = true, paramType = "query"),
    })
    @Operation(value = "selectBusinessById", desc = "查询设备类型下面的业务")
    @RequestMapping(value = "/selectBusinessById", method = RequestMethod.GET)
    public RespVO selectBusinessById(@RequestParam(value = "type") Long id) {
        List<EquipmentBusiness> equipmentBusinesses = equipmentBusinessService.selectByEquipmentid(id);
        if (CollectionUtils.isEmpty(equipmentBusinesses)) {
            return RespVOBuilder.failure("对应设备不存在");
        }
        List<Map<String, Object>> results = new ArrayList<>();
        equipmentBusinesses.forEach(equipmentBusiness -> {
            RespVO<Business> businessRespVO = businessClient.selectById(equipmentBusiness.getBusinessId());
            if (businessRespVO != null && businessRespVO.getRetCode() == 1 && businessRespVO.getInfo() != null) {
                Business business = businessRespVO.getInfo();
                Map<String, Object> map = new HashMap<>();
                map.put("business", business);
                map.put("operation_type", equipmentBusiness.getOperationType());
                results.add(map);
            }

        });
        return RespVOBuilder.success(results);
    }

    @ApiOperation(value = "查询设备下面子设备", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "设备类型", dataType = "String", required = true, paramType = "query"),
    })
    @Operation(value = "selectEquipmentByType", desc = "查询设备下面子设备")
    @RequestMapping(value = "/selectEquipmentByType", method = RequestMethod.GET)
    public RespVO selectByPrimaryKey(@RequestParam(value = "type") String type) {
        RespVO<RespDataVO<FormTemplate>> respDataVORespVO = templateFeignClient.findByDataType(Integer.valueOf(type));
        if (respDataVORespVO == null) {
            ExceptionBuilder.operateFailException("模板服务不可用");
        }
        if (respDataVORespVO.getRetCode() != 1) {
            ExceptionBuilder.operateFailException("获取模板失败");
        }
        List<FormTemplate> formTemplates = respDataVORespVO.getInfo().getList();
        if (CollectionUtils.isEmpty(formTemplates)) {
            ExceptionBuilder.operateFailException("没有对应的表单模板");
        }
        FormTemplate formTemplateGet = formTemplates.get(0);
        return curdClient.queryBusinessData(formTemplateGet.getTemplateCode(), null);
    }


}
