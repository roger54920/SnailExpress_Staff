package com.example.me_jie.snailexpress_staff.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by me-jie on 2017/5/10.
 * 获取时间
 */

public class AcquisitionTimeUtil {
   static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//时间格式

    /**
     * date类型转换为String类型
     * @param data
     * @param formatType
     * @return
     */
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     * long类型转换为String类型
     * @param currentTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    /**
     * string类型转换为date类型
     * @param strTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * long转换为Date类型
     * @param currentTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }
    /**
     * String类型转换为long类型
     * @param strTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    /**
     * date类型转换为long类型
     * @param date
     * @return
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }
    /**
     * 获取当前日期
     * @return
     */
    public static String getCurrentDate(){
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String current = sdf.format(curDate);
        return current;
    }
    /**
     * 获得指定日期的后一天
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay){
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = sdf.parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day+1);
        String dayAfter=sdf.format(c.getTime());
        return dayAfter;
    }

    /**
     * 获得指定日期的前一天
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayBefore(String specifiedDay){
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = sdf.parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date); int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day-1);
        String dayBefore=sdf.format(c.getTime());
        return dayBefore;
    }
}
