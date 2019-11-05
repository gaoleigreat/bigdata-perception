package com.lego.perception.business.controller;

import com.framework.common.consts.RespConsts;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.template.feign.TemplateFeignClient;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.SearchParam;
import com.lego.perception.business.service.ICrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/crud")
@Api(value = "crud", description = "业务操作管理")
@Resource(value = "crud", desc = "业务操作管理")
@Validated
public class CrudController {

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
            @ApiImplicitParam(name = "data", value = "业务数据", dataType = "list", required = true, paramType = "body"),
    })
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Operation(value = "insert", desc = "新增业务数据")
    public RespVO insertBusinessData(@RequestParam String templateCode,
                                     @RequestBody List<Map<String, Object>> data) {
        if (CollectionUtils.isEmpty(data)) {
            return RespVOBuilder.failure("业务参数不能为空");
        }
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
    public RespVO<RespDataVO<Map<String, Object>>> queryBusinessData(@RequestParam String templateCode,
                                                                     @RequestBody(required = false) List<SearchParam> searchParams) {
        return mySqlBusinessService.queryBusinessData(templateCode, searchParams);
    }


    @ApiOperation(value = "查询业务数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateCode", value = "表单模板code", dataType = "String", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/queryCount", method = RequestMethod.POST)
    public RespVO<Integer> queryCount(@RequestParam("templateCode") String templateCode,
                                      @RequestBody List<SearchParam> searchParams) {
        RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateByCode(templateCode);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("获取模板信息失败");
        }
        FormTemplate formTemplate = respVO.getInfo();
        if (formTemplate == null) {
            return RespVOBuilder.failure("找不到对应模板信息");
        }
        Integer count = mySqlBusinessService.queryCount(formTemplate.getDescription(), searchParams);
        return RespVOBuilder.success(count);
    }


    @ApiOperation(value = "统计累积掘进量", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateCode", value = "表单模板code", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "1-年;2-季度;3-月份", dataType = "int", defaultValue = "3", paramType = "query"),
    })
    @RequestMapping(value = "/findSumExcavationByCondition", method = RequestMethod.POST)
    public RespVO<RespDataVO<Map<String, String>>> findSumExcavationByCondition(@RequestParam("templateCode") String templateCode,
                                                                                @RequestBody List<SearchParam> searchParams,
                                                                                @RequestParam(required = false, defaultValue = "3") Integer type) {
        RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateByCode(templateCode);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("获取模板信息失败");
        }
        FormTemplate formTemplate = respVO.getInfo();
        if (formTemplate == null) {
            return RespVOBuilder.failure("找不到对应模板信息");
        }
        List<Map<String, String>> mapList = mySqlBusinessService.findSumExcavationByCondition(formTemplate.getDescription(), searchParams, type);
        return RespVOBuilder.success(mapList);
    }


    @ApiOperation(value = "查询业务数据", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateCode", value = "表单模板code", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "equipmentCode", value = "设备编码", dataType = "String", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/queryByCode", method = RequestMethod.GET)
    @Operation(value = "queryByCode", desc = "查询业务数据")
    public RespVO<Map<String, Object>> queryBusinessDataByCode(@RequestParam String templateCode,
                                                               @RequestParam String equipmentCode) {
        RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateByCode(templateCode);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("获取模板信息失败");
        }
        FormTemplate formTemplate = respVO.getInfo();
        if (formTemplate == null) {
            return RespVOBuilder.failure("找不到对应模板信息");
        }
        return mySqlBusinessService.queryBusinessDataByCode(formTemplate.getDescription(), equipmentCode);
    }


    @ApiOperation(value = "查询业务数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateCode", value = "表单模板code", dataType = "String", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/queryPaged/{pageSize}/{pageIndex}", method = RequestMethod.POST)
    @Operation(value = "query", desc = "查询业务数据")
    public RespVO<PagedResult<Map<String, Object>>> queryPaged(@RequestParam String templateCode,
                                                               @RequestBody List<SearchParam> searchParams,
                                                               @PathParam(value = "") Page page) {
        return mySqlBusinessService.queryBusinessDataPaged(templateCode, searchParams, page);
    }


    @ApiOperation(value = "查询业务数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateCode", value = "表单模板code", dataType = "String", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/queryDataPaged/{pageSize}/{pageIndex}", method = RequestMethod.POST)
    @Operation(value = "query", desc = "查询业务数据")
    public RespVO<PagedResult<Map<String, Object>>> queryDataPaged(@RequestParam String templateCode,
                                                                   @RequestBody List<SearchParam> searchParams,
                                                                   @PathVariable(value = "pageSize") Integer pageSize,
                                                                   @PathVariable(value = "pageIndex") Integer pageIndex) {
        Page page = new Page();
        page.setPageSize(pageSize);
        page.setPageIndex(pageIndex);
        return mySqlBusinessService.queryBusinessDataPaged(templateCode, searchParams, page);
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


    @ApiOperation(value = "上传业务数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateCode", value = "表单模板code", dataType = "String", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @Operation(value = "upload", desc = "上传业务数据")
    public RespVO uploadBusinessData(@RequestParam String templateCode,
                                     @RequestParam MultipartFile file) {
        RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateByCode(templateCode);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            return RespVOBuilder.failure("获取模板信息失败");
        }
        FormTemplate formTemplate = respVO.getInfo();
        if (formTemplate == null) {
            return RespVOBuilder.failure("找不到对应模板信息");
        }
        return mySqlBusinessService.uploadBusinessData(formTemplate, file);
    }


    @ApiOperation(value = "下载业务数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateCode", value = "表单模板code", dataType = "String", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    @Operation(value = "download", desc = "下载业务数据")
    public void downloadBusinessData(@RequestParam String templateCode,
                                     @RequestBody List<SearchParam> searchParams,
                                     HttpServletResponse response) {
        RespVO<FormTemplate> respVO = templateFeignClient.findFormTemplateByCode(templateCode);
        if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
            ExceptionBuilder.operateFailException("获取模板信息失败");
        }
        FormTemplate formTemplate = respVO.getInfo();
        if (formTemplate == null) {
            ExceptionBuilder.operateFailException("找不到对应模板信息");
        }
        mySqlBusinessService.downloadBusinessData(formTemplate, searchParams, response);
    }


}
