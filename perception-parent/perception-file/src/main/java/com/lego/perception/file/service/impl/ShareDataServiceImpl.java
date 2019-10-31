package com.lego.perception.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.framework.common.utils.UuidUtils;
import com.framework.mybatis.tool.WhereEntityTool;
import com.framework.mybatis.utils.PageUtil;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.framework.system.model.entity.ShareData;
import com.lego.perception.file.mapper.DataFileMapper;
import com.lego.perception.file.mapper.ShareDataMapper;
import com.lego.perception.file.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ShareDataServiceImpl implements IShareDataService {

    @Autowired
    private ShareDataMapper dataFileMapper;


    @Override
    public PagedResult<ShareData> selectPaged(ShareData dataFile, Page page) {
        return PageUtil.queryPaged(page, dataFile, dataFileMapper);
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
        Integer result = dataFileMapper.deleteById(id);
        return result;

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
        ShareData dataFile = dataFileMapper.selectById(id);
        if (dataFile == null) {
            return null;
        }
        return dataFile;
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
            return dataFileMapper.batchInsert(list);
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
    public RespVO<RespDataVO<ShareData>> selectBybatchNums(List<String> batchNums, String tags) {
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
        wrapper.in("batch_num", batchNums);
        List<ShareData> dataFiles = dataFileMapper.selectList(wrapper);
        return RespVOBuilder.success(dataFiles);
    }


    @Override
    public PagedResult<ShareData> queryByListBatch(ShareData dataFile, Page page) {

        IPage<ShareData> iPage = PageUtil.page2IPage(page);
        QueryWrapper<ShareData> wrapper = new QueryWrapper<>();
        WhereEntityTool.invoke(dataFile, wrapper);
        wrapper.groupBy("batch_num");
        IPage<ShareData> dataFileIPage = dataFileMapper.selectPage(iPage, wrapper);
        return PageUtil.iPage2Result(dataFileIPage);
    }

}
