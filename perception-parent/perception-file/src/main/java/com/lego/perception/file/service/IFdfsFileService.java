package com.lego.perception.file.service;
import com.framework.common.sdto.RespVO;
import com.lego.perception.file.model.UploadFile;
public interface IFdfsFileService {

    /**
     * 上传
     * @param file
     * @return
     */
    RespVO upload(UploadFile file);

    /**
     * 下载
     * @param remoteFile
     * @param groupName
     * @return
     */
    byte[] download(String groupName, String remoteFile);
}
