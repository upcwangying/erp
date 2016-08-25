package com.erp.util;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by wang_ on 2016-08-18.
 */
public class JavaTest {

    public static void test1() {
        // Java�ļ���
        String fileName = "HelloWorld.java";
        // ����
        String email = "laurenyang@imooc.com";

        // �ж�.java�ļ����Ƿ���ȷ���Ϸ����ļ���Ӧ����.java��β
        /*
        �ο����裺
        1����ȡ�ļ��������һ�γ���"."�ŵ�λ��
        2������"."�ŵ�λ�ã���ȡ�ļ��ĺ�׺
        3���ж�"."��λ�ü��ļ���׺��
        */
        //��ȡ�ļ��������һ�γ���"."�ŵ�λ��
        int index = fileName.lastIndexOf(".");

        // ��ȡ�ļ��ĺ�׺
        String prefix = fileName.substring(index + 1, fileName.length());

        // �жϱ������"."�ţ��Ҳ��ܳ�������λ��ͬʱ��׺��Ϊ"java"
        if (index > 0 && "java".equals(prefix)) {
            System.out.println("Java�ļ�����ȷ");
        } else {
            System.out.println("Java�ļ�����Ч");
        }

        // �ж������ʽ�Ƿ���ȷ���Ϸ���������������Ҫ����"@", ����"@"����"."֮ǰ
         /*
        �ο����裺
        1����ȡ�ļ�����"@"���ŵ�λ��
        2����ȡ������"."�ŵ�λ��
        3���жϱ������"@"���ţ���"@"������"."֮ǰ
        */
        // ��ȡ������"@"���ŵ�λ��
        int index2 = email.lastIndexOf("@");

        // ��ȡ������"."�ŵ�λ��
        int index3 = email.indexOf('.');

        // �жϱ������"@"���ţ���"@"������"."֮ǰ
        if (index2 != -1 && index3 > index2) {
            System.out.println("�����ʽ��ȷ");
        } else {
            System.out.println("�����ʽ��Ч");
        }
    }

    public static void test2() {
        int a = 1;
        int b = 2;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println("a=" + a);
        System.out.println("b=" + b);
    }

    /**
     * intתbyte[]
     *
     * @param value
     * @return
     */
    public static byte[] int2Byte(int value) {
        byte[] arr = new byte[4];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (byte) ((value >>> i * 8) & 0xff);
        }
        return arr;
    }

    /**
     * byte[]תΪint
     *
     * @param arr
     * @return
     */
    public static int byte2Int(byte[] arr) {
        int a = 0;
        for (int i = 0; i < arr.length; i++) {
            a += (int) ((arr[i] & 0xff) << i * 8);
        }
        return a;
    }

    public static void printHex(String fileName) throws IOException {
        FileInputStream in = new FileInputStream(fileName);

        int b;
        int j = 1;
        while ((b = in.read()) != -1) {
            if (b <= 0xf) {
                System.out.print("0");
            }
            System.out.print(Integer.toHexString(b) + " ");
            if (j++ % 40 == 0) {
                System.out.println();
            }

        }
        in.close();
    }

    public static void printHexByByteArray(String fileName) throws IOException {
        FileInputStream in = new FileInputStream(fileName);

        byte[] buf = new byte[20 * 1024];
        int bytes = 0;
        int j = 1;
        while ((bytes = in.read(buf, 0, buf.length)) != -1) {
            for (int i = 0; i < bytes; i++) {
                if ((buf[i] & 0xff) <= 0xf) {
                    System.out.print("0");
                }
                System.out.print(Integer.toHexString(buf[i] & 0xff) + " ");
                if (j++ % 40 == 0) {
                    System.out.println();
                }
            }

        }
        in.close();
    }

    public static void main(String[] args) throws IOException {
//        RandomAccessFile randomAccessFile = new RandomAccessFile("", "rw");
//        randomAccessFile.writeInt();
//        JavaTest.test2();
//        byte[] arr = JavaTest.int2Byte(8143);
//        System.out.println(arr[0]+","+arr[1]+","+arr[2]+","+arr[3]);
//        System.out.println(JavaTest.byte2Int(arr));

//        System.out.println(Integer.toBinaryString(255));
        long start = System.currentTimeMillis();
//        printHexByByteArray("d:\\2.mp3");
        printHex("d:\\2.mp3");
        long end = System.currentTimeMillis();
        System.out.println();
        System.out.println(end - start);
    }
}
