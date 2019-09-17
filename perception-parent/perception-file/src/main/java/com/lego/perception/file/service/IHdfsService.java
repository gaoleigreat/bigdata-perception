package com.lego.perception.file.service;

import com.framework.common.sdto.RespVO;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.PathFilter;

import java.io.File;
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
     *
     * @param path
     * @return
     */
    boolean mkdir(String path);


    /**
     * 上传文件
     *
     * @param srcFile 本地文件路徑
     * @param dstPath hdfs文件路徑
     * @return
     */
    RespVO uploadFileToHdfs(String srcFile, String dstPath);


    /**
     * 上傳文件
     *
     * @param dstPath hdfs目標路徑
     * @param file    上傳的文件
     * @return
     */
    RespVO copyfileToHdfs(String dstPath, File file);


    /**
     * 查询文件
     *
     * @param path
     * @param pathFilter
     * @return
     */
    List<Map<String, Object>> listFiles(String path, PathFilter pathFilter);


    /**
     * 下载文件
     *
     * @param srcFile hdfs 文件路徑
     * @param dstFile 保存的文件路徑
     */
    void downloadFileFromHdfs(String srcFile, String dstFile);


    /**
     * 以數據流的方式打開文件
     *
     * @param path
     * @return
     */
    FSDataInputStream open(String path);


    /**
     * 以字節數組形式打開文件  方便web端下載
     *
     * @param path
     * @return
     */
    byte[] openWithBytes(String path);


    /**
     * 以字符串形式打開文件内容
     *
     * @param path
     * @return
     */
    String openWithString(String path);


    /**
     * 序列化文件内容
     *
     * @param path
     * @param clazz
     * @param <T>
     * @return
     */
    <T extends Object> T openWithObject(String path, Class<T> clazz);


    /**
     * 获取文件的集群位置
     *
     * @param path
     * @return
     */
    BlockLocation[] getFileBlockLocations(String path);


    /**
     * 重命名文件
     *
     * @param srcFile 源文件路徑
     * @param dstFile 修改后的文件路徑
     * @return
     */
    boolean rename(String srcFile, String dstFile);


    /**
     * 刪除文件
     *
     * @param path
     * @return
     */
    boolean delete(String path);


    /**
     * 文件是否存在
     *
     * @param path
     * @return
     */
    boolean checkExists(String path);
}
