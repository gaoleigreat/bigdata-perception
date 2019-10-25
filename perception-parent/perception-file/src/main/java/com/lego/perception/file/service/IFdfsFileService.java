package com.lego.perception.file.service;
import com.framework.common.sdto.RespVO;
import com.lego.perception.file.model.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IFdfsFileService {

    /**
     * 上传
     * @param file
     * @return
     */
    Map<String, Object> upload(UploadFile file);

    /**
     * 下载
     * @param remoteFile
     * @param groupName
     * @return
     */
    byte[] download(String groupName, String remoteFile);

    /**
     * @param file
     * @return
     */
    Map<String, Object> webUpload(MultipartFile file);
}
