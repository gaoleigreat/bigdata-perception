package com.lego.perception.business.controller;

import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.perception.business.service.IBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/business")
@Api(value = "business", description = "业务服务")
@Resource(value = "business", desc = "业务服务")
public class BusinessController {

    @Autowired
    @Qualifier(value = "mySqlBusinessServiceImpl")
    private IBusinessService mySqlBusinessService;

    @Autowired
    @Qualifier(value = "mongoBusinessServiceImpl")
    private IBusinessService mongoBusinessService;

    @ApiOperation(value = "创建业务表", httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Operation(value = "create", desc = "创建业务表")
    public RespVO createBusiness(@RequestBody FormTemplate formTemplate,
                                 Integer sourceType) {
        if (sourceType != null && sourceType == 0) {
            return mySqlBusinessService.createBusinessTable(formTemplate);
        } else {
            return mongoBusinessService.createBusinessTable(formTemplate);
        }
    }


    @ApiOperation(value = "新增业务数据", httpMethod = "POST")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Operation(value = "insert", desc = "新增业务数据")
    public RespVO insertBusinessData(FormTemplate formTemplate,
                                     Long fileId,
                                     List<Map<String, Object>> data,
                                     Integer sourceType) {
        if (sourceType == 0) {
            return mySqlBusinessService.insertBusinessData(formTemplate, data, fileId);
        } else if (sourceType == 1) {
            return mongoBusinessService.insertBusinessData(formTemplate, data, fileId);
        }
        return RespVOBuilder.failure();
    }


    @ApiOperation(value = "查询业务数据", httpMethod = "POST")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @Operation(value = "query", desc = "查询业务数据")
    public RespVO queryBusinessData(@RequestBody FormTemplate formTemplate,
                                    @RequestParam Map<String, Object> data,
                                    @RequestParam Integer sourceType) {
        String tableName = formTemplate.getDescription();
        if (sourceType == 0) {
            return mySqlBusinessService.queryBusinessData(tableName, data);
        } else if (sourceType == 1) {
            return mongoBusinessService.queryBusinessData(tableName, data);
        }
        return RespVOBuilder.failure();
    }


    @ApiOperation(value = "修改业务数据", httpMethod = "PUT")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @Operation(value = "update", desc = "修改业务数据")
    public RespVO updateBusinessData(@RequestBody FormTemplate formTemplate,
                                     @RequestParam Map<String, Object> data,
                                     @RequestParam Integer sourceType) {
        String tableName = formTemplate.getDescription();
        if (sourceType == 0) {
            return mySqlBusinessService.updateBusinessData(tableName, data);
        } else if (sourceType == 1) {
            return mongoBusinessService.updateBusinessData(tableName, data);
        }
        return RespVOBuilder.failure();
    }


    @ApiOperation(value = "删除业务数据", httpMethod = "DELETE")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @Operation(value = "delete", desc = "删除业务数据")
    public RespVO delBusinessData(@RequestBody FormTemplate formTemplate,
                                  @RequestParam Map<String, Object> data,
                                  @RequestParam Integer sourceType) {
        String tableName = formTemplate.getDescription();
        if (sourceType == 0) {
            return mySqlBusinessService.delBusinessData(tableName, data);
        } else if (sourceType == 1) {
            return mongoBusinessService.delBusinessData(tableName, data);
        }
        return RespVOBuilder.failure();
    }

}
