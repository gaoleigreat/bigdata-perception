package com.lego.framework.base.utils;

import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * The type Uuid utils.
 *
 * @author Wesley.Xia
 * @description
 * @since 2019 /1/7 19:11
 */
public class UuidUtils {
    private static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    /**
     * 生成8位UUID.
     *
     * @return the string
     */
    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    /**
     * 生成不带符号的32位UUID.
     *
     * @return the string
     */
    public static String generateUuidNumber() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成32位标准格式UUID.
     *
     * @return the string
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成16位UUID.
     *
     * @return the string
     */
    public static String generate16Uuid() {
        return DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes());
    }
}
