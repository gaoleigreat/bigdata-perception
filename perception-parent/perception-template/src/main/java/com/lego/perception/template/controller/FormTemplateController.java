package com.lego.perception.template.controller;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.excel.utils.ExcelUtil;
import com.framework.mybatis.utils.TableUtils;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.FormTemplateItem;
import com.lego.perception.template.service.IFormTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/formTemplate/v1")
@Resource(value = "formTemplate", desc = "表单模板管理")
@Api(tags = "formTemplate", description = "表单模板管理")
@Slf4j
public class FormTemplateController {

    @Autowired
    private IFormTemplateService formTemplateService;

    @RequestMapping(value = "/findPagedList/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "请求页", dataType = "int", required = true, paramType = "query"),
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
    public RespVO insert(@RequestBody FormTemplate template) {
        return formTemplateService.insert(template);
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


    @RequestMapping(value = "/downloadTemplate/{code}", method = RequestMethod.GET)
    @Operation(value = "downloadTemplate", desc = "下载模板")
    @ApiOperation("下载模板")
    public void downloadTemplate(@PathVariable String code,
                                 HttpServletResponse response) {
        FormTemplate template = new FormTemplate();
        template.setTemplateCode(code);
        FormTemplate formTemplate = formTemplateService.find(template);
        String templateName = formTemplate.getTemplateName();
        List<List<String>> list = new ArrayList<>();
        List<String> fields = new ArrayList<>();
        List<FormTemplateItem> itemList = formTemplate.getItems();
        if (CollectionUtils.isEmpty(itemList)) {
            ExceptionBuilder.operateFailException("模板没有字段");
        }
        for (FormTemplateItem templateItem : itemList) {
            Integer category = templateItem.getCategory();
            boolean columnType = TableUtils.isColumnType(category);
            if (!columnType) {
                continue;
            }
            String title = templateItem.getTitle();
            fields.add(title);
        }
        list.add(fields);
        try {
            ExcelUtil.excelWriter(list, "sheet1", templateName, 0, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/findByDataType/{dataType}", method = RequestMethod.GET)
    @Operation(value = "find", desc = "根据dataType查询")
    @ApiOperation("根据dataType查询")
    public RespVO<RespDataVO<FormTemplate>> findfindByDataType(@PathVariable Integer dataType) {
        FormTemplate template = new FormTemplate();
        template.setDataType(dataType);
        List<FormTemplate> formTemplates = formTemplateService.findList(template);

        return RespVOBuilder.success(formTemplates);
    }
}
