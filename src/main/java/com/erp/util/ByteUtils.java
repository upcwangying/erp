package com.erp.util;

/**
 * Created by IntelliJ IDEA.
 * User: Wang Tao
 * Date: 2010-6-24
 * Time: 16:36:33
 */

public class ByteUtils {
    public static final int LONG_FIELD_SIZE = 8;
    public static final int INT_FIELD_SIZE = 4;
    public static final int SHORT_FIELD_SIZE = 2;

    public static byte[] short2byte(short[] sa) {
        int length = sa.length;
        byte[] ba = new byte[length * 2];
        for (int i = 0, j = 0, k; i < length; ) {
            k = sa[i++];
            ba[j++] = (byte) ((k >>> 8) & 0xFF);
            ba[j++] = (byte) (k & 0xFF);
        }
        return (ba);
    }

    public static short[] byte2short(byte[] ba) {
        int length = ba.length;
        short[] sa = new short[length / 2];
        for (int i = 0, j = 0; j < length / 2; ) {
            sa[j++] = (short) (((ba[i++] & 0xFF) << 8) | ((ba[i++] & 0xFF)));
        }
        return (sa);
    }

    public static byte[] int2byte(int[] ia) {
        int length = ia.length;
        byte[] ba = new byte[length * 4];
        for (int i = 0, j = 0, k; i < length; ) {
            k = ia[i++];
            ba[j++] = (byte) ((k >>> 24) & 0xFF);
            ba[j++] = (byte) ((k >>> 16) & 0xFF);
            ba[j++] = (byte) ((k >>> 8) & 0xFF);
            ba[j++] = (byte) (k & 0xFF);
        }
        return (ba);
    }

    public static int[] byte2int(byte[] ba) {
        int length = ba.length;
        int[] ia = new int[length / 4];
        for (int i = 0, j = 0; j < length / 4; ) {
            ia[j++] = (((ba[i++] & 0xFF) << 24) | ((ba[i++] & 0xFF) << 16) | ((ba[i++] & 0xFF) << 8) | ((ba[i++] & 0xFF)));
        }
        return (ia);
    }

    public static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String toHEX(byte[] ba) {
        int length = ba.length;
        char[] buf = new char[length * 2];
        for (int i = 0, j = 0, k; i < length; ) {
            k = ba[i++];
            buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
            buf[j++] = HEX_DIGITS[k & 0x0F];
        }
        return new String(buf);
    }

    public static void printByte2Hex(byte[] bytes) {
        int length = bytes.length;
        System.err.println("");
        for (int i = 0, k; i < length; ) {
            k = bytes[i++];
            System.err.print(HEX_DIGITS[(k >>> 4) & 0x0F]);
            System.err.print(HEX_DIGITS[k & 0x0F]);
            System.err.print(",");
        }
        System.err.println();
    }

    public static String toHEX(short[] ia) {
        int length = ia.length;
        char[] buf = new char[length * 5];
        for (int i = 0, j = 0, k; i < length; ) {
            k = ia[i++];
            buf[j++] = HEX_DIGITS[(k >>> 12) & 0x0F];
            buf[j++] = HEX_DIGITS[(k >>> 8) & 0x0F];
            buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
            buf[j++] = HEX_DIGITS[k & 0x0F];
        }
        return new String(buf);
    }

    public static String toHEX(int[] ia) {
        int length = ia.length;
        char[] buf = new char[length * 10];
        for (int i = 0, j = 0, k; i < length; ) {
            k = ia[i++];
            buf[j++] = HEX_DIGITS[(k >>> 28) & 0x0F];
            buf[j++] = HEX_DIGITS[(k >>> 24) & 0x0F];
            buf[j++] = HEX_DIGITS[(k >>> 20) & 0x0F];
            buf[j++] = HEX_DIGITS[(k >>> 16) & 0x0F];
            buf[j++] = ' ';
            buf[j++] = HEX_DIGITS[(k >>> 12) & 0x0F];
            buf[j++] = HEX_DIGITS[(k >>> 8) & 0x0F];
            buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
            buf[j++] = HEX_DIGITS[k & 0x0F];
        }
        return new String(buf);
    }

    public static String toHEX1(byte b) {
        char[] buf = new char[2];
        int j = 0;
        buf[j++] = HEX_DIGITS[(b >>> 4) & 0x0F];
        buf[j++] = HEX_DIGITS[b & 0x0F];
        return new String(buf);
    }

    public static String toHEX1(byte[] ba) {
        int length = ba.length;
        char[] buf = new char[length * 2];
        for (int i = 0, j = 0, k; i < length; ) {
            k = ba[i++];
            buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
            buf[j++] = HEX_DIGITS[k & 0x0F];
        }
        return new String(buf);
    }

    public static String toHEX1(short[] ia) {
        int length = ia.length;
        char[] buf = new char[length * 4];
        for (int i = 0, j = 0, k; i < length; ) {
            k = ia[i++];
            buf[j++] = HEX_DIGITS[(k >>> 12) & 0x0F];
            buf[j++] = HEX_DIGITS[(k >>> 8) & 0x0F];
            buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
            buf[j++] = HEX_DIGITS[k & 0x0F];
        }
        return new String(buf);
    }

    public static String toHEX1(int i) {
        char[] buf = new char[8];
        int j = 0;
        buf[j++] = HEX_DIGITS[(i >>> 28) & 0x0F];
        buf[j++] = HEX_DIGITS[(i >>> 24) & 0x0F];
        buf[j++] = HEX_DIGITS[(i >>> 20) & 0x0F];
        buf[j++] = HEX_DIGITS[(i >>> 16) & 0x0F];
        buf[j++] = HEX_DIGITS[(i >>> 12) & 0x0F];
        buf[j++] = HEX_DIGITS[(i >>> 8) & 0x0F];
        buf[j++] = HEX_DIGITS[(i >>> 4) & 0x0F];
        buf[j++] = HEX_DIGITS[i & 0x0F];
        return new String(buf);
    }

    public static String toHEX1(int[] ia) {
        int length = ia.length;
        char[] buf = new char[length * 8];
        for (int i = 0, j = 0, k; i < length; ) {
            k = ia[i++];
            buf[j++] = HEX_DIGITS[(k >>> 28) & 0x0F];
            buf[j++] = HEX_DIGITS[(k >>> 24) & 0x0F];
            buf[j++] = HEX_DIGITS[(k >>> 20) & 0x0F];
            buf[j++] = HEX_DIGITS[(k >>> 16) & 0x0F];
            buf[j++] = HEX_DIGITS[(k >>> 12) & 0x0F];
            buf[j++] = HEX_DIGITS[(k >>> 8) & 0x0F];
            buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
            buf[j++] = HEX_DIGITS[k & 0x0F];
        }
        return new String(buf);
    }

    public static byte[] hex2byte(String hex) {
        int len = hex.length();
        byte[] buf = new byte[((len + 1) / 2)];
        int i = 0, j = 0;
        if ((len % 2) == 1) {
        	buf[j++] = (byte) hexDigit(hex.charAt(i++));
        }

        while (i < len) {
            buf[j++] = (byte) ((hexDigit(hex.charAt(i++)) << 4) | hexDigit(hex.charAt(i++)));
        }
        return buf;
    }

    public static boolean isHex(String hex) {
        int len = hex.length();
        int i = 0;
        char ch;
        while (i < len) {
            ch = hex.charAt(i++);
            if (!((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'F') || (ch >= 'a' && ch <= 'f'))) {
            	return false;
            }
        }
        return true;
    }

    public static int hexDigit(char ch) {
        if (ch >= '0' && ch <= '9') {
        	return ch - '0';
        }
        if (ch >= 'A' && ch <= 'F') {
        	return ch - 'A' + 10;
        }
        if (ch >= 'a' && ch <= 'f') {
        	return ch - 'a' + 10;
        }
        return (0);
    }

    public static int bytes2Int(byte[] src) {
        if (src == null || src.length == 0) {
        	throw new NullPointerException();
        }
        int dest = 0;
        byte[] temp = new byte[src.length];
        if (src[0] < 0) {
            for (int i = src.length - 1; i >= 0; i--) {
                temp[i] = new Integer(~(int) src[i]).byteValue();
            }
        } else {
        	System.arraycopy(src, 0, temp, 0, src.length);
        }
        for (int i = temp.length - 1; i >= 0; i--) {
            dest += (temp[i] & 0x00ff) << (temp.length - 1 - i) * 8;
        }
        if (src[0] < 0) {
            dest = (-1) * ++dest;
        }
        return dest;
    }

    public static byte[] long2Bytes(long src) {
        byte[] dest = new byte[LONG_FIELD_SIZE];
        for (int i = 0; i < LONG_FIELD_SIZE; i++) {
            dest[LONG_FIELD_SIZE - 1 - i] = new Long(src >> i * 8).byteValue();
        }
        return dest;
    }

    public static byte[] int2Bytes(int src) {
        byte[] dest = new byte[INT_FIELD_SIZE];
        for (int i = 0; i < INT_FIELD_SIZE; i++) {
            dest[INT_FIELD_SIZE - 1 - i] = new Integer(src >> i * 8).byteValue();
        }
        return dest;
    }

    public static String byteArr2Str(byte[] arrB, int decimal) {
        int iLen = arrB.length;
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, decimal));
        }
        return sb.toString();
    }

    /**
     * byte�����������.
     *
     * @return
     */
    public static byte[] byteArrayXor(byte[] ba1, byte[] ba2) {
        int ba1Length = ba1.length;
        int ba2Length = ba2.length;
        int c = ba1Length;
        int d = ba1Length;
        if (ba1Length < ba2Length) {
            c = ba2Length;
        } else {
            d = ba2Length;
        }
        byte[] ba3 = new byte[c];
        if (c == ba1Length) {
            System.arraycopy(ba1, 0, ba3, 0, ba1Length);
        } else {
            System.arraycopy(ba2, 0, ba3, 0, ba2Length);
        }
        for (int i = 0; i < d; i++) {
            ba3[i] = (byte) (ba1[i] ^ ba2[i]);
        }
        return ba3;
    }

    /**
     * �������ַ���ÿ����λת��Ϊʮ����������.
     *
     * @param src
     * @return
     */
    public static byte[] string2ByteArray(String src) {
        int length = src.length();
        int a = length / 2;
        int b = length % 2;
        if (b != 0) {
            src = "0" + src;
            a++;
        }
        byte[] bt = new byte[a];
        for (int i = 0; i < a; i++) {
            String ss1 = src.substring(2 * i, 2 * (i + 1) - 1);
            String ss2 = src.substring(2 * (i + 1) - 1, 2 * (i + 1));
            bt[i] = uniteBytes(ss1, ss2);
        }
        return bt;
    }

    private static byte uniteBytes(String src0, String src1) {
        byte _b0 = Byte.decode("0x" + src0).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + src1).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

}

