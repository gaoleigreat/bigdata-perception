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
import com.framework.mybatis.utils.PageUtil;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.system.model.entity.DataFile;

import com.lego.perception.file.mapper.DataFileMapper;
import com.lego.perception.file.service.IDataFileService;
import com.lego.perception.file.service.IHdfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataFileServiceImpl implements IDataFileService {

    @Autowired
    private DataFileMapper dataFileMapper;

    @Autowired
    private IHdfsService iHdfsService;

    @Value("${hdfs.storePath}")
    private String storePath;


    @Value("${hdfs.savePath}")
    private String savePath;

    @Override
    public DataFile findById(Long id) {
        return dataFileMapper.selectById(id);
    }

    @Override
    public PagedResult findPagedList(DataFile dataFile, Page page) {
        PagedResult pagedResult = PageUtil.queryPaged(page, dataFile, dataFileMapper);
        if (!CollectionUtils.isEmpty(pagedResult.getResultList())) {
            List<DataFile> resultList = pagedResult.getResultList();
        }
        return pagedResult;
    }


    @Override
    public RespVO<Long> insert(DataFile dataFile) {
        int result = 0;
        if (dataFile != null) {
            result = dataFileMapper.insert(dataFile);
        }
        if (result > 0) {
            return RespVOBuilder.success(dataFile.getId());
        }
        return RespVOBuilder.failure("插入失敗");
    }

    @Override
    public RespVO<RespDataVO<Long>> insertList(List<DataFile> dataFiles) {
        if (CollectionUtils.isNotEmpty(dataFiles)) {
            dataFiles.forEach(f -> {
                dataFileMapper.insert(f);
            });

        }

        return RespVOBuilder.success(dataFiles.stream().map(DataFile::getId).collect(Collectors.toList()));

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

    @Override
    public RespVO selectBybatchNums(List<String> batchNums, String tags) {
        QueryWrapper<DataFile> wrapper = Wrappers.query();
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
        List<DataFile> dataFiles = dataFileMapper.selectList(wrapper);
        return RespVOBuilder.success(dataFiles);
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


    @Override
    public RespVO<RespDataVO<DataFile>> upLoadFile(MultipartFile[] files, String remark, String tags) {
        String batchNumber = UuidUtils.generateShortUuid();
        if (files == null || files.length == 0) {
            ExceptionBuilder.operateFailException("上传文件不能为空");
        }
        //返回文件名为键值 文件url为key的map
     /*   Map<String, String> uploadsInfo = uploadToHdfs(storePath, savePath, files);
        if (uploadsInfo.isEmpty()) {
            ExceptionBuilder.operateFailException("上传文件失败");
        }*/
        List<DataFile> dataFileList = new ArrayList<>();
        Arrays.stream(files).forEach(f -> {
            //文件名
            String originalFilename = f.getOriginalFilename();
            //文件url
            //  String fileUrl = uploadsInfo.get(originalFilename);
            //文件后缀
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            DataFile dataFile = new DataFile();
            // dataFile.setFileUrl(fileUrl);
            dataFile.setFileType(ext);
            dataFile.setName(originalFilename);
            dataFile.setBatchNum(batchNumber);
            dataFile.setRemark(remark);
            dataFile.setTags(tags);
            dataFileList.add(dataFile);
        });
        RespVO<RespDataVO<Long>> respDataVORespVO = insertList(dataFileList);
        if (respDataVORespVO.getRetCode() != 1) {
            ExceptionBuilder.operateFailException("上传文件失败");
        }
        return RespVOBuilder.success(dataFileList);
    }


    @Override
    public Map<String, String> uploadToHdfs(String storePath, String savePath, MultipartFile[] files) {
        Map<String, String> fileNamemap = new HashMap<>();
        if (files == null || files.length == 0) {
            ExceptionBuilder.operateFailException("上传文件files不能为空");
        }
        Arrays.stream(files).forEach(f -> {
            String name = f.getOriginalFilename();
            String subffix = name.substring(name.lastIndexOf(".") + 1, name.length());//我这里取得文件后缀
            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            File file = new File(storePath);
            if (!file.exists()) {//目录不存在就创建
                file.mkdirs();
            }
            try {
                f.transferTo(new File(storePath + "/" + fileName + "." + subffix));//保存文件
                iHdfsService.uploadFileToHdfs(storePath + "/" + fileName + "." + subffix, savePath);
                fileNamemap.put(name, storePath + "/" + fileName + "." + subffix);
            } catch (IOException e) {
                e.printStackTrace();
                ExceptionBuilder.operateFailException("上传HDFS文件失败");
            }
        });
        return fileNamemap;
    }


}
