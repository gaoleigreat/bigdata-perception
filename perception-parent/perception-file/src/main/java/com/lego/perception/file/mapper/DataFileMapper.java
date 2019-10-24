package com.lego.perception.file.mapper;

import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.system.model.entity.DataFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataFileMapper extends Mapper<DataFile> {
    /**
     * 批量插入
     * @param list List<DataFile
     * @return Integer
     */
    Integer batchInsert(List<DataFile> list);

    /**
     * 批量更新
     * @param list List<DataFile>
     * @return Integer
     */
    Integer batchUpdate(List<DataFile> list);

    /**
     * 存在即更新
     * @param dataFile DataFile
     * @return Integer
     */
    Integer upsert(@Param("dataFile") DataFile dataFile);

    /**
     * 存在即更新，可选择具体属性
     * @param dataFile DataFile
     * @return Integer
     */
    Integer upsertSelective(@Param("dataFile") DataFile dataFile);

    /**
     * 条件查询
     * @param dataFile DataFile
     * @return List<DataFile>
     */
    List<DataFile> query(@Param("dataFile") DataFile dataFile);

    /**
     * 查询总数
     * @return Integer
     */
    Long queryTotalCount();

}
