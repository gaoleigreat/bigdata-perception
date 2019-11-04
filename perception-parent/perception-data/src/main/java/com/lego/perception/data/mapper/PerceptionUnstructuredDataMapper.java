package com.lego.perception.data.mapper;

import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.data.model.entity.PerceptionUnstructuredData;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @description PerceptionUnstructuredDataMapper
 * @author¸ßÀÚ
 * @since jdk1.8
 */
public interface PerceptionUnstructuredDataMapper extends Mapper<PerceptionUnstructuredData> {

    /**
    * 批量插入
    * @param list List<PerceptionUnstructuredData
    * @return Integer
    */
    Integer batchInsert(List<PerceptionUnstructuredData> list);

    /**
     * 批量更新
     * @param list List<PerceptionUnstructuredData>
     * @return Integer
     */
    Integer batchUpdate(List<PerceptionUnstructuredData> list);

    /**
     * 存在即更新
     * @param perceptionUnstructuredData PerceptionUnstructuredData
     * @return Integer
     */
    Integer upsert(@Param("perceptionUnstructuredData") PerceptionUnstructuredData perceptionUnstructuredData);

    /**
     * 存在即更新，可选择具体属性
     * @param perceptionUnstructuredData PerceptionUnstructuredData
     * @return Integer
     */
    Integer upsertSelective(@Param("perceptionUnstructuredData") PerceptionUnstructuredData perceptionUnstructuredData);

    /**
     * 条件查询
     * @param perceptionUnstructuredData PerceptionUnstructuredData
     * @return List<PerceptionUnstructuredData>
    */
    List<PerceptionUnstructuredData> query(@Param("perceptionUnstructuredData") PerceptionUnstructuredData perceptionUnstructuredData);

    /**
     * 查询总数
     * @return Integer
     */
    Long queryTotalCount();


}