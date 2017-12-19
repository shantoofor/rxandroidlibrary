package com.shantoo.rxandroid.common.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DES4Util {

    // 密钥 dvuH8dr9Taxq0Ema2yHKnw==
    private final static String secretKey = "q8jwft9B";
    private static byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0};

    // 密钥  如果 密匙不足 8个 字符  java代码 要加 \0
    // 例如 secretKey = "xxxxxxx\0";

    public static String encode(String plainText) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(plainText.getBytes());
        return Base64.encodeToString(encryptedData, 1);
    }

    public static String decode(String encryptText) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText, 1));
        return new String(decryptData);
    }
}
