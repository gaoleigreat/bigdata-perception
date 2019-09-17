package com.lego.perception.business.mapper;

import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.business.model.entity.Business;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/17 18:22
 * @desc :
 */
@Repository
public interface BusinessMapper extends Mapper<Business> {


    Integer insertSelectiveIgnore(Business business);


    Integer insertSelective(Business business);


    Integer updateByPrimaryKeySelective(Business business);


    Integer updateByPrimaryKey(Business business);


    Integer batchInsert(List<Business> list);


    Integer batchUpdate(List<Business> list);

    Integer upsert(Business business);


    Integer upsertSelective(Business business);

    List<Business> query(Business business);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);


}
