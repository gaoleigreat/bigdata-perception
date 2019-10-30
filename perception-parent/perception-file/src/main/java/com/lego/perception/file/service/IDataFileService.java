package com.lego.perception.file.service;


import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.lego.framework.equipment.model.entity.EquipmentCost;
import com.lego.framework.system.model.entity.DataFile;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Validated
public interface IDataFileService {
    /**
     * 分页查询
     *
     * @param dataFile
     * @param page
     * @return
     */

    PagedResult<DataFile> selectPaged(DataFile dataFile, Page page);

    /**
     * 创建DataFile
     *
     * @param dataFile
     * @return
     */
    Integer insert(@NotNull(message = "添加失败，参数不能为空") DataFile dataFile);


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
    Integer updateByPrimaryKey(@NotNull(message = "添加失败，参数不能为空") DataFile dataFile);


    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    DataFile selectByPrimaryKey(@NotNull(message = "查询失败，参数不能为空") Long id);


    /**
     * 批量插入
     *
     * @param list List<DataFile
     * @return Integer
     */
    Integer batchInsert(List<DataFile> list);

    /**
     * 批量更新
     *
     * @param list List<DataFile>
     * @return Integer
     */
    Integer batchUpdate(List<DataFile> list);

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
     * @param dataFile DataFile
     * @return Integer
     */
    Integer upsert(DataFile dataFile);

    /**
     * 存在即更新，可选择具体属性
     *
     * @param dataFile DataFile
     * @return Integer
     */
    Integer upsertSelective(DataFile dataFile);

    /**
     * 条件查询
     *
     * @param dataFile DataFile
     * @return List<DataFile>
     */
    List<DataFile> query(DataFile dataFile);

    /**
     * 查询总数
     *
     * @return Integer
     */
    Long queryTotalCount();

    RespVO selectBybatchNums(List<String> batchNums, String tags);


    /**
     * 上传业务文件
     *
     * @param files
     * @param remark
     * @param tags
     * @return
     */
    RespVO<RespDataVO<DataFile>> upLoadFile(MultipartFile[] files, String remark, String tags);

    /**
     * @param storePath
     * @param savePath
     * @param files
     * @return
     */
    Map<String, String> uploadToHdfs(String storePath, String savePath, MultipartFile[] files);

    /**
     * @param dataFile
     * @return
     */
    PagedResult<DataFile> queryByListBatch(DataFile dataFile,Page page);

    /**
     * @param batchNums
     * @param tags
     * @return
     */
    RespVO updateCheckStatusByBatchNums(List<String> batchNums, String tags);
}
