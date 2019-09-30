package com.lego.kenowledge.service.controller;

import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.kenowledge.service.model.Knowledge;
import com.lego.kenowledge.service.repository.KnowledgeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/30 15:14
 * @desc :
 */
@Api(value = "knowledge", description = "设备类型管理")
@RestController
@RequestMapping("/knowledge")
@Resource(value = "knowledge", desc = "设备类型管理")
@Slf4j
public class KnowledgeController {

    @Autowired
    private KnowledgeRepository knowledgeRepository;


    @ApiOperation(value = "新增知识库信息", httpMethod = "POST")
    @ApiImplicitParams({

    })
    @Operation(value = "select_paged", desc = "查询设备类型信息")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public RespVO save(@RequestBody Knowledge knowledge){
        Knowledge save = knowledgeRepository.save(knowledge);
        return RespVOBuilder.success();
    }
}
