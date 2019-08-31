package com.lego.perception.file.util;

import com.aspose.cad.imageoptions.CadRasterizationOptions;
import com.aspose.cad.imageoptions.PdfCompliance;
import com.aspose.cad.imageoptions.PdfDocumentOptions;
import com.aspose.cad.imageoptions.PdfOptions;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.exception.ExceptionBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
public class FileUtil {
    /**
     * @param urlPath     下载文件路径
     * @param downloadDir 下载后保存路径
     * @param fileName    文件名（包括后缀）
     * @return
     */
    public static File downloadFile(String urlPath, String downloadDir, String fileName) {
        File file = null;
        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            //设置超时
            httpURLConnection.setConnectTimeout(1000 * 5);
            //设置请求方式，默认是GET
            httpURLConnection.setRequestMethod("GET");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();
            // 文件大小
            int fileLength = httpURLConnection.getContentLength();

            // 控制台打印文件大小
            System.out.println("您要下载的文件大小为:" + fileLength / (1024 * 1024) + "MB");

            // 建立链接从请求中获取数据
            URLConnection con = url.openConnection();
            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
            // 指定文件名称(有需求可以自定义)
            String fileFullName = fileName;
            // 指定存放位置(有需求可以自定义)
            String path = downloadDir + File.separatorChar + fileFullName;
            file = new File(path);
            // 校验文件夹目录是否存在，不存在就创建一个目录
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[2048];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                // 控制台打印文件下载的百分比情况
                log.info("下载了-------> " + len * 100 / fileLength + "%\n");
            }
            // 关闭资源
            bin.close();
            out.close();
            System.out.println("文件下载成功！");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            log.error("文件下载失败{}", e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("文件下载失败{}", e.getMessage());
        } finally {
            return file;
        }
    }


    public static RespVO changemeCadToPDf(String cadPath,String origionFileName, String targetPath, String targetFileName) {
        try {
            com.aspose.cad.Image objImage = com.aspose.cad.Image.load(cadPath+"/" + origionFileName);
            // Create an instance of PdfOptions
            PdfOptions pdfOptions = new PdfOptions();
            pdfOptions.setVectorRasterizationOptions(new CadRasterizationOptions());

            pdfOptions.setCorePdfOptions(new PdfDocumentOptions());

            pdfOptions.getCorePdfOptions().setCompliance(PdfCompliance.PdfA1a);
            objImage.save(targetPath + "/" + targetFileName, pdfOptions);
        } catch (Exception e) {
            log.error("文件转换失败{}", e.getMessage());
            return RespVOBuilder.failure("文件转换失败");
        }
        return RespVOBuilder.success("文件转换成功");

    }



    public static byte[] fileToBytes(File file) {
        byte[] buffer = null;
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        } catch (Exception ex) {
            log.error("转化失败");
        } finally {
            try {
                if (null != bos) {
                    log.error("转化失败");
                }
            } finally{
                try {
                    if(null!=fis){
                        fis.close();
                    }
                } catch (IOException ex) {
                    log.error("转化失败");
                }
            }
        }

        return buffer;
    }
}
