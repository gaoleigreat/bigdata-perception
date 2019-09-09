package com.lego.perception.template.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.perception.template.service.IFormTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/formTemplate/v1")
@Resource(value = "formTemplate", desc = "表单模板管理")
@Api(tags = "formTemplate", description = "表单模板管理")
@Slf4j
public class FormTemplateController {

    @Autowired
    private IFormTemplateService formTemplateService;

    @RequestMapping(value = "/findPagedList/{pageSize}/{curPage}", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "curPage", value = "请求页", dataType = "int", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", dataType = "int", required = true, paramType = "query"),
    })
    @ApiOperation("分页查询模板列表")
    public RespVO<PagedResult<FormTemplate>> findPagedList(@ModelAttribute FormTemplate template, @PathParam("") Page page) {

        return RespVOBuilder.success(formTemplateService.findPagedList(template, page));
    }

    @RequestMapping(value = "/findList", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询模板列表")
    public RespVO<RespDataVO<FormTemplate>> findList(@ModelAttribute FormTemplate template) {

        return RespVOBuilder.success(formTemplateService.findList(template));
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询模板详情")
    public RespVO<FormTemplate> find(@PathVariable Long id) {
        FormTemplate template = new FormTemplate();
        template.setId(id);
        return RespVOBuilder.success(formTemplateService.find(template));
    }

    @RequestMapping(value = "/findByCode/{code}", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询模板详情")
    public RespVO<FormTemplate> find(@PathVariable String code) {
        FormTemplate template = new FormTemplate();
        template.setTemplateCode(code);
        FormTemplate formTemplate = formTemplateService.find(template);


        return RespVOBuilder.success(formTemplate);
    }


    @RequestMapping(value = "/insert", method = RequestMethod.PUT)
    @Operation(value = "insert", desc = "新增")
    @ApiOperation("新增")
    public RespVO insert(@RequestBody FormTemplate template,
                         @RequestParam Integer sourceType) {
        return formTemplateService.insert(template, sourceType);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Operation(value = "update", desc = "更新")
    @ApiOperation("更新")
    public RespVO update(@RequestBody FormTemplate template) {
        // TODO 查询关联表是否有对应业务

        return formTemplateService.update(template);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @Operation(value = "delete", desc = "删除")
    @ApiOperation("删除")
    public RespVO delete(@PathVariable Long id) {
        //TODO  查询关联表 是否存在业务

        return formTemplateService.delete(id);
    }


    @RequestMapping(value = "/queryFields/{code}", method = RequestMethod.GET)
    @Operation(value = "queryFields", desc = "查询模板字段")
    @ApiOperation("查询模板字段")
    public RespVO<List<String>> queryFields(@PathVariable String code) {
        return formTemplateService.queryFields(code);
    }

}
