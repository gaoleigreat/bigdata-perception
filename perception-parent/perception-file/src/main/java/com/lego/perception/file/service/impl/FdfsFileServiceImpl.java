package com.lego.perception.file.service.impl;

import com.framework.common.consts.RespConsts;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.exception.ExceptionBuilder;
import com.lego.perception.file.model.UploadFile;
import com.lego.perception.file.service.IFdfsFileService;
import org.csource.common.MyException;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FdfsFileServiceImpl implements IFdfsFileService {

    public static final Logger log = LoggerFactory.getLogger(FdfsFileServiceImpl.class);

    @Autowired
    private TrackerGroup trackerGroup;

    @Value("${file.service.url}")
    private String fileServiceUrl;

    @Override
    public Map<String, Object> upload(UploadFile file) {
        Map<String, Object> map = new HashMap<>(16);
        StorageClient storageClient;
        TrackerServer trackerServer = null;
        try {
            trackerServer = trackerGroup.getConnection();
            storageClient = new StorageClient(trackerServer, null);

            String[] ary = storageClient.upload_file(file.getContent(), file.getExt(), null);
            String groupName = ary[0];
            String remoteName = ary[1];
            map.put("data", fileServiceUrl + groupName + "/" + remoteName + "?fileName=" + file.getFileName());
        } catch (IOException | MyException e) {
            log.error("upload file to [fastdfs] system error", e);
            ExceptionBuilder.operateFailException("上传失败");
        } finally {
            if (null != trackerServer) {
                try {
                    trackerServer.close();
                } catch (IOException e) {
                    log.error("close tracker server error", e);
                }
            }
        }
        return map;
    }

    @Override
    public byte[] download(String groupName, String remoteFile) {
        StorageClient storageClient = null;
        TrackerServer trackerServer = null;
        byte[] datas = null;
        try {
            trackerServer = trackerGroup.getConnection();
            storageClient = new StorageClient(trackerServer, null);
            datas = storageClient.download_file(groupName, remoteFile);
        } catch (IOException e) {
            log.error("download file from [fastdfs] system error", e);
            return datas;
        } catch (MyException e) {
            log.error("download file from [fastdfs] system error", e);
            return datas;
        } finally {
            if (null != trackerServer) {
                try {
                    trackerServer.close();
                } catch (IOException e) {
                    log.error("close tracker server error", e);
                }
            }
        }
        return datas;
    }

    @Override
    public Map<String, Object> webUpload(MultipartFile file) {
        try {
            UploadFile uploadFile = new UploadFile();
            uploadFile.setFileName(file.getOriginalFilename());

            uploadFile.setContent(file.getBytes());
            if (!StringUtils.isEmpty(file.getOriginalFilename())) {
                int pos = file.getOriginalFilename().lastIndexOf(".");
                if (pos > -1 && pos + 1 < file.getOriginalFilename().length()) {
                    uploadFile.setExt(file.getOriginalFilename().substring(pos + 1));
                }
            }
            return upload(uploadFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
