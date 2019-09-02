package com.lego.framework.base.utils;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * @author yanglf
 * @description
 * @since 2019/1/21
 **/
public class DateUtils {

    public static final String LONG_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SHORT_FORMAT = "yyyy-MM-dd";
    private static final String SHORT_FORMAT_MONTH = "yyyy-MM";
    private static final String SHORT_FORMAT_YEAR = "yyyy";
    public static final String ORDER_FORM_FORMAT = "yyyyMMddHHmmss";
    public static SimpleDateFormat SIMPLE_LONG_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat SIMPLE_SHORT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static Date now() {
        return new Date();
    }

    public static Date convert2Date(String dateStr, String format) {
        if (StringUtils.isNotBlank(dateStr)) {
            try {
                return new SimpleDateFormat(format).parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 计算两个日期相差天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }


    /**
     *      * 获取某年第一天日期
     *      * @param year 年份
     *      * @return Date
     *      
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     *      * 获取某年最后一天日期
     *      * @param year 年份
     *      * @return Date
     *      
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }


    /**
     * 格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {

        }
        return date;
    }

    public static String convert2String(Date date, String format) {
        if (date != null && StringUtils.isNotBlank(format)) {
            return new SimpleDateFormat(format).format(date);
        }
        return StringUtils.EMPTY;
    }

    public static Date format(Date date, SimpleDateFormat format) {
        Date result = null;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            result = format.parse(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE) + " "
                    + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param dateStr -日期字符串 <br>
     *                int len -移动天数;eg: +1(明天),-1(昨天)
     * @return String
     * @description -将日期字符串 按天 前后移动 (yyyy-MM-dd)
     * @date
     * @author
     */
    public static String dayMove(String dateStr, int len) {
        return dateMove(dateStr, len, Calendar.DATE, "yyyy-MM-dd");
    }

    public static Date dayMove(Date date, int len) {
        return dateMove(date, len, Calendar.DATE, "yyyy-MM-dd");
    }

    /**
     * @param dateStr -日期字符串 <br>
     *                int len -移动的月数;eg:+1(下个月),-1(上个月)
     * @return String
     * @description -将日期字符串 按月 前后移动 (yyyy-MM-dd)
     * @date
     * @author
     */
    public static String mouthMove(String dateStr, int len) {
        return dateMove(dateStr, len, Calendar.MONTH, "yyyy-MM-dd");
    }

    /**
     * @return String
     * @description -将日期字符串 按月 前后移动 (yyyy-MM-dd)
     * @date
     * @author
     */
    public static Date mouthMove(Date date, int len) {
        return dateMove(date, len, Calendar.MONTH, "yyyy-MM-dd");
    }

    /**
     * @param dateStr -日期字符串 <br>
     *                int len -移动的年数;eg:+1(明年),-1(去年)
     * @return String
     * @description -将日期字符串 按年 前后移动 (yyyy-MM-dd)
     * @date
     * @author
     */
    public static String yearMove(String dateStr, int len) {
        return dateMove(dateStr, len, Calendar.YEAR, "yyyy-MM-dd");
    }

    /**
     * @return String 返回日期字符串
     * @description -返回日期字符串dateStr按移动字段field,移动天数len移动后的字符串
     * @date
     * @author
     */
    public static String dateMove(String dateStr, int len, int field) {
        return dateMove(dateStr, len, field, "yyyy-MM-dd");
    }

    /**
     * @return Date 返回日期
     * @description -返回日期字符串dateStr移动小时len后的时间对象
     * @date Nov 1, 2013
     * @author
     */
    public static Date hourMove(String dateStr, int len, String format) {
        return stringToDate(dateMove(dateStr, len, Calendar.HOUR, format), format);
    }

    /**
     * @return String
     * @description -将日期字符串 按年 前后移动 (yyyy-MM-dd)
     * @date
     * @author
     */
    public static String minuteMove(String dateStr, int len) {
        return dateMove(dateStr, len, Calendar.MINUTE, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @return String
     * @description -将日期字符串 按年 前后移动 (yyyy-MM-dd)
     * @date
     * @author
     */
    public static Date minuteMove(Date date, int len) {
        return dateMove(date, len, Calendar.MINUTE);
    }

    /**
     * @return String
     * @description -返回日期字符串按移动字段field，移动天数len, 格式pattern
     * @date
     * @author
     */
    public static String dateMove(String dateStr, int len, int field, String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(stringToDate(dateStr, pattern));
        cal.add(field, len);
        return dateToString(cal.getTime(), pattern);
    }

    public static Date dateMove(Date date, int len, int field, String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, len);
        return cal.getTime();
    }

    /**
     * @return String
     * @description -返回日期字符串按移动字段field，移动天数len, 格式pattern
     * @date
     * @author
     */
    public static Date dateMove(Date date, int len, int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, len);
        return cal.getTime();
    }

    /**
     * @return String
     * @description 返回系统时间的字符串 (yyyy-MM-dd HH:mm:ss)
     * @date
     * @author
     */
    public static String getTime() {
        return dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @return String
     * @description 返回系统日期的字符串 (yyyy-MM-dd)
     * @date
     * @author
     */
    public static String getDate() {
        return dateToString(new Date(), "yyyy-MM-dd");
    }

    /**
     * @return String
     * @description -返回系统现在时间的毫秒数
     * @date Nov 5, 2012 2:45:35 PM
     * @author
     */
    public static String getTimeMilliseconds() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * @return String -日期字符串
     * @description 将日期对象date转化成格式pattern的日期字符串
     * @date
     * @author
     */
    public static String dateToString(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * @param date
     * @return String
     * @description 返回指定时间的字符串 (yyyy-MM-dd HH:mm:ss)
     * @date
     * @author
     */
    public static String timeToString(Date date) {
        return dateToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @param date 日期对象
     * @return String 日期的字符串
     * @description 返回指定日期的字符串 (yyyy-MM-dd)
     * @date
     * @author
     */
    public static String dateToString(Date date) {
        return dateToString(date, "yyyy-MM-dd");
    }

    /**
     * @param dateStr -日期字符串 <br>
     *                String pattern -转化格式
     * @return Date -转化成功返回该格式的日期对象,失败返回null
     * @description -按格式pattern将字符串dateStr转化为日期
     * @date
     * @author
     */
    public static Date stringToDate(String dateStr, String pattern) {
        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param timeStr -日期字符串
     * @return Date
     * @description -将日期字符串timeStr转化为日期对象 (yyyy-MM-dd HH:mm:ss)
     * @date
     * @author
     */
    public static Date stringToTime(String timeStr) {
        return stringToDate(timeStr, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @param dateStr -日期字符串
     * @return Date
     * @description -将日期字符串dateStr转化为日期对象 (yyyy-MM-dd)
     * @date
     * @author
     */
    public static Date stringToDate(String dateStr) {
        return stringToDate(dateStr, "yyyy-MM-dd");
    }

    /**
     * @param dateString -日期字符串 <br>
     *                   Stirng pattern -格式
     * @return String
     * @description -将日期字符按pattern串格式化
     * @date
     * @author
     */
    public static String format(String dateString, String pattern) {
        String result = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            result = dateFormat.format(dateFormat.parse(dateString));
        } catch (ParseException e) {
            return getSysTime(pattern);
        }
        return result;
    }

    /**
     * @param dateString
     * @return String
     * @description-将日期字符按"yyyy-MM-dd"串格式化
     * @author
     * @date :
     */
    public static String format(String dateString) {
        try {
            return format(dateString, "yyyy-MM-dd");
        } catch (Exception e) {
            return getSysTime("yyyy-MM-dd");
        }
    }

    /**
     * 取得系统时间
     *
     * @param pattern 按指定格式输出如'yyyy-MM-dd'
     * @return
     */
    public static String getSysTime(String pattern) {
        if (pattern == null || "".equals(pattern)) {
            pattern = "yyyy-MM-dd";
        }
        Properties p = System.getProperties();
        p.setProperty("user.timezone", "GMT+8");
        String tt = new SimpleDateFormat(pattern).format(new Date());
        return tt;
    }


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


    public static LocalDateTime string2DateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }


    public static LocalDateTime date2LocalDateTime(Date date) {
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(date.toInstant(), zoneId);
    }


    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }


}
