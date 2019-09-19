package com.lego.framework.base.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/17 10:53
 * @desc :
 */
@Slf4j
public class FileUtils {

    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * MultipartFile 转  File
     *
     * @param file
     * @return
     */
    public static File multipartFileToFile(MultipartFile file) {
        File f = null;
        try {
            if (file != null && !file.isEmpty()) {
                InputStream ins = file.getInputStream();
                f = new File(file.getOriginalFilename());
                inputStreamToFile(ins, f);
            }
            return f;
        } catch (Exception e) {
            e.printStackTrace();
            return f;
        }
    }


    /**
     * File  转  MultipartFile
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static MultipartFile toMultipartFile(File file) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        return new MockMultipartFile(file.getName(), file.getName(),
                MediaType.APPLICATION_OCTET_STREAM_VALUE, fileInputStream);
    }



    public static void main(String []args) throws Exception {
        File file=new File("D:\\bilibili\\【伦桑】原创古风「遗剑赠山河」-唱完了一首诗.flv");
        MultipartFile multipartFile = toMultipartFile(file);
        String originalFilename = multipartFile.getOriginalFilename();
        String type = originalFilename.lastIndexOf(".") > 0 ? originalFilename.substring(originalFilename.lastIndexOf(".") + 1) : "";
        log.info(type);
    }

}
