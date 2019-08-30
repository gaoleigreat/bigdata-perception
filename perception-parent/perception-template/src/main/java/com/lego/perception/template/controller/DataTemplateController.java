package com.lego.perception.template.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.template.model.entity.DataTemplate;
import com.lego.framework.template.model.entity.DataTemplateItem;
import com.lego.perception.template.service.IDataTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/dataTemplate/v1")
@Resource(value = "dataTemplate", desc = "数据模板管理")
@Api(tags = "dataTemplate",description ="数据模板管理" )
@Slf4j
public class DataTemplateController {

    @Autowired
    private IDataTemplateService dataTemplateService;

    @RequestMapping(value = "/findPagedList/{pageSize}/{curPage}", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "curPage", value = "请求页", dataType = "int", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", dataType = "int", required = true, paramType = "query"),
    })
    @ApiOperation("分页查询模板列表")
    public RespVO<PagedResult<DataTemplate>> findPagedList(@ModelAttribute DataTemplate template, @PathParam("") Page page) {

        return RespVOBuilder.success(dataTemplateService.findPagedList(template, page));
    }

    @RequestMapping(value = "/findList", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询模板列表")
    public RespVO<RespDataVO<DataTemplate>> findList(@ModelAttribute DataTemplate template) {

        return RespVOBuilder.success(dataTemplateService.findList(template));
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询模板详情")
    public RespVO<DataTemplate> find(@PathVariable Long id) {
        DataTemplate template = new DataTemplate();
        template.setId(id);
        return RespVOBuilder.success(dataTemplateService.find(template));
    }

    @RequestMapping(value = "/findByCode/{code}", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询模板详情")
    public RespVO<DataTemplate> findByCode(@PathVariable String code,
                                           @RequestParam(required = false, defaultValue = "0") Integer type) {
        DataTemplate template = new DataTemplate();
        template.setTemplateCode(code);
        DataTemplate dataTemplate = dataTemplateService.find(template);
        if (type == 1) {
            if (dataTemplate != null) {
                List<DataTemplateItem> dataTemplateItems = new ArrayList<>();
                List<DataTemplateItem> items = dataTemplate.getItems();
                if (items != null) {
                    for (DataTemplateItem item : items) {
                        String field = item.getField();
                        if (!field.equals("k")) {
                            dataTemplateItems.add(item);
                        }
                    }
                }
                dataTemplate.setItems(dataTemplateItems);
            }
        }

        return RespVOBuilder.success(dataTemplate);
    }


    @RequestMapping(value = "/insert", method = RequestMethod.PUT)
    @Operation(value = "insert", desc = "新增")
    @ApiOperation("新增")
    public RespVO insert(@RequestBody DataTemplate template) {

        return dataTemplateService.insert(template);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Operation(value = "update", desc = "更新")
    @ApiOperation("更新")
    public RespVO update(@RequestBody DataTemplate template) {

        return dataTemplateService.update(template);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @Operation(value = "delete", desc = "删除")
    @ApiOperation("删除")
    public RespVO delete(@PathVariable Long id) {

        return dataTemplateService.delete(id);
    }
}
