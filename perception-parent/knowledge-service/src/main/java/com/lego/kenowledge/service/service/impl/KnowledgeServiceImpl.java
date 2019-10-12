package com.lego.kenowledge.service.service.impl;

import com.framework.common.consts.RespConsts;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.CurrentVo;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.context.RequestContext;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.base.utils.UuidUtils;
import com.lego.framework.file.feign.FileClient;
import com.lego.kenowledge.service.model.entity.Answer;
import com.lego.kenowledge.service.model.entity.Ask;
import com.lego.kenowledge.service.model.entity.Knowledge;
import com.lego.kenowledge.service.model.vo.AskVo;
import com.lego.kenowledge.service.repository.KnowledgeRepository;
import com.lego.kenowledge.service.service.IKnowledgeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/10/12 11:07
 * @desc :
 */
@Service
public class KnowledgeServiceImpl implements IKnowledgeService {

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private FileClient fileClient;


    @Override
    public RespVO saveAsk(Ask ask, Integer classify, MultipartFile[] files, List<String> tags) {
        CurrentVo current = RequestContext.getCurrent();
        Knowledge knowledge = new Knowledge();
        ask.setCreatedId(current.getUserId());
        ask.setCreatedDate(new Date());
        ask.setId(UuidUtils.generateShortUuid());
        knowledge.setAsk(ask);
        knowledge.setId(UuidUtils.generateShortUuid());
        knowledge.setClassify(classify);
        knowledge.setCreatedId(current.getUserId());
        knowledge.setCreatedDate(new Date());
        knowledge.setUpdatedDate(new Date());
        knowledge.setTags(tags);
        if (files != null && files.length > 0) {
            RespVO<String> respVO = fileClient.upLoadFile(files, "知识库提问附件", "KNOWLEDGE,");
            if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
                return RespVOBuilder.failure("附件上传失败");
            }
            String batchNumber = respVO.getInfo();
            if (batchNumber == null) {
                return RespVOBuilder.failure("附件上传失败");
            }
            ask.setAnnexNum(batchNumber);
        }
        Knowledge save = knowledgeRepository.save(knowledge);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO saveAnswer(Answer answer, MultipartFile[] files, String askId) {
        CurrentVo current = RequestContext.getCurrent();
        Knowledge knowledge = knowledgeRepository.findKnowledgeByAskId(askId);
        if (knowledge == null) {
            return RespVOBuilder.failure("知识不存在");
        }
        List<Answer> answers = knowledge.getAnswers();
        if (answers == null) {
            answers = new ArrayList<>();
        }
        answer.setCreatedDate(new Date());
        answer.setCreatedId(current.getUserId());
        answer.setId(UuidUtils.generateShortUuid());
        answers.add(answer);
        knowledge.setAnswers(answers);
        if (files != null && files.length > 0) {
            RespVO<String> respVO = fileClient.upLoadFile(files, "知识库提问附件", "KNOWLEDGE,");
            if (respVO.getRetCode() != RespConsts.SUCCESS_RESULT_CODE) {
                return RespVOBuilder.failure("附件上传失败");
            }
            String batchNumber = respVO.getInfo();
            if (batchNumber == null) {
                return RespVOBuilder.failure("附件上传失败");
            }
            answer.setAnnexNum(batchNumber);
        }
        Knowledge save = knowledgeRepository.save(knowledge);
        return RespVOBuilder.success();
    }

    @Override
    public PagedResult<AskVo> list(String keyWords, List<String> tags, Integer classify, Integer pageIndex, Integer pageSize) {
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
        return pagedResult;
    }

    @Override
    public Knowledge details(String askId) {
        Knowledge knowledge = knowledgeRepository.findKnowledgeByAskId(askId);
        if (knowledge == null) {
            ExceptionBuilder.operateFailException("知识不存在");
        }
        return knowledge;
    }

    @Override
    public List<AskVo> myAsk() {
        List<Knowledge> knowledgeList = knowledgeRepository.findAllByAskCreatedIdOrderByCreatedDateDesc(1L);
        return getAskVos(knowledgeList);
    }

    @Override
    public List<Knowledge> searchTag(String q, String tag) {
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
        return list;
    }

    @Override
    public List<Knowledge> all() {
        List<Knowledge> knowledgeList = new ArrayList<>();
        Iterable<Knowledge> all = knowledgeRepository.findAll();
        Iterator<Knowledge> knowledgeIterator = all.iterator();
        while (knowledgeIterator.hasNext()) {
            knowledgeList.add(knowledgeIterator.next());
        }
        return knowledgeList;
    }

    @Override
    public void delete(String id) {
        knowledgeRepository.deleteById(id);
    }

    @Override
    public boolean drop() {
        return elasticsearchTemplate.deleteIndex(Knowledge.class);
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

}
