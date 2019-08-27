package com.lego.framework.base.utils;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Random;

/**
 * @author yanglf
 * @description
 * @since 2018/12/29
 **/
public class SecurityUtils {
    public static final String SECRET_DICT_a0 = "abcdefghijklmnopqrstuvwxyz0123456789";
    public static final String SECRET_DICT_Aa = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final String SECRET_DICT_Aa0 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static final String SECRET_DICT_0 = "0123456789";
    public static final String SECRET_DICT_A0 = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    /**
     * 生成密钥
     *
     * @param length 密钥长度
     * @param dict   密钥字典
     * @return
     */
    public static String createSecret(int length, String dict) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
//        sb.append(dict.charAt(random.nextInt(dict.length()))); //第1位必须为字母大小写
        for (int i = 0; i < length; i++) {//从第2位开始
            sb.append(dict.charAt(random.nextInt(dict.length())));
        }
        return sb.toString();
    }


    /**
     * 加密（MD5、Base64）
     *
     * @param clearText
     * @return
     */
    public static String encryptionWithMd5Base64(String clearText) {
        String cipherText;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            Base64.Encoder encoder = Base64.getEncoder();
            cipherText = encoder.encodeToString(md5.digest(clearText.getBytes("utf-8")));
        } catch (Exception e) {
            throw new RuntimeException("加密失败[clearText: " + clearText + "]");
        }
        return cipherText;
    }

    /**
     * 加密(MD5)
     *
     * @param clearText
     * @return
     */
    public static String encryptionWithMd5(String clearText) {
        String cipherText;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            cipherText = byteArrayToHexString(md5.digest(clearText
                    .getBytes("utf-8")));
        } catch (Exception e) {
            throw new RuntimeException("加密失败[clearText: " + clearText + "]");
        }
        return cipherText;
    }

    /**
     * 字节数组转换为字符串
     *
     * @param b
     * @return
     */
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 字节转字符串
     *
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     *
     */
    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

}
