package com.erp.util;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * ͼƬѹ����
 * Created by wang_ on 2016-08-25.
 */
public class ImageUtil {
    private static Logger logger = Logger.getLogger(ImageUtil.class);

    /**
     * ͼƬ�ļ���ȡ
     * @param srcImgPath
     * @return
     */
    private static BufferedImage InputImage(String srcImgPath) {
        BufferedImage srcImage = null;
        try {
            FileInputStream in = new FileInputStream(srcImgPath);
            srcImage = javax.imageio.ImageIO.read(in);
        } catch (IOException e) {
            logger.error("��ȡͼƬ�ļ�����" + e.getMessage(), e);
            e.printStackTrace();
        }
        return srcImage;
    }

    /**
     * ͼƬ�ļ���ȡ
     * @param in
     * @return
     */
    private static BufferedImage InputImage(FileInputStream in) {
        BufferedImage srcImage = null;
        try {
            srcImage = javax.imageio.ImageIO.read(in);
        } catch (IOException e) {
            logger.error("��ȡͼƬ���ļ�����" + e.getMessage(), e);
            e.printStackTrace();
        }
        return srcImage;
    }

    /**
     * ��ͼƬ����ָ����ͼƬ�ߴ�ѹ��
     * @param srcImgPath ԴͼƬ·��
     * @param outImgPath �����ѹ��ͼƬ��·��
     * @param new_w ѹ�����ͼƬ��
     * @param new_h ѹ�����ͼƬ��
     */
    public static void compressImage(String srcImgPath, String outImgPath,
                                     int new_w, int new_h) {
        BufferedImage src = InputImage(srcImgPath);
        disposeImage(src, outImgPath, new_w, new_h);
    }

    /**
     * ��ͼƬ����ָ����ͼƬ�ߴ�ѹ��
     * @param in
     * @param file
     * @param new_w ѹ�����ͼƬ��
     * @param new_h ѹ�����ͼƬ��
     */
    public static void compressImage(FileInputStream in, File file,
                                     int new_w, int new_h) {
        BufferedImage src = InputImage(in);
        disposeImage(src, file, new_w, new_h);
    }

    /**
     * ָ�������߿�����ֵ��ѹ��ͼƬ
     * @param srcImgPath ԴͼƬ·��
     * @param outImgPath �����ѹ��ͼƬ��·��
     * @param maxLength �����߿�����ֵ
     */
    public static void compressImage(String srcImgPath, String outImgPath,
                                     int maxLength) {
        // �õ�ͼƬ
        BufferedImage src = InputImage(srcImgPath);
        if (null != src) {
            int old_w = src.getWidth();
            // �õ�Դͼ��
            int old_h = src.getHeight();
            // �õ�Դͼ��
            int new_w = 0;
            // ��ͼ�Ŀ�
            int new_h = 0;
            // ��ͼ�ĳ�
            // ����ͼƬ�ߴ�ѹ���ȵõ���ͼ�ĳߴ�
            if (old_w > old_h) {
                // ͼƬҪ���ŵı���
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
     * ����ͼƬ
     * @param src
     * @param outImgPath
     * @param new_w
     * @param new_h
     */
    private synchronized static void disposeImage(BufferedImage src,
                                                  String outImgPath, int new_w, int new_h) {
        // ���÷������ͼƬ�ļ�
        OutImage(outImgPath, createNewImage(src, new_w, new_h));
    }

    /**
     * ����ͼƬ
     * @param src
     * @param file
     * @param new_w
     * @param new_h
     */
    private synchronized static void disposeImage(BufferedImage src,
                                                  File file, int new_w, int new_h) {
        // ���÷������ͼƬ�ļ�
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
        // �õ�ͼƬ
        int old_w = src.getWidth();
        // �õ�Դͼ��
        int old_h = src.getHeight();
        // �õ�Դͼ��
        BufferedImage newImg = null;
        // �ж�����ͼƬ������
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
        // ��ԭͼ��ȡ��ɫ������ͼ
        g.drawImage(src, 0, 0, old_w, old_h, null);
        g.dispose();
        // ����ͼƬ�ߴ�ѹ���ȵõ���ͼ�ĳߴ�
        newImg.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
        return newImg;
    }

    /**
     * ��ͼƬ�ļ������ָ����·���������趨ѹ������
     * @param outImgPath
     * @param newImg
     */
    private static void OutImage(String outImgPath, BufferedImage newImg) {
        // �ж�������ļ���·���Ƿ���ڣ��������򴴽�
        File file = new File(outImgPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        logger.info(outImgPath.substring(outImgPath.lastIndexOf(".") + 1));
        // ������ļ���
        try {
            ImageIO.write(newImg, outImgPath.substring(outImgPath.lastIndexOf(".") + 1), new File(outImgPath));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * ��ͼƬ�ļ������ָ����·���������趨ѹ������
     * @param file
     * @param newImg
     */
    private static void OutImage(File file, BufferedImage newImg) {
        String fileName = file.getName();
        // ������ļ���
        try {
            ImageIO.write(newImg, fileName.substring(fileName.lastIndexOf(".") + 1), file);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        try {
            compressImage("", "E:/ww/_abc.png", 200, 150);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("ok");
    }
}
