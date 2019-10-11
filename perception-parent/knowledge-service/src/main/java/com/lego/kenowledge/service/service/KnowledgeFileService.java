package com.lego.kenowledge.service.service;

import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.utils.UuidUtils;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.framework.file.feign.HDFSFileClient;
import com.lego.framework.system.feign.DataFileClient;
import com.lego.framework.system.model.entity.DataFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @auther xiaodao
 * @date 2019/10/11 14:26
 */
@Service
public class KnowledgeFileService {
    @Autowired
    private DataFileClient dataFileClient;

    @Autowired
    private HDFSFileClient hdfsFileClient;

    @Value("${hdfs.storePath}")
    private String storePath;


    @Value("${hdfs.savePath}")
    private String savePath;

    public String upLoadFile(MultipartFile[] files,String remark,String tags) {
        String batchNumber = UuidUtils.generateShortUuid();
        if (files == null || files.length == 0) {
            ExceptionBuilder.operateFailException("上传文件不能为空");
        }
        //返回文件名为键值 文件url为key的map
        RespVO<Map<String, String>> uploads = hdfsFileClient.uploads(storePath, savePath, files);
        if (uploads.getRetCode() != 1) {
            ExceptionBuilder.operateFailException("上传文件失败");
        }
        Map<String, String> uploadsInfo = uploads.getInfo();

        List<DataFile> dataFileList = new ArrayList<>();

        Arrays.stream(files).forEach(f -> {
            //文件名
            String originalFilename = f.getOriginalFilename();
            //文件url
            String fileUrl = uploadsInfo.get(originalFilename);
            //文件后缀
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            DataFile dataFile = new DataFile();
            dataFile.setFileUrl(fileUrl);
            dataFile.setFileType(ext);
            dataFile.setName(originalFilename);
            dataFile.setBatchNum(batchNumber);
            dataFileList.add(dataFile);
        });
        RespVO<RespDataVO<Long>> respDataVORespVO = dataFileClient.insertList(dataFileList);
        if (respDataVORespVO.getRetCode() != 1){
            ExceptionBuilder.operateFailException("上传文件失败");
        }
        return batchNumber;
    }


}
