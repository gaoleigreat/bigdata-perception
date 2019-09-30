package com.lego.kenowledge.service.repository;
import com.lego.kenowledge.service.model.Knowledge;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/30 15:09
 * @desc :
 */
@Repository
public interface KnowledgeRepository extends ElasticsearchRepository<Knowledge, Long> {

}
