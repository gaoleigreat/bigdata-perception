package com.lego.kenowledge.service.controller;

import com.framework.common.sdto.RespDataVO;
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
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    @Operation(value = "save", desc = "新增知识库信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RespVO save(@RequestBody Knowledge knowledge) {
        Knowledge save = knowledgeRepository.save(knowledge);
        return RespVOBuilder.success();
    }


    @ApiOperation(value = "检索知识库信息(检索知识库所有属性)", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "search", desc = "检索知识库信息")
    @RequestMapping(value = "/search/{q}", method = RequestMethod.GET)
    public RespVO<RespDataVO<Knowledge>> search(@PathVariable String q) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(q);
        Iterable<Knowledge> searchResult = knowledgeRepository.search(builder);
        Iterator<Knowledge> iterator = searchResult.iterator();
        List<Knowledge> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return RespVOBuilder.success(list);
    }


    @ApiOperation(value = "根据标签检索知识库内容(根据标签检索知识库内容)", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "searchTag", desc = "根据标签检索知识库内容")
    @RequestMapping(value = "/searchTag/{q}", method = RequestMethod.GET)
    public RespVO<RespDataVO<Knowledge>> searchTag(@PathVariable String q, String tag) {
        QueryBuilder whereQb = QueryBuilders.fuzzyQuery("askContent", q);
        BoolQueryBuilder qb = QueryBuilders.boolQuery().must(whereQb);
        if (tag != null) {
            QueryBuilder tagQb = QueryBuilders.multiMatchQuery("tags",tag);
            qb.must(tagQb);
        }
        Iterable<Knowledge> searchResult = knowledgeRepository.search(qb);
        Iterator<Knowledge> iterator = searchResult.iterator();
        List<Knowledge> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return RespVOBuilder.success(list);
    }


}
