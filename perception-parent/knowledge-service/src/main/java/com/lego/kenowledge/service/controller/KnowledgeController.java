package com.lego.kenowledge.service.controller;

import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.kenowledge.service.model.entity.Answer;
import com.lego.kenowledge.service.model.entity.Ask;
import com.lego.kenowledge.service.model.entity.Knowledge;
import com.lego.kenowledge.service.model.vo.AskVo;
import com.lego.kenowledge.service.model.vo.KnowledgeVo;
import com.lego.kenowledge.service.service.IKnowledgeService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/30 15:14
 * @desc :
 */
@Api(value = "knowledge", description = "知识库管理")
@RestController
@RequestMapping("/knowledge")
@Resource(value = "knowledge", desc = "知识库管理")
@Slf4j
@Validated
public class KnowledgeController {

    @Autowired
    private IKnowledgeService iKnowledgeService;


    @ApiOperation(value = "新增知识库提问信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tags", value = "标签", dataType = "String", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "classify", value = "知识库分类(1-专家经验库;2-厂家一般故障库;3-特殊装备故障;4-其他故障)", dataType = "int", required = true, paramType = "query"),
            @ApiImplicitParam(name = "askBody", value = "提问内容", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "askDesc", value = "提问描述", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tags", value = "标签", dataType = "String", allowMultiple = true, paramType = "query"),

    })
    @Operation(value = "saveAsk", desc = "新增知识库信息")
    @RequestMapping(value = "/saveAsk", method = RequestMethod.POST)
    public RespVO saveAsk(@RequestParam String askBody,
                          @RequestParam(required = false) String askDesc,
                          @RequestParam Integer classify,
                          @RequestParam(required = false) MultipartFile[] files,
                          @RequestParam(required = false) List<String> tags) {
        Ask ask = new Ask();
        ask.setAskBody(askBody);
        ask.setAskDesc(askDesc);
        return iKnowledgeService.saveAsk(ask, classify, files, tags);
    }


    @ApiOperation(value = "新增知识库回答信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "answerBody", value = "回复内容", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "askId", value = "提问id", dataType = "String", required = true, paramType = "query"),
    })
    @Operation(value = "saveAnswer", desc = "新增知识库回答信息")
    @RequestMapping(value = "/saveAnswer", method = RequestMethod.POST)
    public RespVO saveAnswer(@RequestParam String answerBody,
                             @RequestParam(required = false) MultipartFile[] files,
                             @RequestParam String askId) {
        Answer answer = new Answer();
        answer.setAnswerBody(answerBody);
        return iKnowledgeService.saveAnswer(answer, files, askId);
    }


    @ApiOperation(value = "知识提问列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyWords", value = "提问内容搜索词", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tags", value = "标签", dataType = "String", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "classify", value = "知识库分类(1-专家经验库;2-厂家一般故障库;3-特殊装备故障;4-其他故障)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "当前页数", dataType = "int", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", dataType = "int", defaultValue = "10", paramType = "query")

    })
    @Operation(value = "list", desc = "知识提问列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RespVO<PagedResult<AskVo>> search(@RequestParam(required = false) String keyWords,
                                             @RequestParam(required = false) List<String> tags,
                                             @RequestParam(required = false) Integer classify,
                                             @RequestParam Integer pageIndex,
                                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PagedResult<AskVo> pagedResult = iKnowledgeService.list(keyWords, tags, classify, pageIndex, pageSize);
        return RespVOBuilder.success(pagedResult);
    }


    @ApiOperation(value = "知识详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "askId", value = "提问id", dataType = "String", required = true, paramType = "path"),
    })
    @Operation(value = "details", desc = "根据标签检索知识库内容")
    @RequestMapping(value = "/details/{askId}", method = RequestMethod.GET)
    public RespVO<KnowledgeVo> details(@PathVariable String askId) {
        KnowledgeVo knowledge = iKnowledgeService.details(askId);
        return RespVOBuilder.success(knowledge);
    }


    @ApiOperation(value = "我的提问", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "myAsk", desc = "我的提问")
    @RequestMapping(value = "/myAsk", method = RequestMethod.GET)
    public RespVO<RespDataVO<AskVo>> myAsk() {
        List<AskVo> askVos = iKnowledgeService.myAsk();
        return RespVOBuilder.success(askVos);
    }


    @ApiOperation(value = "根据标签检索知识库内容(根据标签检索知识库内容)", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "searchTag", desc = "根据标签检索知识库内容")
    @RequestMapping(value = "/searchTag/{q}", method = RequestMethod.GET)
    public RespVO<RespDataVO<KnowledgeVo>> searchTag(@PathVariable String q, String tag) {
        List<KnowledgeVo> list = iKnowledgeService.searchTag(q, tag);
        return RespVOBuilder.success(list);
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public RespVO<RespDataVO<KnowledgeVo>> findAll() {
        List<KnowledgeVo> knowledgeList = iKnowledgeService.all();
        return RespVOBuilder.success(knowledgeList);
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public RespVO delete(@PathVariable String id) {
        iKnowledgeService.delete(id);
        return RespVOBuilder.success();
    }


    @RequestMapping(value = "/drop", method = RequestMethod.GET)
    public RespVO drop() {
        boolean b = iKnowledgeService.drop();
        if (b) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }


}
