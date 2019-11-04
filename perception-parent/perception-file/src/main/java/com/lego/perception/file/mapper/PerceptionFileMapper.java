package com.lego.perception.file.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import com.lego.framework.file.model.PerceptionFile;


/**
 * @description PerceptionFileMapper
 * @author¸ßÀÚ
 * @since jdk1.8
 */
public interface PerceptionFileMapper extends Mapper<PerceptionFile> {

    /**
     * 批量插入
     *
     * @param list List<PerceptionFile
     * @return Integer
     */
    Integer batchInsert(List<PerceptionFile> list);

    /**
     * 批量更新
     *
     * @param list List<PerceptionFile>
     * @return Integer
     */
    Integer batchUpdate(List<PerceptionFile> list);

    /**
     * 存在即更新
     *
     * @param perceptionFile PerceptionFile
     * @return Integer
     */
    Integer upsert(@Param("perceptionFile") PerceptionFile perceptionFile);

    /**
     * 存在即更新，可选择具体属性
     *
     * @param perceptionFile PerceptionFile
     * @return Integer
     */
    Integer upsertSelective(@Param("perceptionFile") PerceptionFile perceptionFile);

    /**
     * 条件查询
     *
     * @param perceptionFile PerceptionFile
     * @return List<PerceptionFile>
     */
    List<PerceptionFile> query(@Param("perceptionFile") PerceptionFile perceptionFile);

    /**
     * 查询总数
     *
     * @return Integer
     */
    Long queryTotalCount();


}