package com.lego.framework.base.utils;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author yanglf
 * @description
 * @since 2019/1/21
 **/
public class DateUtils {


    public static int betweenDays(Date lastDate, Date currentDate) {
        LocalDate lastDay = lastDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDay = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long days = currentDay.toEpochDay() - lastDay.toEpochDay();
        return (int) days;
    }


}
