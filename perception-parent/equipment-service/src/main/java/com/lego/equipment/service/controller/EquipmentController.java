package com.lego.equipment.service.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.equipment.service.service.IEquipmentBusinessService;
import com.lego.equipment.service.service.IEquipmentTypeService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.business.feign.BusinessClient;
import com.lego.framework.business.feign.CrudClient;
import com.lego.framework.business.model.entity.Business;
import com.lego.framework.equipment.model.entity.EquipmentBusiness;
import com.lego.framework.equipment.model.entity.EquipmentType;
import com.lego.framework.template.feign.TemplateFeignClient;
import com.lego.framework.template.model.entity.FormTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(value = "equipment", description = "设备管理")
@RestController
@RequestMapping("/equipment")
@Resource(value = "equipment", desc = "设备管理")
public class EquipmentController {


    @Autowired
    private TemplateFeignClient templateFeignClient;

    @Autowired
    private CrudClient crudClient;

    @Autowired
    private IEquipmentBusinessService equipmentBusinessService;

    @Autowired
    private BusinessClient businessClient;
    @Autowired
    private IEquipmentTypeService equipmentTypeService;


    /**
     * 通过id查询设备类型下面的业务
     *
     * @return
     */
    @ApiOperation(value = "查询设备类型下面的业务", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "设备ID", dataType = "String", required = true, paramType = "query"),
    })
    @Operation(value = "selectBusinessById", desc = "查询设备类型下面的业务")
    @RequestMapping(value = "/selectBusinessById", method = RequestMethod.GET)
    public RespVO selectBusinessById(@RequestParam(value = "id") Long id) {
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

        boolean b = respDataVORespVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE || respDataVORespVO.getInfo() == null && CollectionUtils.isEmpty(respDataVORespVO.getInfo().getList());

        if (b) {
            ExceptionBuilder.operateFailException("获取模板失败");
        }
        FormTemplate formTemplateGet = respDataVORespVO.getInfo().getList().get(0);
        return crudClient.queryBusinessData(formTemplateGet.getTemplateCode(), new ArrayList<>());
    }


    /**
     * 通过设备类型code查询该类型下面的设备信息
     *
     * @return
     */
    @ApiOperation(value = "通过设备类型code查询该类型下面的设备信息", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "query_equipment_by_code", desc = "通过设备类型code查询该类型下面的设备信息")
    @RequestMapping(value = "/query_equipment_by_code", method = RequestMethod.GET)
    public RespVO<RespDataVO<Map<String, Object>>> queryEquipmentByCode(@RequestParam(value = "code") String code) {
        if (StringUtils.isEmpty(code)) {
            return RespVOBuilder.failure("设备code不能为空");
        }
        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setCode(code);

        List<EquipmentType> list = equipmentTypeService.query(equipmentType);
        if (CollectionUtils.isEmpty(list)) {
            return RespVOBuilder.success();
        } else {
            EquipmentType equipmentType1 = list.get(0);
            RespVO<RespDataVO<FormTemplate>> data = templateFeignClient.findByDataType(equipmentType1.getType());
            if (data.getRetCode() != 1) {
                return RespVOBuilder.failure("查询失败");
            } else {
                FormTemplate formTemplateGet = data.getInfo().getList().get(0);
                return crudClient.queryBusinessData(formTemplateGet.getTemplateCode(), new ArrayList<>());
            }

        }

    }


    /**
     * 新增设备
     *
     * @return
     */
    @ApiOperation(value = "新增设备", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "save_equipment", desc = "新增设备")
    @RequestMapping(value = "/save_equipmentCost", method = RequestMethod.POST)
    public RespVO insert(@RequestParam(value = "equipmentTypeCode") String equipmentTypeCode, @RequestBody Map<String, Object> data) {

        if (StringUtils.isBlank(equipmentTypeCode)) {
            return RespVOBuilder.failure("设备类型code不能为空");
        }
        if (CollectionUtils.isEmpty(data)) {
            return RespVOBuilder.failure("设备数据不能为空");
        }
        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setCode(equipmentTypeCode);
        List<EquipmentType> equipmentTypeList = equipmentTypeService.query(equipmentType);
        if (CollectionUtils.isNotEmpty(equipmentTypeList) && equipmentTypeList.size() > 0) {
            Integer type = equipmentTypeList.get(0).getType();
            RespVO<RespDataVO<FormTemplate>> respVOTemplate = templateFeignClient.findByDataType(type);
            if (respVOTemplate.getRetCode() == 1) {
                List<FormTemplate> formTemplates = respVOTemplate.getInfo().getList();
                if (CollectionUtils.isNotEmpty(formTemplates) && formTemplates.size() > 0) {
                    equipmentTypeCode = formTemplates.get(0).getTemplateCode();
                    List<Map<String, Object>> datas = new ArrayList<>();
                    datas.add(data);
                    return crudClient.insertBusinessData(equipmentTypeCode, datas);
                } else {
                    return RespVOBuilder.failure("对应模板不存在，请确认上传模板类型是否正确");
                }
            } else {
                return RespVOBuilder.failure("增加设备失败");
            }
        } else {
            return RespVOBuilder.failure("增加设备失败");
        }

    }
}
