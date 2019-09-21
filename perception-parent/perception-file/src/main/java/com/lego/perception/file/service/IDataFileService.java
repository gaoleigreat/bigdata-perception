package com.lego.perception.file.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.lego.framework.system.model.entity.DataFile;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface IDataFileService {
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    DataFile findById(@NotNull Long id);


    /**
     * 查询列表
     *
     * @param DataFile
     * @return
     */
    List<DataFile> findList(DataFile DataFile);


    /**
     * 分页查询列表
     *
     * @param DataFile
     * @param page
     * @return
     */
    IPage<DataFile> findPagedList(DataFile DataFile, Page page);

    /**
     * 新增
     *
     * @param DataFile
     * @return
     */
    RespVO insert(DataFile DataFile);

    /**
     * 批量新增
     *
     * @param dictionaries
     * @return
     */
    RespVO<RespDataVO<Long>> insertList(List<DataFile> dictionaries);

    /**
     * 更新
     *
     * @param DataFile
     * @return
     */
    RespVO update(DataFile DataFile);

    /**
     * 批量更新
     *
     * @param dictionaries
     * @return
     */
    RespVO updateList(List<DataFile> dictionaries);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    RespVO delete(@NotNull Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    RespVO deleteList(List<Long> ids);

    /**
     * 通过batchIda批量查询
     *
     * @param batchNums
     * @return
     */
    RespVO selectBybatchNums(List<String> batchNums);
}
