package com.lego.perception.business.utils;

import org.springframework.util.StringUtils;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/3 18:45
 * @desc :
 */
public class TableUtils {

    /**
     * 类型转换
     *
     * @param category
     * @return
     */
    public static String getColumnType(Integer category) {
        switch (category) {
            case 1:
                // input
            case 2:
                // textarea
            case 4:
                // 图片
            case 5:
                // 附件
            case 7:
                // 多选
                return "VARCHAR(256)";
            case 3:
                // date
                return "TIMESTAMP";
            case 9:
                // 整数
            case 6:
                // 单选
                return "BIGINT";
            case 14:
                // 小数
                return "DOUBLE(255, 5)";
            default:
                break;
        }
        return null;
    }

    /**
     * 是否为空
     *
     * @param isRequired
     * @return
     */
    public static String getIsNull(Integer isRequired) {
        if (isRequired != null && isRequired == 2) {
            return "NOT NULL";
        }
        return "";
    }

    /**
     * 获取默认值
     *
     * @param defaultValue
     * @return
     */
    public static String getDefaultValue(String defaultValue) {
        if (StringUtils.isEmpty(defaultValue)) {
            return "";
        }
        return "DEFAULT " + defaultValue;
    }

    /**
     * 获取描述信息
     *
     * @param desc
     * @return
     */
    public static String getComment(String desc) {
        if (StringUtils.isEmpty(desc)) {
            return "";
        }
        return "COMMENT '" + desc + "'";
    }
}
