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
        // Java文件名
        String fileName = "HelloWorld.java";
        // 邮箱
        String email = "laurenyang@imooc.com";

        // 判断.java文件名是否正确：合法的文件名应该以.java结尾
        /*
        参考步骤：
        1、获取文件名中最后一次出现"."号的位置
        2、根据"."号的位置，获取文件的后缀
        3、判断"."号位置及文件后缀名
        */
        //获取文件名中最后一次出现"."号的位置
        int index = fileName.lastIndexOf(".");

        // 获取文件的后缀
        String prefix = fileName.substring(index + 1, fileName.length());

        // 判断必须包含"."号，且不能出现在首位，同时后缀名为"java"
        if (index > 0 && "java".equals(prefix)) {
            System.out.println("Java文件名正确");
        } else {
            System.out.println("Java文件名无效");
        }

        // 判断邮箱格式是否正确：合法的邮箱名中至少要包含"@", 并且"@"是在"."之前
         /*
        参考步骤：
        1、获取文件名中"@"符号的位置
        2、获取邮箱中"."号的位置
        3、判断必须包含"@"符号，且"@"必须在"."之前
        */
        // 获取邮箱中"@"符号的位置
        int index2 = email.lastIndexOf("@");

        // 获取邮箱中"."号的位置
        int index3 = email.indexOf('.');

        // 判断必须包含"@"符号，且"@"必须在"."之前
        if (index2 != -1 && index3 > index2) {
            System.out.println("邮箱格式正确");
        } else {
            System.out.println("邮箱格式无效");
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
     * int转byte[]
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
     * byte[]转为int
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
