package com.lego.perception.template.controller;
import com.baomidou.mybatisplus.extension.api.R;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.template.model.entity.*;
import com.lego.framework.template.model.entity.Enumeration;
import com.lego.perception.template.init.EnumerationInit;
import com.lego.perception.template.service.IDataTemplateService;
import com.lego.perception.template.service.IEnumerationItemService;
import com.lego.perception.template.service.IEnumerationService;
import com.lego.perception.template.service.IFormTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.*;

@RestController
@RequestMapping("/enumeration/v1")
@Resource(value = "enumeration", desc = "枚举管理")
@Api(tags = "enumeration",description = "枚举管理")
@Slf4j
public class EnumerationController {

    @Autowired
    private IEnumerationService enumerationService;


    @Autowired
    private IEnumerationItemService iEnumerationItemService;

    @Autowired
    private EnumerationInit enumerationInit;

    @Autowired
    private IFormTemplateService iFormTemplateService;

    @Autowired
    private IDataTemplateService iDataTemplateService;

    @RequestMapping(value = "/findPagedList/{pageSize}/{curPage}", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "curPage", value = "请求页", dataType = "int", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", dataType = "int", required = true, paramType = "query"),
    })
    @ApiOperation("分页查询枚举")
    public RespVO<PagedResult<Enumeration>> findPagedList(@ModelAttribute Enumeration enumeration, @PathParam("") Page page) {

        return RespVOBuilder.success(enumerationService.findPagedList(enumeration, page));
    }




    @RequestMapping(value = "/findItemList", method = RequestMethod.POST)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询枚举值列表")
    public RespVO<RespDataVO<EnumerationItem>> findItemList(@RequestBody EnumerationItem enumerationItem) {

        return RespVOBuilder.success(iEnumerationItemService.findList(enumerationItem));
    }

    @RequestMapping(value = "/findList", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询枚举列表")
    public RespVO<EnumerationAttribute> findList(@ModelAttribute Enumeration enumeration) {
        EnumerationAttribute attributes = new EnumerationAttribute();
        List<Enumeration> list = enumerationService.findList(enumeration);
        attributes.setEnumerations(list);
        if (list != null && list.size() > 0) {
            attributes.setLastUpdateDate(list.get(0).getLastUpdateDate());
            attributes.setFlag(false);
        }
        return RespVOBuilder.success(attributes);
    }


    @Operation(value = "findEnumerationList", desc = "根据id查询枚举label列表")
    @ApiOperation("根据id查询枚举label列表")
    @RequestMapping(value = "/findEnumerationList", method = RequestMethod.POST)
    public RespVO<Map<Long, String>> findEnumerationList(@RequestBody Map<Long, Integer> map) {
        Map<Long, String> param = new HashMap<>();
        if (map.size() > 0) {
            for (Map.Entry<Long, Integer> m : map.entrySet()) {
                Long key = m.getKey();
                Integer value = m.getValue();
                EnumerationItem item = new EnumerationItem();
                item.setEnumId(key);
                item.setValue(value);
                EnumerationItem finItem = iEnumerationItemService.finItem(item);
                param.put(finItem.getEnumId(), finItem.getLabel());
            }
        }
        return RespVOBuilder.success(param);
    }


    @RequestMapping(value = "/findModifyInfo/{code}", method = RequestMethod.GET)
    @Operation(value = "findModifyInfo", desc = "查询模板更新信息")
    @ApiOperation("查询模板更新信息")
    public RespVO<TemplateModifyInfo> findModifyInfo(@PathVariable String code,
                                                @RequestParam Integer type) {
        TemplateModifyInfo templateModifyInfo = new TemplateModifyInfo();
        templateModifyInfo.setType(type);
        templateModifyInfo.setTemplateCode(code);
        if (type == 1) {
            // 表单模板
            FormTemplate formTemplate = new FormTemplate();
            formTemplate.setTemplateCode(code);
            FormTemplate template = iFormTemplateService.find(formTemplate);
            if (template != null) {
                templateModifyInfo.setLastUpdateDate(template.getLastUpdateDate());
            }
        } else if (type == 2) {
            // 数据模板
            DataTemplate dataTemplate = new DataTemplate();
            dataTemplate.setTemplateCode(code);
            DataTemplate template = iDataTemplateService.find(dataTemplate);
            if (template != null) {
                templateModifyInfo.setLastUpdateDate(template.getLastUpdateDate());
            }
        }
        return RespVOBuilder.success(templateModifyInfo);
    }


    @RequestMapping(value = "/findModifyList", method = RequestMethod.GET)
    @Operation(value = "findModifyList", desc = "查询枚举更新时间")
    @ApiOperation("查询枚举更新时间")
    public RespVO<EnumerationModifyInfo> findModifyList(@ModelAttribute Enumeration enumeration) {
        EnumerationModifyInfo enumerationModifyInfo = new EnumerationModifyInfo();
        List<Enumeration> enumerations = enumerationService.findList(enumeration);
        if (enumerations != null && enumerations.size() > 0) {
            enumerationModifyInfo.setLastUpdateDate(enumerations.get(0).getLastUpdateDate());
        }
        List<TemplateModifyInfo> modifyList = new ArrayList<>();
        List<FormTemplate> all = iFormTemplateService.findAll();
        if (all != null) {
            for (FormTemplate formTemplate : all) {
                TemplateModifyInfo modifyInfo = new TemplateModifyInfo();
                modifyInfo.setTemplateCode(formTemplate.getTemplateCode());
                modifyInfo.setLastUpdateDate(formTemplate.getLastUpdateDate());
                modifyInfo.setType(1);
                modifyList.add(modifyInfo);
            }
        }
        List<DataTemplate> all1 = iDataTemplateService.findAll();
        if (all1 != null) {
            for (DataTemplate dataTemplate : all1) {
                TemplateModifyInfo modifyInfo = new TemplateModifyInfo();
                modifyInfo.setTemplateCode(dataTemplate.getTemplateCode());
                modifyInfo.setLastUpdateDate(dataTemplate.getLastUpdateDate());
                modifyInfo.setType(2);
                modifyList.add(modifyInfo);
            }
        }
        enumerationModifyInfo.setTemplateModifyInfos(modifyList);
        return RespVOBuilder.success(enumerationModifyInfo);
    }


    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询枚举详情")
    public RespVO<Enumeration> find(@PathVariable Long id) {
        Enumeration enumeration = enumerationInit.getEnumerationIdMap().get(id);
        return RespVOBuilder.success(enumeration);
    }


    @RequestMapping(value = "/findItem/{enumId}/{value}", method = RequestMethod.GET)
    @Operation(value = "findItem", desc = "查询")
    @ApiOperation("查询枚举值")
    public RespVO<EnumerationItem> findItem(@PathVariable(value = "enumId") Long enumId, @PathVariable(value = "value") Integer value) {
        EnumerationItem item = new EnumerationItem();
        item.setEnumId(enumId);
        item.setValue(value);
        EnumerationItem enumerationItem = iEnumerationItemService.finItem(item);
        return RespVOBuilder.success(enumerationItem);
    }

    @Operation(value = "findItem", desc = "查询")
    @ApiOperation("查询枚举值")
    @RequestMapping(value = "/findItemLable/{enumId}/{label}", method = RequestMethod.GET)
    public RespVO<EnumerationItem> findItemLable(@PathVariable(value = "enumId") Long enumId,
                                            @PathVariable(value = "label") String label) {
        EnumerationItem item = new EnumerationItem();
        item.setEnumId(enumId);
        item.setLabel(label);
        EnumerationItem enumerationItem = iEnumerationItemService.finItem(item);
        return RespVOBuilder.success(enumerationItem);
    }


    @RequestMapping(value = "/findByCode/{code}", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询枚举详情")
    public RespVO<Enumeration> findByCode(@PathVariable String code) {
        Enumeration enumeration = enumerationInit.getEnumerationCodeMap().get(code);
        return RespVOBuilder.success(enumeration);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.PUT)
    @Operation(value = "insert", desc = "新增")
    @ApiOperation("新增")
    public RespVO insert(@RequestBody Enumeration enumeration) {

        return enumerationService.insert(enumeration);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Operation(value = "update", desc = "更新")
    @ApiOperation("更新")
    public RespVO update(@RequestBody Enumeration template) {

        return enumerationService.update(template);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @Operation(value = "delete", desc = "删除")
    @ApiOperation("删除")
    public RespVO delete(@PathVariable Long id) {

        return enumerationService.delete(id);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    @Operation(value = "update", desc = "更新")
    @ApiOperation("刷新")
    public RespVO refresh() {
        enumerationInit.initEnumMap();
        return RespVOBuilder.success();
    }


}
