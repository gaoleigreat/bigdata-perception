package com.lego.perception.file.service;
import com.lego.perception.file.model.UploadFile;
import com.survey.lib.common.vo.RespVO;

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
