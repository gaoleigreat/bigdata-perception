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

import com.lego.perception.file.mapper.DataFileMapper;
import com.lego.perception.file.service.IDataFileService;
import com.lego.perception.file.service.IFdfsFileService;
import com.lego.perception.file.service.IFpFileService;
import com.lego.perception.file.service.IHdfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DataFileServiceImpl implements IDataFileService {

    @Autowired
    private DataFileMapper dataFileMapper;


    @Value("${define.store.type}")
    private Integer storeType;


    @Autowired
    private IHdfsService iHdfsService;

    @Autowired
    private IFdfsFileService iFdfsFileService;

    @Autowired
    private IFpFileService iFpFileService;


    @Value("${hdfs.storePath}")
    private String storePath;


    @Value("${hdfs.savePath}")
    private String savePath;


    @Override
    public PagedResult<DataFile> selectPaged(DataFile dataFile, Page page) {
        return PageUtil.queryPaged(page, dataFile, dataFileMapper);
    }

    /**
     * 创建DataFile
     *
     * @param dataFile
     * @return
     */
    @Override
    public Integer insert(DataFile dataFile) {
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
    public Integer updateByPrimaryKey(DataFile dataFile) {
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
    public DataFile selectByPrimaryKey(Long id) {
        if (id == null) {
            return null;
        }
        DataFile dataFile = dataFileMapper.selectById(id);
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
    public Integer batchInsert(List<DataFile> list) {
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
    public Integer batchUpdate(List<DataFile> list) {
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
    public Integer upsert(DataFile dataFile) {

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
    public Integer upsertSelective(DataFile dataFile) {
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
    public List<DataFile> query(DataFile dataFile) {
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


    @Override
    public RespVO<RespDataVO<DataFile>> upLoadFile(MultipartFile[] files, String remark, String tags) {
        String batchNumber = UuidUtils.generateShortUuid();
        if (files == null || files.length == 0) {
            ExceptionBuilder.operateFailException("上传文件不能为空");
        }
        //返回文件名为键值 文件url为key的map
        Map<String, String> uploadsInfo = uploadToHdfs(storePath, savePath, files);
        if (uploadsInfo.isEmpty()) {
            ExceptionBuilder.operateFailException("上传文件失败");
        }
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

        Integer num = dataFileMapper.batchInsert(dataFileList);
        if (num == dataFileList.size()) {
            return RespVOBuilder.success(dataFileList);
        } else {
            return RespVOBuilder.failure("上传失败");
        }
    }


    @Override
    public Map<String, String> uploadToHdfs(String storePath, String savePath, MultipartFile[] files) {
        Map<String, String> fileNamemap = new HashMap<>();
        if (files == null || files.length == 0) {
            ExceptionBuilder.operateFailException("上传文件files不能为空");
        }
        for (MultipartFile f : files) {
            String name = f.getOriginalFilename();
            try {
                if (storeType == 0) {
                    // 本地保存
                    Map<String, Object> objectMap = iFpFileService.upload(f);
                    if (objectMap == null || !objectMap.containsKey("data")) {
                        ExceptionBuilder.operateFailException("上传本地文件失败");
                    }
                    fileNamemap.put(name, objectMap.get("data").toString());
                } else if (storeType == 1) {
                    // fastdfs
                    Map<String, Object> objectMap = iFdfsFileService.webUpload(f);
                    if (objectMap == null || !objectMap.containsKey("data")) {
                        ExceptionBuilder.operateFailException("上传本地文件失败");
                    }
                    fileNamemap.put(name, objectMap.get("data").toString());
                } else if (storeType == 2) {
                    // HDFS
                    String subffix = name.substring(name.lastIndexOf(".") + 1, name.length());//我这里取得文件后缀
                    String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                    File file = new File(storePath);
                    if (!file.exists()) {//目录不存在就创建
                        file.mkdirs();
                    }
                    iHdfsService.uploadFileToHdfs(storePath + "/" + fileName + "." + subffix, savePath);
                    fileNamemap.put(name, storePath + "/" + fileName + "." + subffix);
                } else {
                    ExceptionBuilder.operateFailException("未定义存储方式");
                }
            } catch (IOException e) {
                e.printStackTrace();
                ExceptionBuilder.operateFailException("上传HDFS文件失败");
            }
        }
        return fileNamemap;
    }

    @Override
    public PagedResult<DataFile> queryByListBatch(DataFile dataFile, Page page) {

        IPage<DataFile> iPage = PageUtil.page2IPage(page);
        QueryWrapper<DataFile> wrapper = new QueryWrapper<>();
        WhereEntityTool.invoke(dataFile, wrapper);
        wrapper.groupBy("batch_num");
        IPage<DataFile> dataFileIPage = dataFileMapper.selectPage(iPage, wrapper);
        return PageUtil.iPage2Result(dataFileIPage);
    }

    @Override
    public RespVO updateCheckStatusByBatchNums(List<String> batchNums, String tags) {
        QueryWrapper<DataFile> wrapper = new QueryWrapper<>();
        wrapper.in("batch_num", batchNums);
        List<DataFile> dataFiles = dataFileMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(dataFiles)) {
            for (DataFile dataFile : dataFiles) {
                dataFile.setCheckFlag(1);
            }
            Integer integer = dataFileMapper.batchUpdate(dataFiles);
            if (integer > 0) {
                return RespVOBuilder.success();
            }
        }
        return RespVOBuilder.failure();
    }


}
