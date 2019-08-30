package com.lego.perception.template.controller;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.template.model.entity.DataTemplate;
import com.lego.framework.template.model.entity.DataTemplateItem;
import com.lego.perception.template.service.IDataTemplateService;
import com.lego.perception.template.service.ITemplateItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dataTemplateItem/v1")
@Resource(value = "dataTemplateItem", desc = "数据模板数据项")
@Api(tags="数据模板数据项")
public class DataTemplateItemController {

    private static final Logger log = LoggerFactory.getLogger(DataTemplateItemController.class);

    @Autowired
    private IDataTemplateService iDataTemplateService;


    @Autowired
    @Qualifier(value = "dataTemplateItemService")
    private ITemplateItemService dataTemplateItemService;

    @RequestMapping(value="/findList", method=RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询模板项列表")
    public RespVO<RespDataVO<DataTemplateItem>> findPagedList(@ModelAttribute DataTemplateItem item){

        return RespVOBuilder.success(dataTemplateItemService.findList(item));
    }

    @RequestMapping(value="/findFieldTree", method=RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询模板项 字段树")
    public RespVO<Map<String, String>> findFieldTree(@ModelAttribute DataTemplateItem item){

        return RespVOBuilder.success(dataTemplateItemService.findFieldTree(item));
    }

    @RequestMapping(value="/findFieldNameMap", method=RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询模板项 字段树")
    public RespVO<Map<String, Object>> findFieldNameMap(@ModelAttribute DataTemplateItem item){

        return RespVOBuilder.success(dataTemplateItemService.findFieldNameMap(item));
    }

    @RequestMapping(value="/findFieldName", method=RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("查询模板项 字段树")
    public RespVO<Map<String, String>> findFieldName(@ModelAttribute DataTemplateItem item){

        return RespVOBuilder.success(dataTemplateItemService.findFieldName(item));
    }

    @RequestMapping(value="/insertList", method=RequestMethod.PUT)
    @Operation(value = "insert", desc = "新增")
    @ApiOperation("新增模板项")
    public RespVO insertList(@RequestBody List<DataTemplateItem> items){

        return dataTemplateItemService.insertList(items);
    }

    @RequestMapping(value="/updateList", method=RequestMethod.POST)
    @Operation(value = "update", desc = "更新")
    @ApiOperation("更新模板项")
    public RespVO updateList(@RequestBody List<DataTemplateItem> items){
        if(items!=null && items.size()>0){
            Long templateId = items.get(0).getTemplateId();
            DataTemplate dataTemplate=iDataTemplateService.findById(templateId);
            if(dataTemplate!=null){
                dataTemplate.setLastUpdateDate(new Date());
                iDataTemplateService.update(dataTemplate);
            }
        }
        return dataTemplateItemService.updateList(items);
    }

    @RequestMapping(value="/deleteList", method=RequestMethod.POST)
    @Operation(value = "delete", desc = "删除")
    @ApiOperation("删除模板项")
    public RespVO deleteList(@RequestBody List<Long> ids){

        return dataTemplateItemService.deleteList(ids);
    }


}
