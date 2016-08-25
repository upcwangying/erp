package com.erp.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AESEncrypt {
    private static String D_A_K = "D5B6D8584F94B432";
    private static String D_A_V = "205681D89D731A8F";
    private static AESEncrypt instance;

    private Cipher enc;
    private Cipher dec;
    private SecretKeySpec keySpec;
    private IvParameterSpec ivSpec;

    public static AESEncrypt getInstance() {
        if (instance == null) {
            instance = new AESEncrypt();
        }
        return instance;
    }

    private AESEncrypt() {
        this.init(D_A_K.getBytes(), D_A_V.getBytes());
    }

    public void init(byte[] key, int keyoff, int keylen, byte[] iv, int ivoff) {
        keySpec = new SecretKeySpec(key, keyoff, keylen, "AES");
        ivSpec = new IvParameterSpec(iv, ivoff, 16);
    }

    public void init(byte[] key, byte[] iv) {
        keySpec = new SecretKeySpec(key, "AES");
        ivSpec = new IvParameterSpec(iv);
    }

    public int getCipherLen(int len) {
        int pad = len % 16;
        if (0 == pad) {
            return len + 16;
        }
        return len - pad + 16;
    }

    protected void encrypt(byte[] indata, int inoff, int inlen, byte[] outdata,
                           int outoff) throws Exception {
        initEncryptor();
        enc.doFinal(indata, inoff, inlen, outdata, outoff);
    }

    protected byte[] encrypt(byte[] indata, int inoff, int inlen) throws Exception {
        initEncryptor();
        return enc.doFinal(indata, inoff, inlen);
    }

    public byte[] encrypt(byte[] indata) throws Exception {
        initEncryptor();
        return enc.doFinal(indata);
    }

    public byte[] decrypt(byte[] indata) throws Exception {
        initDecryptor();
        return dec.doFinal(indata);
    }

    public int getPlainLen(int len) {
        return len;
    }

    protected void decrypt(byte[] indata, int inoff, int inlen, byte[] outdata,
                           int outoff) throws Exception {
        initDecryptor();
        dec.doFinal(indata, inoff, inlen, outdata, outoff);
    }

    protected byte[] decrypt(byte[] indata, int inoff, int inlen) throws Exception {
        initDecryptor();
        return dec.doFinal(indata, inoff, inlen);
    }

    private void initEncryptor() throws Exception {
        if (null == enc) {
            enc = Cipher.getInstance("AES/CBC/PKCS5Padding");
            enc.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        }
    }

    private void initDecryptor() throws Exception {
        if (null == dec) {
            dec = Cipher.getInstance("AES/CBC/PKCS5Padding");
            dec.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println();
//        System.out.println();
    }

}
