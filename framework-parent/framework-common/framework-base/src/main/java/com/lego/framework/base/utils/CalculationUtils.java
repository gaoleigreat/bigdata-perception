package com.lego.framework.base.utils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @author yanglf
 * @description
 * @since 2019/1/21
 **/
public class CalculationUtils {


    /**
     * 计算  单次沉降量
     * 单位 毫米 mm
     * @param currElevation 本次沉降量    m
     * @param preElevation  上次沉降量    m
     * @return 单次沉降量
     */
    public static double settlementDiff(double currElevation, double preElevation) {
        //单次沉降超限  单次沉降量 = 本次高程 - 上次高程
        return doubleSubtract(currElevation, preElevation) * 1000;
    }


    /**
     * 计算  累积沉降量
     * 单位 mm
     * @param currElevation 本次高程     m
     * @param initElevation 初始高程     m
     * @return 累积沉降量
     */
    public static double totalSettlement(double currElevation, double initElevation) {
        //累积沉降超限 累积沉降量 = 本次高程 - 初次高程
        return doubleSubtract(currElevation, initElevation) * 1000;
    }


    /**
     * 计算  沉降速率
     * 单位 mm/d
     * @param totalSettlement 累积沉降量   m
     * @param currDate        当前时间
     * @param initDate        初始时间
     * @return 沉降速率
     */
    public static double settlementRate(double totalSettlement, Date currDate, Date initDate) {
        //沉降速率超限  沉降速率 = 累积沉降量 / 历经的天数（自然天数）
        int days = DateUtils.betweenDays(initDate, currDate);
        return doubleDivide(totalSettlement,  days + 1);
    }


    /**
     * Double add double.
     *
     * @param data1 the data 1
     * @param data2 the data 2
     * @return the double
     */
    private static double doubleAdd(double data1, double data2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(data1));
        BigDecimal b2 = new BigDecimal(String.valueOf(data2));
        BigDecimal ret = b1.add(b2);
        return ret.doubleValue();
    }

    /**
     * Double subtract double.
     *
     * @param data1 the data 1
     * @param data2 the data 2
     * @return the double
     */
    private static double doubleSubtract(double data1, double data2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(data1));
        BigDecimal b2 = new BigDecimal(String.valueOf(data2));
        BigDecimal ret = b1.subtract(b2);
        return ret.doubleValue();
    }

    /**
     * Double multiply double.
     *
     * @param data1 the data 1
     * @param data2 the data 2
     * @return the double
     */
    private static double doubleMultiply(double data1, double data2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(data1));
        BigDecimal b2 = new BigDecimal(String.valueOf(data2));
        BigDecimal ret = b1.multiply(b2);
        return ret.doubleValue();
    }

    /**
     * Double divide double.
     *
     * @param data1 the data 1
     * @param data2 the data 2
     * @return the double
     */
    private static double doubleDivide(double data1, double data2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(data1));
        BigDecimal b2 = new BigDecimal(String.valueOf(data2));
        BigDecimal ret = b1.divide(b2, 6, RoundingMode.HALF_UP);
        return ret.doubleValue();
    }

}
