package com.lego.framework.base.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author yanglf
 * @description
 * @since 2019/1/21
 **/
public class DateUtils {


    /**
     * 计算两个时间段相差天数
     *
     * @param lastDate
     * @param currentDate
     * @return
     */
    public static int betweenDays(Date lastDate, Date currentDate) {
        LocalDate lastDay = lastDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDay = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long days = currentDay.toEpochDay() - lastDay.toEpochDay();
        return (int) days;
    }


    /**
     * 格式化时间
     *
     * @param localDateTime
     * @param format
     * @return
     */
    public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }


    /**
     * 时间戳  转换  LocalDateTime
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }


    /**
     * LocalDateTime   转  时间戳
     *
     * @param localDateTime
     * @return
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }


    public static LocalDateTime parseStringToDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }


    public static LocalDateTime dateToLocalDateTime(Date date) {
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(date.toInstant(), zoneId);
    }


    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }


}
