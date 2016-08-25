package com.erp.product.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ͼƬѹ����
 * Created by wang_ on 2016-08-25.
 */
public class ImageUtil {

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
            System.out.println("��ȡͼƬ�ļ�����" + e.getMessage());
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
            System.out.println("��ȡͼƬ�ļ�����" + e.getMessage());
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
                // newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_4BYTE_ABGR);
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
        newImg.getGraphics().drawImage(
                src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0,
                null);
        // ���÷������ͼƬ�ļ�
        OutImage(outImgPath, newImg);
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
        newImg.getGraphics().drawImage(
                src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0,
                null);
        // ���÷������ͼƬ�ļ�
        OutImage(file, newImg);
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
        System.out.println(outImgPath.substring(outImgPath.lastIndexOf(".") + 1));
        // ������ļ���
        try {
            ImageIO.write(newImg, outImgPath.substring(outImgPath.lastIndexOf(".") + 1), new File(outImgPath));
        } catch (Exception e) {
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
            e.printStackTrace();
        }
    }

    /**
     *
     * @param filepath
     * @param pathMap
     * @return
     * @throws Exception
     */
    public static Map<Integer, String> readfile(String filepath,
                                                Map<Integer, String> pathMap) throws Exception {
        if (pathMap == null) {
            pathMap = new HashMap<Integer, String>();
        }

        File file = new File(filepath);
        // �ļ�
        if (!file.isDirectory()) {
            pathMap.put(pathMap.size(), file.getPath());
        } else if (file.isDirectory()) { // �����Ŀ¼�� ����������Ŀ¼ȡ�������ļ���
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(filepath + "/" + filelist[i]);
                if (!readfile.isDirectory()) {
                    pathMap.put(pathMap.size(), readfile.getPath());
                } else if (readfile.isDirectory()) { // ��Ŀ¼��Ŀ¼
                    readfile(filepath + "/" + filelist[i], pathMap);
                }
            }
        }
        return pathMap;
    }


    public static void main(String args[]) {
        try {
            Map<Integer, String> map = readfile("E:/yuan", null);
            for (int i = 0; i < map.size(); i++) {
                System.out.println(map.get(i) + " ==" + i);
                System.out.println();
                String oldpath = map.get(i);
                compressImage(map.get(i), "E:/ww/_" + i + ".png", 200, 150);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("ok");
    }
}
