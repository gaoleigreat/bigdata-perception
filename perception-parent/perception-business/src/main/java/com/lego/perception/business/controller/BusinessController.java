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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@Resource(value = "business",desc = "业务服务")
public class BusinessController {

    @Autowired
    private IBusinessService iBusinessService;

    @ApiOperation(value = "创建业务表",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Operation(value = "create",desc = "创建业务表")
    public RespVO createBusiness(@RequestBody  FormTemplate formTemplate) {
        return iBusinessService.createBusinessTable(formTemplate);
    }


    @ApiOperation(value = "新增业务数据",httpMethod = "POST")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Operation(value = "insert",desc = "新增业务数据")
    public  RespVO insertBusinessData(FormTemplate formTemplate, Map<String, Object> data){
        return iBusinessService.insertBusinessData(formTemplate,data);
    }


}
