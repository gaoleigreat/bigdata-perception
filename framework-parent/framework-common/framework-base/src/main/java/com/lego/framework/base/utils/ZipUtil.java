package com.lego.framework.base.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @auther xiaodao
 * @date 2019/11/5 9:50
 */
public class ZipUtil {



    public static byte[] getFileByte(String url) {
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void downloadBatchByFile(HttpServletResponse response, Map<String, byte[]> files, String zipName) {

        try {

            response.reset();

            zipName = java.net.URLEncoder.encode(zipName, "UTF-8");

            response.setContentType("application/vnd.ms-excel;charset=UTF-8");

            response.setHeader("Content-Disposition", "attachment;filename=" + zipName + ".zip");


            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());

            BufferedOutputStream bos = new BufferedOutputStream(zos);


            for (Map.Entry<String, byte[]> entry : files.entrySet()) {

                String fileName = entry.getKey();            //每个zip文件名

                byte[] file = entry.getValue();            //这个zip文件的字节


                BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(file));

                zos.putNextEntry(new ZipEntry(fileName));

                int len = 0;

                byte[] buf = new byte[10 * 1024];

                while ((len = bis.read(buf, 0, buf.length)) != -1) {

                    bos.write(buf, 0, len);

                }

                bis.close();

                bos.flush();

            }

            bos.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

}
