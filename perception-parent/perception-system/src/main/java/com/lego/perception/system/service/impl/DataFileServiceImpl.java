package com.lego.perception.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.perception.system.mapper.DataFileMapper;
import com.lego.perception.system.service.IDataFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataFileServiceImpl implements IDataFileService {

    @Autowired
    private DataFileMapper dataFileMapper;

    @Override
    public DataFile findById(Long id) {
        return  dataFileMapper.selectById(id);
    }

    @Override
    public List<DataFile> findList(DataFile dataFile) {
        QueryWrapper<DataFile> wrapper = query(dataFile);
        List<DataFile> dataFiles = dataFileMapper.selectList(wrapper);
        return dataFiles;
    }

    @Override
    public IPage<DataFile> findPagedList(DataFile dataFile, Page page) {
        QueryWrapper queryWrapper = query(dataFile);
        IPage<DataFile> ipage = dataFileMapper.selectPage(page, queryWrapper);
        return ipage;
    }

    @Override
    public RespVO insert(DataFile dataFile) {
        int result = 0;
        if (dataFile != null) {
            result = dataFileMapper.insert(dataFile);
        }
        if (result > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure("插入失敗");
    }

    @Override
    public RespVO<RespDataVO<Long>> insertList(List<DataFile> dataFiles) {
        int result = 0;
        if (CollectionUtils.isNotEmpty(dataFiles)) {
            result = dataFileMapper.insertList(dataFiles);
        }
        if (result > 0) {
            return RespVOBuilder.success(dataFiles.stream().map(DataFile::getId).collect(Collectors.toList()));
        }
        return RespVOBuilder.failure("插入失敗");
    }

    @Override
    public RespVO update(DataFile dataFile) {
        int result = 0;
        if (dataFile != null && dataFile.getId() != null) {
            result = dataFileMapper.updateById(dataFile);
        }
        if (result > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure("更新失敗");
    }

    @Override
    public RespVO updateList(List<DataFile> dataFiles) {
        int result = 0;
        if (CollectionUtils.isNotEmpty(dataFiles)) {
            result = dataFileMapper.updateList(dataFiles);
        }
        if (result > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure("更新失敗");
    }

    @Override
    public RespVO delete(Long id) {
        int result = 0;
        QueryWrapper<DataFile> queryWrapper = Wrappers.query();
        queryWrapper.eq("id", id);
        DataFile dataFile = new DataFile();
        dataFile.setDeleteFlag(2);
        result = dataFileMapper.update(dataFile, queryWrapper);
        if (result > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure("删除失败");
    }

    @Override
    public RespVO deleteList(List<Long> ids) {
        int result = 0;
        if (CollectionUtils.isNotEmpty(ids)) {
            result = dataFileMapper.deleteList(ids);
        }
        dataFileMapper.deleteList(ids);
        if (result > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure("删除失败");
    }


    private QueryWrapper<DataFile> query(DataFile dataFile) {
        QueryWrapper queryWrapper = Wrappers.query();

        if (dataFile != null) {
            if (dataFile.getId() != null) {
                queryWrapper.eq("id", dataFile.getId());
            }
            if (dataFile.getName() != null) {
                queryWrapper.eq("name", dataFile.getName());
            }
            if (dataFile.getFileUrl() != null) {
                queryWrapper.eq("file_url", dataFile.getFileUrl());
            }
            if (dataFile.getPreviewUrl() != null) {
                queryWrapper.eq("preview_url", dataFile.getPreviewUrl());
            }
            if (dataFile.getFileType() != null) {
                queryWrapper.eq("file_type", dataFile.getFileType());
            }
            if (dataFile.getCreatedBy() != null) {
                queryWrapper.eq("created_by", dataFile.getCreatedBy());
            }

            if (dataFile.getLastUpdatedBy() != null) {
                queryWrapper.eq("last_updated_by", dataFile.getLastUpdatedBy());
            }

        }
        return queryWrapper;
    }
}
