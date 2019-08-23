package com.lego.perception.log.repository;

import com.lego.framework.log.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2018/12/21
 **/
@Repository
public interface LogRepository extends MongoRepository<Log, String> {

    /**
     * @return
     */
    List<Log> findLogByUserIdAndDescOrderByTimeDesc(String userId, String desc);


}
