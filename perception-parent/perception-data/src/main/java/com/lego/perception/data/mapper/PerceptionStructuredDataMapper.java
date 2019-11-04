package com.lego.perception.data.mapper;

import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.data.model.entity.PerceptionStructuredData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @description PerceptionStructuredDataMapper
 * @author¸ßÀÚ
 * @since jdk1.8
 */
@Repository
public interface PerceptionStructuredDataMapper extends Mapper<PerceptionStructuredData> {

    /**
    * 批量插入
    * @param list List<PerceptionStructuredData
    * @return Integer
    */
    Integer batchInsert(List<PerceptionStructuredData> list);

    /**
     * 批量更新
     * @param list List<PerceptionStructuredData>
     * @return Integer
     */
    Integer batchUpdate(List<PerceptionStructuredData> list);

    /**
     * 存在即更新
     * @param perceptionStructuredData PerceptionStructuredData
     * @return Integer
     */
    Integer upsert(@Param("perceptionStructuredData") PerceptionStructuredData perceptionStructuredData);

    /**
     * 存在即更新，可选择具体属性
     * @param perceptionStructuredData PerceptionStructuredData
     * @return Integer
     */
    Integer upsertSelective(@Param("perceptionStructuredData") PerceptionStructuredData perceptionStructuredData);

    /**
     * 条件查询
     * @param perceptionStructuredData PerceptionStructuredData
     * @return List<PerceptionStructuredData>
    */
    List<PerceptionStructuredData> query(@Param("perceptionStructuredData") PerceptionStructuredData perceptionStructuredData);

    /**
     * 查询总数
     * @return Integer
     */
    Long queryTotalCount();


}