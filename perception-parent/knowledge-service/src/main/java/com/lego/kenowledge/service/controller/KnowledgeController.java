package com.lego.kenowledge.service.controller;

import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.utils.UuidUtils;
import com.lego.kenowledge.service.model.entity.Answer;
import com.lego.kenowledge.service.model.entity.Ask;
import com.lego.kenowledge.service.model.entity.Knowledge;
import com.lego.kenowledge.service.model.vo.AskVo;
import com.lego.kenowledge.service.repository.KnowledgeRepository;
import com.lego.kenowledge.service.service.KnowledgeFileService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;

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

    @Autowired
    private KnowledgeFileService knowledgeFileService;


    @ApiOperation(value = "新增知识库提问信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tags", value = "标签", dataType = "String", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "classify", value = "知识库分类(1-专家经验库;2-厂家一般故障库;3-特殊装备故障;4-其他故障)", dataType = "long", required = true, paramType = "query"),
    })
    @Operation(value = "saveAsk", desc = "新增知识库信息")
    @RequestMapping(value = "/saveAsk", method = RequestMethod.POST)
    public RespVO saveAsk(@RequestBody Ask ask,
                          @RequestParam Integer classify,
                          @RequestParam MultipartFile[] files,
                          @RequestParam(required = false) List<String> tags) {
        Knowledge knowledge = new Knowledge();
        ask.setCreatedId(1L);
        ask.setCreatedDate(new Date());
        ask.setId(UuidUtils.generateShortUuid());
        knowledge.setAsk(ask);
        knowledge.setId(UuidUtils.generateShortUuid());
        knowledge.setClassify(classify);
        knowledge.setCreatedId(1L);
        knowledge.setCreatedDate(new Date());
        knowledge.setUpdatedDate(new Date());
        knowledge.setTags(tags);
        if (files != null && files.length > 0) {
            String batchNumber = knowledgeFileService.upLoadFile(files, "知识库提问附件", "KNOWLEDGE");
            if (batchNumber == null) {
                return RespVOBuilder.failure("附件上传失败");
            }
            ask.setAnnexNum(batchNumber);
        }
        Knowledge save = knowledgeRepository.save(knowledge);
        return RespVOBuilder.success();
    }


    @ApiOperation(value = "新增知识库回答信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "askId", value = "提问id", dataType = "String", required = true, paramType = "query"),
    })
    @Operation(value = "saveAnswer", desc = "新增知识库回答信息")
    @RequestMapping(value = "/saveAnswer", method = RequestMethod.POST)
    public RespVO saveAnswer(@RequestBody Answer answer,
                             @RequestParam MultipartFile[] files,
                             @RequestParam String askId) {
        Knowledge knowledge = knowledgeRepository.findKnowledgeByAskId(askId);
        if (knowledge == null) {
            return RespVOBuilder.failure("知识不存在");
        }
        List<Answer> answers = knowledge.getAnswers();
        if (answers == null) {
            answers = new ArrayList<>();
        }
        answer.setCreatedDate(new Date());
        answer.setCreatedId(2L);
        answer.setId(UuidUtils.generateShortUuid());
        answers.add(answer);
        knowledge.setAnswers(answers);
        if (files != null && files.length > 0) {
            String batchNumber = knowledgeFileService.upLoadFile(files, "知识库回答附件", "KNOWLEDGE");
            if (batchNumber == null) {
                return RespVOBuilder.failure("附件上传失败");
            }
            answer.setAnnexNum(batchNumber);
        }
        Knowledge save = knowledgeRepository.save(knowledge);
        return RespVOBuilder.success();
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
        // NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        Criteria criteria = new Criteria();
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, Sort.by(Sort.Direction.DESC, "ask.createdDate"));
        if (classify != null) {
            criteria.and("classify").is(classify);
        }
        if (!StringUtils.isEmpty(keyWords)) {
            criteria.and("ask.askBody").contains(keyWords);
        }
        if (!CollectionUtils.isEmpty(tags)) {
            criteria.and("tags").in(tags);
        }
        PagedResult pagedResult = new PagedResult();
        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
        criteriaQuery.setPageable(pageable);
        Page<Knowledge> knowledgePage = elasticsearchTemplate.queryForPage(criteriaQuery, Knowledge.class);
        List<Knowledge> knowledgeList = knowledgePage.getContent();
        List<AskVo> askVos = getAskVos(knowledgeList);
        pagedResult.setResultList(askVos);
        pagedResult.setPage(new com.framework.common.page.Page(pageIndex, pageSize, 0, knowledgePage.getNumberOfElements(), knowledgePage.getTotalPages()));
        return RespVOBuilder.success(pagedResult);
    }

    private List<AskVo> getAskVos(List<Knowledge> knowledgeList) {
        List<AskVo> askVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(knowledgeList)) {
            for (Knowledge know : knowledgeList) {
                Ask ask = know.getAsk();
                AskVo askVo = new AskVo();
                BeanUtils.copyProperties(ask, askVo);
                List<Answer> answers = know.getAnswers();
                askVo.setAnswerCount(answers != null ? answers.size() : 0);
                askVos.add(askVo);
            }
        }
        return askVos;
    }


    @ApiOperation(value = "知识详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "askId", value = "提问id", dataType = "String", required = true, paramType = "path"),
    })
    @Operation(value = "details", desc = "根据标签检索知识库内容")
    @RequestMapping(value = "/details/{askId}", method = RequestMethod.GET)
    public RespVO<Knowledge> details(@PathVariable String askId) {
        Knowledge knowledge = knowledgeRepository.findKnowledgeByAskId(askId);
        if (knowledge == null) {
            return RespVOBuilder.failure("知识不存在");
        }
        return RespVOBuilder.success(knowledge);
    }


    @ApiOperation(value = "我的提问", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "myAsk", desc = "我的提问")
    @RequestMapping(value = "/myAsk", method = RequestMethod.GET)
    public RespVO<RespDataVO<AskVo>> myAsk() {
        List<Knowledge> knowledgeList = knowledgeRepository.findAllByAskCreatedIdOrderByCreatedDateDesc(1L);
        List<AskVo> askVos = getAskVos(knowledgeList);
        return RespVOBuilder.success(askVos);
    }


    @ApiOperation(value = "根据标签检索知识库内容(根据标签检索知识库内容)", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @Operation(value = "searchTag", desc = "根据标签检索知识库内容")
    @RequestMapping(value = "/searchTag/{q}", method = RequestMethod.GET)
    public RespVO<RespDataVO<Knowledge>> searchTag(@PathVariable String q, String tag) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // queryStringQuery 全文检索
        //  matchQuery  multiMatchQuery 分词检索
        //  matchPhraseQuery  短语检索
        //  termQuery  termsQuery  精准匹配
        //  prefixQuery  前缀查询
        //  fuzzyQuery   分词模糊查询
        //  wildcardQuery  通配符查询
        // QueryBuilders.moreLikeThisQuery(new String[] {"fieldName"}).addLikeText("pipeidhua");//如果不指定filedName，则默认全部，常用在相似内容的推荐上
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


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public RespVO<RespDataVO<Knowledge>> findAll() {
        List<Knowledge> knowledgeList = new ArrayList<>();
        Iterable<Knowledge> all = knowledgeRepository.findAll();
        Iterator<Knowledge> knowledgeIterator = all.iterator();
        while (knowledgeIterator.hasNext()) {
            knowledgeList.add(knowledgeIterator.next());
        }
        return RespVOBuilder.success(knowledgeList);
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public RespVO delete(@PathVariable String id) {
        knowledgeRepository.deleteById(id);
        return RespVOBuilder.success();
    }


    @RequestMapping(value = "/drop", method = RequestMethod.GET)
    public RespVO drop() {
        boolean b = elasticsearchTemplate.deleteIndex(Knowledge.class);
        return RespVOBuilder.success(b);
    }


}
