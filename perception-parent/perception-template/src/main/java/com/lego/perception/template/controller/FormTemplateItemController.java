package com.lego.perception.template.controller;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.FormTemplateItem;
import com.lego.perception.template.service.IFormTemplateService;
import com.lego.perception.template.service.ITemplateItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/formTemplateItem/v1")
@Resource(value = "formTemplateItem", desc = "表单模板数据项")
@Api(tags = "formTemplateItem",description = "表单模板数据项")
@Slf4j
public class FormTemplateItemController {

    @Autowired
    private IFormTemplateService iFormTemplateService;

    @Autowired
    @Qualifier(value = "formTemplateItemServiceImpl")
    private ITemplateItemService formTemplateItemService;

    @RequestMapping(value = "/findList", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询模板项列表")
    public RespVO<RespDataVO<FormTemplateItem>> findPagedList(@ModelAttribute FormTemplateItem item) {

        return RespVOBuilder.success(formTemplateItemService.findList(item));
    }

    @RequestMapping(value = "/insertList", method = RequestMethod.PUT)
    @Operation(value = "insert", desc = "新增")
    @ApiOperation("新增模板项")
    public RespVO insertList(@RequestBody List<FormTemplateItem> items) {

        return formTemplateItemService.insertList(items);
    }

    @RequestMapping(value = "/updateList", method = RequestMethod.POST)
    @Operation(value = "update", desc = "更新")
    @ApiOperation("更新模板项")
    public RespVO updateList(@RequestBody List<FormTemplateItem> items) {
        if (items != null && items.size() > 0) {
            FormTemplateItem formTemplateItem = items.get(0);
            Long templateId = formTemplateItem.getTemplateId();
            FormTemplate formTemplate = iFormTemplateService.findById(templateId);
            formTemplate.setLastUpdateDate(new Date());
            iFormTemplateService.update(formTemplate);
        }
        return formTemplateItemService.updateList(items);
    }

    @RequestMapping(value = "/deleteList", method = RequestMethod.POST)
    @Operation(value = "delete", desc = "删除")
    @ApiOperation("删除模板项")
    public RespVO deleteList(@RequestBody List<Long> ids) {

        return formTemplateItemService.deleteList(ids);
    }


}
