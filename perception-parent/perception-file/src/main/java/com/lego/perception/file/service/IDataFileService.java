package com.lego.perception.file.service;



import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.lego.framework.system.model.entity.DataFile;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

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
     * 分页查询列表
     *
     * @param DataFile
     * @param page
     * @return
     */
    PagedResult findPagedList(DataFile DataFile, Page page);

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
}
