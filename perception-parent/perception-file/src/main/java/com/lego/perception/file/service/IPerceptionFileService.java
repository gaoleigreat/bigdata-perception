package com.lego.perception.file.service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.lego.framework.file.model.PerceptionFile;
import org.apache.ibatis.annotations.Param;
import javax.validation.constraints.NotNull;


/**
 * @description IPerceptionFile Service层
 * @author ¸ßÀÚ
 * @since jdk1.8
 */
public interface IPerceptionFileService {



    /**
     * 创建PerceptionFile
     *
     * @param perceptionFile
     * @return
     */
    Integer insert(@NotNull(message = "添加失败，参数不能为空") PerceptionFile perceptionFile);


    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    Integer deleteByPrimaryKey(@NotNull(message = "删除失败，参数不能为空") Long id);

    /**
     * 修改PerceptionFile
     *
     * @param perceptionFile
     * @return
    */
    Integer updateByPrimaryKey(@NotNull(message = "添加失败，参数不能为空") PerceptionFile perceptionFile);


    /**
    * 根据主键查询
    *
    * @param id
    * @return
    */
    PerceptionFile selectByPrimaryKey(@NotNull(message = "查询失败，参数不能为空") Long id);


    /**
     * 分页查询
     * @param
     * @param
     * @param perceptionFile PerceptionFile
     * @return IPage<PerceptionFile>
     */
    PagedResult<PerceptionFile> selectPaged(PerceptionFile perceptionFile, Page page);

    /**
    * 批量插入
    * @param list List<PerceptionFile
    * @return Integer
    */
    Integer batchInsert(List<PerceptionFile> list);

    /**
     * 批量更新
     * @param list List<PerceptionFile>
     * @return Integer
     */
    Integer batchUpdate(List<PerceptionFile> list);

    /**
     * 批量删除
     * @param list List<Long >
     * @return Integer
     */
    Integer deleteBatchIds(List<Long> list);

    /**
     * 存在即更新
     * @param perceptionFile PerceptionFile
     * @return Integer
     */
    Integer upsert(@Param("perceptionFile") PerceptionFile perceptionFile);

    /**
     * 存在即更新，可选择具体属性
     * @param perceptionFile PerceptionFile
     * @return Integer
     */
    Integer upsertSelective(PerceptionFile perceptionFile);

    /**
     * 条件查询
     * @param perceptionFile PerceptionFile
     * @return List<PerceptionFile>
    */
    List<PerceptionFile> query(PerceptionFile perceptionFile);

    /**
     * 查询总数
     * @return Integer
     */
    Long queryTotalCount();


}