package com.erp.util;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 图片压缩类
 * Created by wang_ on 2016-08-25.
 */
public class ImageUtil {
    private static Logger logger = Logger.getLogger(ImageUtil.class);

    /**
     * 图片文件读取
     * @param srcImgPath
     * @return
     */
    private static BufferedImage InputImage(String srcImgPath) {
        BufferedImage srcImage = null;
        try {
            FileInputStream in = new FileInputStream(srcImgPath);
            srcImage = javax.imageio.ImageIO.read(in);
        } catch (IOException e) {
            logger.error("读取图片文件出错！" + e.getMessage(), e);
            e.printStackTrace();
        }
        return srcImage;
    }

    /**
     * 图片文件读取
     * @param in
     * @return
     */
    private static BufferedImage InputImage(FileInputStream in) {
        BufferedImage srcImage = null;
        try {
            srcImage = javax.imageio.ImageIO.read(in);
        } catch (IOException e) {
            logger.error("读取图片流文件出错！" + e.getMessage(), e);
            e.printStackTrace();
        }
        return srcImage;
    }

    /**
     * 将图片按照指定的图片尺寸压缩
     * @param srcImgPath 源图片路径
     * @param outImgPath 输出的压缩图片的路径
     * @param new_w 压缩后的图片宽
     * @param new_h 压缩后的图片高
     */
    public static void compressImage(String srcImgPath, String outImgPath,
                                     int new_w, int new_h) {
        BufferedImage src = InputImage(srcImgPath);
        disposeImage(src, outImgPath, new_w, new_h);
    }

    /**
     * 将图片按照指定的图片尺寸压缩
     * @param in
     * @param file
     * @param new_w 压缩后的图片宽
     * @param new_h 压缩后的图片高
     */
    public static void compressImage(FileInputStream in, File file,
                                     int new_w, int new_h) {
        BufferedImage src = InputImage(in);
        disposeImage(src, file, new_w, new_h);
    }

    /**
     * 指定长或者宽的最大值来压缩图片
     * @param srcImgPath 源图片路径
     * @param outImgPath 输出的压缩图片的路径
     * @param maxLength 长或者宽的最大值
     */
    public static void compressImage(String srcImgPath, String outImgPath,
                                     int maxLength) {
        // 得到图片
        BufferedImage src = InputImage(srcImgPath);
        if (null != src) {
            int old_w = src.getWidth();
            // 得到源图宽
            int old_h = src.getHeight();
            // 得到源图长
            int new_w = 0;
            // 新图的宽
            int new_h = 0;
            // 新图的长
            // 根据图片尺寸压缩比得到新图的尺寸
            if (old_w > old_h) {
                // 图片要缩放的比例
                new_w = maxLength;
                new_h = (int) Math.round(old_h * ((float) maxLength / old_w));
            } else {
                new_w = (int) Math.round(old_w * ((float) maxLength / old_h));
                new_h = maxLength;
            }
            disposeImage(src, outImgPath, new_w, new_h);
        }
    }

    /**
     * 处理图片
     * @param src
     * @param outImgPath
     * @param new_w
     * @param new_h
     */
    private synchronized static void disposeImage(BufferedImage src,
                                                  String outImgPath, int new_w, int new_h) {
        // 调用方法输出图片文件
        OutImage(outImgPath, createNewImage(src, new_w, new_h));
    }

    /**
     * 处理图片
     * @param src
     * @param file
     * @param new_w
     * @param new_h
     */
    private synchronized static void disposeImage(BufferedImage src,
                                                  File file, int new_w, int new_h) {
        // 调用方法输出图片文件
        OutImage(file, createNewImage(src, new_w, new_h));
    }

    /**
     *
     * @param src
     * @param new_w
     * @param new_h
     * @return
     */
    private static BufferedImage createNewImage(BufferedImage src, int new_w, int new_h) {
        // 得到图片
        int old_w = src.getWidth();
        // 得到源图宽
        int old_h = src.getHeight();
        // 得到源图长
        BufferedImage newImg = null;
        // 判断输入图片的类型
        switch (src.getType()) {
            case 13:
                // png,gif
                newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_4BYTE_ABGR);
                break;
            default:
                newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
                break;
        }
        Graphics2D g = newImg.createGraphics();
        // 从原图上取颜色绘制新图
        g.drawImage(src, 0, 0, old_w, old_h, null);
        g.dispose();
        // 根据图片尺寸压缩比得到新图的尺寸
        newImg.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
        return newImg;
    }

    /**
     * 将图片文件输出到指定的路径，并可设定压缩质量
     * @param outImgPath
     * @param newImg
     */
    private static void OutImage(String outImgPath, BufferedImage newImg) {
        // 判断输出的文件夹路径是否存在，不存在则创建
        File file = new File(outImgPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        logger.info(outImgPath.substring(outImgPath.lastIndexOf(".") + 1));
        // 输出到文件流
        try {
            ImageIO.write(newImg, outImgPath.substring(outImgPath.lastIndexOf(".") + 1), new File(outImgPath));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 将图片文件输出到指定的路径，并可设定压缩质量
     * @param file
     * @param newImg
     */
    private static void OutImage(File file, BufferedImage newImg) {
        String fileName = file.getName();
        // 输出到文件流
        try {
            ImageIO.write(newImg, fileName.substring(fileName.lastIndexOf(".") + 1), file);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

//    public static void main(String args[]) {
//        try {
//            compressImage("", "E:/ww/_abc.png", 200, 150);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        System.out.println("ok");
//    }
}
