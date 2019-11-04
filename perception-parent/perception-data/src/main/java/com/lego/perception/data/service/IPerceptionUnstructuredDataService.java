package com.lego.perception.data.service;

import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.lego.framework.data.model.entity.PerceptionUnstructuredData;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @description IPerceptionUnstructuredData Service层
 * @author ¸ßÀÚ
 * @since jdk1.8
 */
public interface IPerceptionUnstructuredDataService {



    /**
     * 创建PerceptionUnstructuredData
     *
     * @param perceptionUnstructuredData
     * @return
     */
    Integer insert(@NotNull(message = "添加失败，参数不能为空") PerceptionUnstructuredData perceptionUnstructuredData);


    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    Integer deleteByPrimaryKey(@NotNull(message = "删除失败，参数不能为空") Long id);

    /**
     * 修改PerceptionUnstructuredData
     *
     * @param perceptionUnstructuredData
     * @return
    */
    Integer updateByPrimaryKey(@NotNull(message = "添加失败，参数不能为空") PerceptionUnstructuredData perceptionUnstructuredData);


    /**
    * 根据主键查询
    *
    * @param id
    * @return
    */
    PerceptionUnstructuredData selectByPrimaryKey(@NotNull(message = "查询失败，参数不能为空") Long id);


    /**
     * 分页查询
     * @param
     * @param
     * @param perceptionUnstructuredData PerceptionUnstructuredData
     * @return IPage<PerceptionUnstructuredData>
     */
    PagedResult<PerceptionUnstructuredData> selectPaged(PerceptionUnstructuredData perceptionUnstructuredData, Page page);

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
     * 批量删除
     * @param list List<Long >
     * @return Integer
     */
    Integer deleteBatchIds(List<Long> list);

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
    Integer upsertSelective(PerceptionUnstructuredData perceptionUnstructuredData);

    /**
     * 条件查询
     * @param perceptionUnstructuredData PerceptionUnstructuredData
     * @return List<PerceptionUnstructuredData>
    */
    List<PerceptionUnstructuredData> query(PerceptionUnstructuredData perceptionUnstructuredData);

    /**
     * 查询总数
     * @return Integer
     */
    Long queryTotalCount();


}