package com.lego.framework.base.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/4 12:56
 * @desc :
 */
public class ImageUtils {


    /**
     * 指定大小进行缩放
     * @param fromPath
     * @param toPath
     * @param width
     * @param height
     * @throws IOException
     */
    public static void size(String fromPath, String toPath, int width, int height) throws IOException {
        /*
         * 若图片横比200小，高比300小，不变
         * 若图片横比200小，高比300大，高缩小到300，图片比例不变
         * 若图片横比200大，高比300小，横缩小到200，图片比例不变
         * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
         */
        Thumbnails.of(fromPath)
                .size(width, height)
                .toFile(toPath);
    }


    /**
     * 按照比例进行缩放
     * @param fromPath
     * @param toPath
     * @param scale
     * @throws IOException
     */
    public static  void scale(String fromPath, String toPath,float scale) throws IOException {
        Thumbnails.of(fromPath)
                .scale(scale)
                .toFile(toPath);
    }


    /**
     *
     * @param fromPath
     * @param toPath
     * @param width
     * @param height
     * @throws IOException
     */
    public static void ratioSize(String fromPath, String toPath, int width, int height) throws IOException {
        //keepAspectRatio(false)默认是按照比例缩放的
        Thumbnails.of(fromPath)
                .size(width,height)
                .keepAspectRatio(false)
                .toFile(toPath);
    }


    /**
     * 旋转
     * @param fromPath
     * @param toPath
     * @param width
     * @param height
     * @param rotate
     * @throws IOException
     */
    public static void  rotate(String fromPath, String toPath, int width, int height,double rotate) throws IOException {
        Thumbnails.of(fromPath)
                .size(width,height)
                .rotate(rotate)
                .toFile(toPath);
    }


    /**
     * 添加水印
     * @param fromPath
     * @param toPath
     * @param watermark
     * @param transparency
     * @throws IOException
     */
    public static void  watermark(String fromPath, String toPath,String watermark,float transparency) throws IOException {
        //watermark(位置，水印图，透明度)
        Thumbnails.of(fromPath)
                .size(1280,1024)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(watermark)),transparency)
                // 输出的图片质量，范围：0.0~1.0，1为最高质量
                .outputQuality(0.8f)
                .toFile(toPath);
    }


    /**
     * 裁剪
     * @param fromPath
     * @param toPath
     * @throws IOException
     */
    public static void sourceRegion(String fromPath, String toPath) throws IOException {
        Thumbnails.of(fromPath)
                .sourceRegion(Positions.CENTER,400,400)
                .size(200,200)
                .keepAspectRatio(false)
                .toFile(toPath);
    }

    /**
     * 转化图像格式
     * @param fromPath
     * @param toPath
     * @throws IOException
     */
    public static  void  format(String fromPath, String toPath) throws IOException {
        //outputFormat(图像格式)
        Thumbnails.of(fromPath)
                .size(1280,1024)
                .outputFormat("png")
                .toFile(toPath);
    }


    /**
     * 输出到OutputStream
     * @return
     * @throws IOException
     */
    public static OutputStream  toOutputStream(String fromPath, String toPath) throws IOException {
        OutputStream os=new FileOutputStream(toPath);
        Thumbnails.of(fromPath)
                .size(1280,1024)
                .toOutputStream(os);
        return os;
    }

    /**
     * 输出到BufferedImage
     * @param fromPath
     * @param toPath
     * @throws IOException
     */
    public static void asBufferedImage(String fromPath, String toPath) throws IOException {
        //asBufferedImage()返回BufferedImage
        BufferedImage thumbnail=Thumbnails.of(fromPath)
                .size(1280,1024)
                .asBufferedImage();
        ImageIO.write(thumbnail,"jpg",new File(toPath));
    }



    public static void main(String []args) throws IOException {
       rotate("C:\\Users\\yanglf\\Desktop\\1.jpg","C:\\Users\\yanglf\\Desktop\\4.jpg",200,500,90);
    }

}
