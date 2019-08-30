package com.lego.perception.template.controller;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.template.model.entity.FormTemplateHistory;
import com.lego.perception.template.service.IHistoryTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/formTemplateHistory/v1")
@Resource(value = "formTemplateHistory", desc = "历史数据模板管理")
@Api(tags="formTemplateHistory",description = "历史数据模板管理")
@Slf4j
public class FormTemplateHistoryController {

    @Autowired
    @Qualifier(value = "formTemplateHistoryServiceImpl")
    private IHistoryTemplateService formTemplateHistoryService;

    @RequestMapping(value="/find/{id}", method=RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询模板详情")
    public RespVO find(@PathVariable String code, @RequestParam String tag){

        return RespVOBuilder.success(formTemplateHistoryService.find(code, tag));
    }

    @RequestMapping(value="/insert", method=RequestMethod.PUT)
    @Operation(value = "insert", desc = "新增")
    @ApiOperation("新增")
    public RespVO insert(@RequestBody FormTemplateHistory template ){

        return formTemplateHistoryService.insert(template);
    }
}
