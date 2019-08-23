package com.lego.perception.file.service.impl;

import com.lego.framework.base.utils.FpFileUtil;
import com.lego.perception.file.service.IFpFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gaolei
 * @date 2019/5/30/0030
 */
@Service
public class FpFileServiceImpl implements IFpFileService {
    public static final Logger log = LoggerFactory.getLogger(FpFileServiceImpl.class);

    /**
     * 文件访问路径
     */
    @Value("${fpfile.url}")
    private String fileUrl;
    /**
     * 文件保存路径
     */
    @Value("${fpfile.path}")
    private String fpFileRootPath;


    @Override
    public Map<String, Object> upload(MultipartFile file) {
        Map<String, Object> map = new HashMap<>(16);
        try {
            String uuid = FpFileUtil.getUUID();
            String fileName = file.getOriginalFilename();
            String type = fileName.substring(fileName.indexOf("."));
            String newFileName = uuid + type;
            map.put("data", FpFileUtil.getFileUrl(fileUrl, null, null, newFileName));
            map.put("fileName", newFileName);
            File tempFile = new File(FpFileUtil.getFileUrl(fpFileRootPath, null, null, newFileName));
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();//创建父级文件路径
            }
            tempFile.createNewFile();//创建文件
            // 转存文件
            file.transferTo(tempFile);
        } catch (Exception e) {
            log.error("upload file to  system error", e);
            return map;
        }
        return map;
    }


    @Override
    public byte[] download(String groupName, String remoteFile) {
        return new byte[0];
    }

}
