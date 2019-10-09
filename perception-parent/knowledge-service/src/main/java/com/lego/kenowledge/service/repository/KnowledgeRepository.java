package com.lego.kenowledge.service.repository;

import com.lego.kenowledge.service.model.entity.Knowledge;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/30 15:09
 * @desc :
 */
@Repository
public interface KnowledgeRepository extends ElasticsearchRepository<Knowledge, String> {


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Knowledge findKnowledgeById(String id);


    /**
     * 通过提问id获取提问信息
     *
     * @param askId
     * @return
     */
    Knowledge findKnowledgeByAskId(String askId);


    /**
     *
     * @param askBy
     * @return
     */
    List<Knowledge> findAllByAskCreatedIdOrderByCreatedDateDesc(Long askBy);


    /**
     * 通过回复id获取知识库信息
     *
     * @param answerId
     * @return
     */
    Knowledge findKnowledgeByAnswersId(String answerId);

}
