package com.lego.perception.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.mybatis.tool.WhereEntityTool;
import com.framework.mybatis.utils.PageUtil;
import com.lego.framework.data.model.entity.PerceptionStructuredData;
import com.lego.framework.data.model.entity.PerceptionUnstructuredData;
import com.lego.framework.data.model.entity.ShareData;
import com.lego.perception.data.mapper.ShareDataMapper;
import com.lego.perception.data.service.IPerceptionStructuredDataService;
import com.lego.perception.data.service.IPerceptionUnstructuredDataService;
import com.lego.perception.data.service.IShareDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShareDataServiceImpl implements IShareDataService {

    @Autowired
    private ShareDataMapper dataFileMapper;

    @Autowired
    private IPerceptionStructuredDataService iPerceptionStructuredDataService;

    @Autowired
    private IPerceptionUnstructuredDataService iPerceptionUnstructuredDataService;


    @Override
    public PagedResult<ShareData> selectPaged(ShareData dataFile, Page page) {
        return PageUtil.queryPaged(page, dataFile, dataFileMapper);
    }


    @Override
    public List<ShareData> selectDataByBatchNum(List<String> batchNums, String tags,Integer publishFlag) {
        List<ShareData> shareDataList = new ArrayList<>();
        List<PerceptionStructuredData> perceptionStructuredDataList = iPerceptionStructuredDataService.selectDataByBatchNum(batchNums, tags,publishFlag);
        perceptionStructuredDataList.forEach(psd -> shareDataList.add(convertPerceptionStructuredData2ShareData(psd)));
        List<PerceptionUnstructuredData> perceptionUnstructuredDataList = iPerceptionUnstructuredDataService.selectDataByBatchNum(batchNums, tags,publishFlag);
        perceptionUnstructuredDataList.forEach(psd -> shareDataList.add(convertPerceptionUnstructuredData2ShareData(psd)));
        return shareDataList;
    }


    public ShareData convertPerceptionStructuredData2ShareData(PerceptionStructuredData perceptionStructuredData) {
        ShareData shareData = new ShareData();
        shareData.setBatchNum(perceptionStructuredData.getBatchNum());
        shareData.setBusinessModule(perceptionStructuredData.getBusinessModule());
        shareData.setSourceModule(perceptionStructuredData.getSourceModule());
        shareData.setDataType(1);
        shareData.setCreationDate(perceptionStructuredData.getCreationDate());
        shareData.setLastUpdateDate(perceptionStructuredData.getLastUpdateDate());
        shareData.setCreatedBy(perceptionStructuredData.getCreatedBy());
        shareData.setLastUpdatedBy(perceptionStructuredData.getCreatedBy());
        shareData.setName(perceptionStructuredData.getName());
        shareData.setDataSize(perceptionStructuredData.getSize());
        shareData.setRemark(perceptionStructuredData.getRemark());
        shareData.setTags(perceptionStructuredData.getTags());
        shareData.setTemplateId(perceptionStructuredData.getTemplateId());
        shareData.setProjectId(perceptionStructuredData.getProjectId());
        return shareData;
    }

    public ShareData convertPerceptionUnstructuredData2ShareData(PerceptionUnstructuredData perceptionUnstructuredData) {
        ShareData shareData = new ShareData();
        shareData.setBatchNum(perceptionUnstructuredData.getBatchNum());
        shareData.setBusinessModule(perceptionUnstructuredData.getBusinessModule());
        shareData.setSourceModule(perceptionUnstructuredData.getSourceModule());
        shareData.setDataType(2);
        shareData.setCreationDate(perceptionUnstructuredData.getCreationDate());
        shareData.setLastUpdateDate(perceptionUnstructuredData.getLastUpdateDate());
        shareData.setCreatedBy(perceptionUnstructuredData.getCreatedBy());
        shareData.setLastUpdatedBy(perceptionUnstructuredData.getCreatedBy());
        shareData.setName(perceptionUnstructuredData.getName());
        shareData.setDataSize(perceptionUnstructuredData.getSize());
        shareData.setRemark(perceptionUnstructuredData.getRemark());
        shareData.setTags(perceptionUnstructuredData.getTags());
        shareData.setProjectId(perceptionUnstructuredData.getProjectId());
        return shareData;
    }


    /**
     * 创建DataFile
     *
     * @param dataFile
     * @return
     */
    @Override
    public Integer insert(ShareData dataFile) {
        if (dataFile == null) {
            return 0;
        }
        return dataFileMapper.insert(dataFile);
    }


    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteByPrimaryKey(Long id) {
        if (id == null) {
            return 0;
        }
        return dataFileMapper.deleteById(id);

    }

    /**
     * 修改DataFile
     *
     * @param dataFile
     * @return
     */
    @Override
    public Integer updateByPrimaryKey(ShareData dataFile) {
        if (dataFile == null) {
            return 0;
        }
        return dataFileMapper.updateById(dataFile);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    @Override
    public ShareData selectByPrimaryKey(Long id) {
        if (id == null) {
            return null;
        }
        return dataFileMapper.selectById(id);
    }


    /**
     * 批量插入
     *
     * @param list List<DataFile
     * @return Integer
     */
    @Override
    public Integer batchInsert(List<ShareData> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        } else {
            return dataFileMapper.batchInsert(list);
        }
    }

    /**
     * 批量更新
     *
     * @param list List<DataFile>
     * @return Integer
     */
    @Override
    public Integer batchUpdate(List<ShareData> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        } else {
            return dataFileMapper.batchUpdate(list);
        }
    }

    /**
     * 批量删除
     *
     * @param list List<Long >
     * @return Integer
     */
    @Override
    public Integer deleteBatchIds(List<Long> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        } else {
            return dataFileMapper.deleteBatchIds(list);
        }
    }

    /**
     * 存在即更新
     *
     * @param dataFile DataFile
     * @return Integer
     */
    @Override
    public Integer upsert(ShareData dataFile) {

        if (dataFile == null) {
            return 0;
        } else {
            return dataFileMapper.upsert(dataFile);
        }

    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @param dataFile DataFile
     * @return Integer
     */
    @Override
    public Integer upsertSelective(ShareData dataFile) {
        if (dataFile == null) {
            return 0;
        } else {
            return dataFileMapper.upsert(dataFile);
        }
    }

    /**
     * 条件查询
     *
     * @param dataFile DataFile
     * @return List<DataFile>
     */
    @Override
    public List<ShareData> query(ShareData dataFile) {
        if (dataFile == null) {
            return null;
        } else {
            return dataFileMapper.query(dataFile);
        }
    }

    /**
     * 查询总数
     *
     * @return Integer
     */
    @Override
    public Long queryTotalCount() {
        return dataFileMapper.queryTotalCount();
    }


    @Override
    public RespVO<RespDataVO<ShareData>> selectByBatchNums(List<String> batchNums, String tags, int isRecall) {
        QueryWrapper<ShareData> wrapper = Wrappers.query();
        if (tags != null) {
            String[] tag = tags.split(",");
            if (tag.length > 0) {
                wrapper.in("tags", tag);
            }
        }
        if (CollectionUtils.isEmpty(batchNums)) {
            return RespVOBuilder.failure("批次号为空");
        }
        wrapper.eq("is_recall", isRecall);
        wrapper.eq("delete_flag", 0);
        wrapper.in("batch_num", batchNums).orderByDesc("creation_date");
        List<ShareData> dataFiles = dataFileMapper.selectList(wrapper);
        return RespVOBuilder.success(dataFiles);
    }


    @Override
    public PagedResult<ShareData> queryByListBatch(ShareData dataFile, Page page) {

        IPage<ShareData> iPage = PageUtil.page2IPage(page);
        QueryWrapper<ShareData> wrapper = new QueryWrapper<>();
        WhereEntityTool.invoke(dataFile, wrapper);
        wrapper.eq("is_recall", 0);
        wrapper.eq("delete_flag", 0);
        wrapper.orderByDesc("creation_date");
        IPage<ShareData> dataFileIPage = dataFileMapper.selectPage(iPage, wrapper);
        return PageUtil.iPage2Result(dataFileIPage);
    }

    @Override
    public int updatePerceptionByBatchNum(List<String> batchNums, String tags, int isRecall,int publishFlag) {
        List<PerceptionStructuredData> perceptionStructuredDataList = iPerceptionStructuredDataService.selectDataByBatchNum(batchNums, tags,publishFlag);
        if (!CollectionUtils.isEmpty(perceptionStructuredDataList)) {
            perceptionStructuredDataList.forEach(perceptionStructuredData -> perceptionStructuredData.setPublishFlag(isRecall));
        }
        iPerceptionStructuredDataService.batchUpdate(perceptionStructuredDataList);
        List<PerceptionUnstructuredData> perceptionUnstructuredDataList = iPerceptionUnstructuredDataService.selectDataByBatchNum(batchNums, tags,publishFlag);
        if (!CollectionUtils.isEmpty(perceptionUnstructuredDataList)) {
            perceptionUnstructuredDataList.forEach(perceptionUnstructuredData -> perceptionUnstructuredData.setPublishFlag(isRecall));
        }
        iPerceptionUnstructuredDataService.batchUpdate(perceptionUnstructuredDataList);
        return 1;
    }

}
