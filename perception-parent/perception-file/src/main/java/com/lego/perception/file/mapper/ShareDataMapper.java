package com.lego.perception.file.mapper;

import com.framework.mybatis.mapper.Mapper;
import com.lego.framework.system.model.entity.ShareData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xunai
 */
public interface ShareDataMapper extends Mapper<ShareData> {
    /**
     * 批量插入
     * @param list List<DataFile
     * @return Integer
     */
    Integer batchInsert(List<ShareData> list);

    /**
     * 批量更新
     * @param list List<DataFile>
     * @return Integer
     */
    Integer batchUpdate(List<ShareData> list);



    /**
     * 存在即更新
     * @param dataFile DataFile
     * @return Integer
     */
    Integer upsert(@Param("dataFile") ShareData dataFile);

    /**
     * 存在即更新，可选择具体属性
     * @param dataFile DataFile
     * @return Integer
     */
    Integer upsertSelective(@Param("dataFile") ShareData dataFile);

    /**
     * 条件查询
     * @param dataFile DataFile
     * @return List<DataFile>
     */
    List<ShareData> query(@Param("dataFile") ShareData dataFile);

    /**
     * 查询总数
     * @return Integer
     */
    Long queryTotalCount();

}
