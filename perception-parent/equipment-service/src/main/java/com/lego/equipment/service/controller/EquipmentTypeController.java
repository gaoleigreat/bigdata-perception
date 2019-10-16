package com.lego.equipment.service.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.equipment.service.service.IEquipmentTypeService;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.business.feign.CrudClient;
import com.lego.framework.equipment.model.entity.EquipmentType;
import com.lego.framework.template.feign.TemplateFeignClient;
import com.lego.framework.template.model.entity.FormTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-24 07:43:07
 * @since jdk 1.8
 */
@Api(value = "equipmentType", description = "设备类型管理")
@RestController
@RequestMapping("/equipmentType")
@Resource(value = "equipmentType", desc = "设备类型管理")
@Slf4j
@Validated
public class EquipmentTypeController {


    @Autowired
    private IEquipmentTypeService iEquipmentTypeService;

    @Autowired
    private TemplateFeignClient templateFeignClient;

    @Autowired
    private CrudClient crudClient;

    /**
     * 分页查询数据
     */
    @ApiOperation(value = "查询设备类型信息", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "select_paged", desc = "查询设备类型信息")
    @RequestMapping(value = "/select_paged/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public RespVO<PagedResult<EquipmentType>> selectPaged(@PathParam(value = "") Page page,
                                                          @ModelAttribute EquipmentType equipmentType) {
        PagedResult<EquipmentType> pagedResult = iEquipmentTypeService.selectPaged(equipmentType, page);
        List<EquipmentType> resultList = pagedResult.getResultList();
        resultList.forEach(equipmentType1 -> {
            RespVO<FormTemplate> respDataVORespVO = templateFeignClient.findByDataType(Integer.valueOf(equipmentType1.getType()));

            if (respDataVORespVO == null) {
                ExceptionBuilder.operateFailException("模板服务不可用");
            }
            if (respDataVORespVO.getRetCode() != 1) {
                ExceptionBuilder.operateFailException("获取模板失败");
            }
            FormTemplate formTemplateGet = respDataVORespVO.getInfo();
            if (formTemplateGet == null) {
                ExceptionBuilder.operateFailException("没有对应的表单模板");
            }
            equipmentType1.setTemplateCode(formTemplateGet.getTemplateCode());
        });
        pagedResult.setResultList(resultList);
        return RespVOBuilder.success(pagedResult);
    }

    /**
     * 通过id查询
     *
     * @return
     */
    @ApiOperation(value = "查询设备类型信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "设备类型信息id", dataType = "long", required = true, paramType = "query"),
    })
    @Operation(value = "select_by_id", desc = "查询设备类型信息")
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    public RespVO<EquipmentType> selectByPrimaryKey(@RequestParam(value = "id") Long id) {
        EquipmentType po = iEquipmentTypeService.selectByPrimaryKey(id);

        RespVO<FormTemplate> respDataVORespVO = templateFeignClient.findByDataType(Integer.valueOf(po.getType()));

        if (respDataVORespVO == null) {
            ExceptionBuilder.operateFailException("模板服务不可用");
        }
        if (respDataVORespVO.getRetCode() != 1) {
            ExceptionBuilder.operateFailException("获取模板失败");
        }
        FormTemplate formTemplateGet = respDataVORespVO.getInfo();
        if (formTemplateGet == null) {
            ExceptionBuilder.operateFailException("没有对应的表单模板");
        }
        po.setTemplateCode(formTemplateGet.getTemplateCode());
        return RespVOBuilder.success(po);
    }

    /**
     * 通过ID删除
     *
     * @return
     */
    @ApiOperation(value = "删除设备类型信息", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "设备类型信息id", dataType = "long", required = true, paramType = "query"),
    })
    @Operation(value = "delete_by_id", desc = "删除设备信息")
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.DELETE)
    public RespVO<Integer> deleteByPrimaryKey(Long id) {
        Integer num = iEquipmentTypeService.deleteByPrimaryKey(id);
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
    @ApiOperation(value = "新增设备类型信息", httpMethod = "POST")
    @ApiImplicitParams({
    })
    @Operation(value = "save", desc = "新增设备类型信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Transactional(rollbackFor = RuntimeException.class)
    public RespVO<Integer> insert(@RequestBody EquipmentType equipmentType) {
        if (equipmentType == null) {
            return RespVOBuilder.failure("模板类型不能为空");
        }


        String templateCode = equipmentType.getTemplateCode();
        if (StringUtils.isEmpty(templateCode)) {
            return RespVOBuilder.failure("模板code不能为空");
        }
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

        Integer dataType = formTemplate.getDataType();

        if (dataType == null) {
            return RespVOBuilder.failure("模板对应数据有误，请联系运维人员进行处理");
        }
        EquipmentType equipmentTypeSelect = new EquipmentType();
        equipmentTypeSelect.setType(dataType);
        List<EquipmentType> equipmentTypes = iEquipmentTypeService.query(equipmentTypeSelect);
        if (CollectionUtils.isNotEmpty(equipmentTypes)) {
            return RespVOBuilder.failure("模板对应设备已经存在");
        }

        equipmentType.setType(dataType);
        Integer num = iEquipmentTypeService.insertSelective(equipmentType);


        if (num > 0) {
            RespVO respVO = crudClient.createBusiness(templateCode);
            if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
                log.error("设备表创建失败:{}", equipmentType);
                ExceptionBuilder.operateFailException("创建设备表失败:" + respVO.getMsg());
            }
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }

    /**
     * 修改数据
     *
     * @return
     */
    @ApiOperation(value = "修改设备类型信息", httpMethod = "PUT")
    @ApiImplicitParams({

    })
    @Operation(value = "update", desc = "修改设备类型信息")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public RespVO<Integer> updateByPrimaryKeySelective(@RequestBody EquipmentType equipmentType) {
        Integer num = iEquipmentTypeService.updateByPrimaryKeySelective(equipmentType);
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
    @ApiOperation(value = "查询设备类型信息", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "query_list", desc = "查询设备类型信息")
    @RequestMapping(value = "/query_list", method = RequestMethod.GET)
    public RespVO<RespDataVO<EquipmentType>> queryByCondition(@ModelAttribute EquipmentType equipmentType) {
        List<EquipmentType> list = iEquipmentTypeService.query(equipmentType);
        return RespVOBuilder.success(list);
    }

}
