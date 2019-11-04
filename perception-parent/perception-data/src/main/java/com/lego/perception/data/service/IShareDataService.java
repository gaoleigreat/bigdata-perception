package com.lego.perception.data.service;


import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.lego.framework.data.model.entity.ShareData;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface IShareDataService {
    /**
     * 分页查询
     *
     * @param dataFile
     * @param page
     * @return
     */

    PagedResult<ShareData> selectPaged(ShareData dataFile, Page page);


    /**
     * @param batchNum
     * @param tags
     * @return
     */
    List<ShareData> selectDataByBatchNum(List<String> batchNum,String tags);


    /**
     * 创建DataFile
     *
     * @param dataFile
     * @return
     */
    Integer insert(@NotNull(message = "添加失败，参数不能为空") ShareData dataFile);


    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    Integer deleteByPrimaryKey(@NotNull(message = "删除失败，参数不能为空") Long id);

    /**
     * 修改DataFile
     *
     * @param dataFile
     * @return
     */
    Integer updateByPrimaryKey(@NotNull(message = "添加失败，参数不能为空") ShareData dataFile);


    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    ShareData selectByPrimaryKey(@NotNull(message = "查询失败，参数不能为空") Long id);


    /**
     * 批量插入
     *
     * @param list List<DataFile
     * @return Integer
     */
    Integer batchInsert(List<ShareData> list);

    /**
     * 批量更新
     *
     * @param list List<DataFile>
     * @return Integer
     */
    Integer batchUpdate(List<ShareData> list);

    /**
     * 批量删除
     *
     * @param list List<Long >
     * @return Integer
     */
    Integer deleteBatchIds(List<Long> list);

    /**
     * 存在即更新
     *
     * @param dataFile ShareData
     * @return Integer
     */
    Integer upsert(ShareData dataFile);

    /**
     * 存在即更新，可选择具体属性
     *
     * @param dataFile ShareData
     * @return Integer
     */
    Integer upsertSelective(ShareData dataFile);

    /**
     * 条件查询
     *
     * @param dataFile ShareData
     * @return List<DataFile>
     */
    List<ShareData> query(ShareData dataFile);

    /**
     * 查询总数
     *
     * @return Integer
     */
    Long queryTotalCount();


    /**
     * @param batchNums
     * @param tags
     * @return
     */
    RespVO<RespDataVO<ShareData>> selectBybatchNums(List<String> batchNums, String tags);


    /**
     * @param dataFile
     * @return
     */
    PagedResult<ShareData> queryByListBatch(ShareData dataFile, Page page);

    /**
     * @param batchNums
     * @param tags
     * @return
     */
    int updatePerceptionByBatchNum(List<String> batchNums, String tags);
}
