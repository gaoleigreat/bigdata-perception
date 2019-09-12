package com.lego.perception.file.service;

import com.framework.common.sdto.RespVO;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.PathFilter;

import java.util.List;
import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/12 17:49
 * @desc :
 */

public interface IHdfsService {

    /**
     * 创建hdfs文件
     * @param path
     * @return
     */
    boolean mkdir(String path);


    /**
     * 上传文件
     * @param srcFile
     * @param dstPath
     * @return
     */
    RespVO uploadFileToHdfs(String srcFile, String dstPath);


    /**
     * 查询文件
     * @param path
     * @param pathFilter
     * @return
     */
    List<Map<String, Object>> listFiles(String path, PathFilter pathFilter);


    /**
     * 下载文件
     * @param srcFile
     * @param dstFile
     */
    void downloadFileFromHdfs(String srcFile, String dstFile);


    /**
     * @param path
     * @return
     */
    FSDataInputStream  open(String path);


    /**
     * @param srcFile
     * @param dstFile
     * @return
     */
    boolean rename(String srcFile, String dstFile);


    /**
     * @param path
     * @return
     */
    boolean delete(String path);
}
