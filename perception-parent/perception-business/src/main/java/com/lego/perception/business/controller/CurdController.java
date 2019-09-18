package com.lego.perception.business.controller;

import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.template.feign.TemplateFeignClient;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.SearchParam;
import com.lego.perception.business.service.ICrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/3 18:02
 * @desc :
 */
@RestController
@RequestMapping("/curd")
@Api(value = "curd", description = "业务操作管理")
@Resource(value = "curd", desc = "业务操作管理")
public class CurdController {

    @Autowired
    private ICrudService mySqlBusinessService;

    @Autowired
    private TemplateFeignClient templateFeignClient;


    @ApiOperation(value = "创建业务表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateCode", value = "表单模板code", dataType = "String", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Operation(value = "create", desc = "创建业务表")
    public RespVO createBusiness(@RequestParam String templateCode) {
        RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateByCode(templateCode);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("获取模板信息失败");
        }
        FormTemplate formTemplate = respVO.getInfo();
        if (formTemplate == null) {
            return RespVOBuilder.failure("找不到对应模板信息");
        }
        return mySqlBusinessService.createBusinessTable(formTemplate);
    }


    @ApiOperation(value = "新增业务数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateCode", value = "表单模板code", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "fileId", value = "文件ID", dataType = "long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "data", value = "业务数据", dataType = "list", required = true, paramType = "body"),
    })
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Operation(value = "insert", desc = "新增业务数据")
    public RespVO insertBusinessData(@RequestParam String templateCode,
                                     @RequestBody List<Map<String, Object>> data) {
        RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateByCode(templateCode);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("获取模板信息失败");
        }
        FormTemplate formTemplate = respVO.getInfo();
        if (formTemplate == null) {
            return RespVOBuilder.failure("找不到对应模板信息");
        }
        return mySqlBusinessService.insertBusinessData(formTemplate, data);
    }


    @ApiOperation(value = "查询业务数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateCode", value = "表单模板code", dataType = "String", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @Operation(value = "query", desc = "查询业务数据")
    public RespVO<RespDataVO<Map>> queryBusinessData(@RequestParam String templateCode,
                                                     @RequestBody List<SearchParam> searchParams) {
        RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateByCode(templateCode);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("获取模板信息失败");
        }
        FormTemplate formTemplate = respVO.getInfo();
        if (formTemplate == null) {
            return RespVOBuilder.failure("找不到对应模板信息");
        }
        return mySqlBusinessService.queryBusinessData(formTemplate.getDescription(), searchParams);
    }


    @ApiOperation(value = "查询业务数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateCode", value = "表单模板code", dataType = "String", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/queryPaged/{pageSize}/{pageIndex}", method = RequestMethod.POST)
    @Operation(value = "query", desc = "查询业务数据")
    public RespVO<PagedResult<Map>> queryPaged(@RequestParam String templateCode,
                                               @RequestBody List<SearchParam> searchParams,
                                               @PathParam(value = "") Page page) {
        RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateByCode(templateCode);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("获取模板信息失败");
        }
        FormTemplate formTemplate = respVO.getInfo();
        if (formTemplate == null) {
            return RespVOBuilder.failure("找不到对应模板信息");
        }
        return mySqlBusinessService.queryBusinessDataPaged(formTemplate.getDescription(), searchParams,page);
    }


    @ApiOperation(value = "修改业务数据", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateCode", value = "表单模板code", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "data", value = "修改后的业务数据", dataType = "list", required = true, paramType = "body"),
    })
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @Operation(value = "update", desc = "修改业务数据")
    public RespVO updateBusinessData(@RequestParam String templateCode,
                                     @RequestBody Map<String, Object> data) {
        RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateByCode(templateCode);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("获取模板信息失败");
        }
        FormTemplate formTemplate = respVO.getInfo();
        if (formTemplate == null) {
            return RespVOBuilder.failure("找不到对应模板信息");
        }
        return mySqlBusinessService.updateBusinessData(formTemplate.getDescription(), data);
    }


    @ApiOperation(value = "删除业务数据", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateCode", value = "表单模板code", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "data", value = "删除条件", dataType = "map", required = true, paramType = "body"),
    })
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @Operation(value = "delete", desc = "删除业务数据")
    public RespVO delBusinessData(@RequestParam String templateCode,
                                  @RequestBody Map<String, Object> data) {
        RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateByCode(templateCode);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("获取模板信息失败");
        }
        FormTemplate formTemplate = respVO.getInfo();
        if (formTemplate == null) {
            return RespVOBuilder.failure("找不到对应模板信息");
        }
        return mySqlBusinessService.delBusinessData(formTemplate.getDescription(), data);
    }

}
