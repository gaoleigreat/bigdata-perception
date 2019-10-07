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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

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
public class KnowledgeController {

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


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
    public RespVO<RespDataVO<Knowledge>> search(@PathVariable String q, @PageableDefault() Pageable pageable) {
        SearchQuery builder = new NativeSearchQueryBuilder()
                .withQuery(queryStringQuery(q))
                .withPageable(pageable)
                .build();
        List<Knowledge> knowles = elasticsearchTemplate.queryForList(builder, Knowledge.class);
        return RespVOBuilder.success(knowles);
    }


    @ApiOperation(value = "根据标签检索知识库内容(根据标签检索知识库内容)", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "searchTag", desc = "根据标签检索知识库内容")
    @RequestMapping(value = "/searchTag/{q}", method = RequestMethod.GET)
    public RespVO<RespDataVO<Knowledge>> searchTag(@PathVariable String q, String tag) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //  matchQuery  分词检索
        //  matchPhraseQuery  短语检索
        //  termsQuery   精准匹配
        queryBuilder.withQuery(matchPhraseQuery("ask.askBody", q).slop(2));
        if (tag != null) {
            queryBuilder.withQuery(termsQuery("tags", tag));
        }
        Iterable<Knowledge> searchResult = knowledgeRepository.search(queryBuilder.build());
        Iterator<Knowledge> iterator = searchResult.iterator();
        List<Knowledge> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return RespVOBuilder.success(list);
    }


}
