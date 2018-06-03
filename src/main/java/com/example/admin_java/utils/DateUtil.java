package com.example.admin_java.utils;

import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 *
 * 时间格式互转工具类
 *
 * @Author: Think
 * @Date: 2018/5/30
 * @Time: 15:12
 */
public class DateUtil {

    public static String timestampToDate(long timestamp) {
        Timestamp ts = new Timestamp(timestamp);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = "";
        try {
            date = sdf.format(ts);
        } catch (Exception e) {
            throw new RuntimeException("转换时间错误");
        }
        return date;
    }

    /**
     *
     * 时间格式转成时间戳
     *
     * @param dateStr
     * @return
     */
    public static String dateToTimestamp(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = null;
        Long time = null;
        try {
            date = sdf.parse(dateStr);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(time);
    }

    /**
     *
     * 时间戳转成CST格式
     *
     * @param timestamp
     * @return
     */
    public static String timestampToCST(String timestamp) {
        SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        String d = format.format(Long.valueOf(timestamp));
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(date);
    }

    public static int timeToSecond(String time) {
        int second = 0;
        String secondStr = "";
        if (time.contains("时") && time.contains("分") && time.contains("秒")) {
            String[] hourArr = time.split("时");
            String minuteStr = hourArr[1];
            second += Integer.valueOf(hourArr[0]) * 3600;
            String[] minuteArr = minuteStr.split("分");
            secondStr = minuteArr[1];
            second += Integer.valueOf(minuteArr[0]) * 60;
            String[] secondArr = secondStr.split("秒");
            second += Integer.valueOf(secondArr[0]);
        } else if (time.contains("分") && time.contains("秒")) {
            String[] minuteArr = time.split("分");
            secondStr = minuteArr[1];
            second += Integer.valueOf(minuteArr[0]) * 60;
            String[] secondArr = secondStr.split("秒");
            second += Integer.valueOf(secondArr[0]);
        } else if (time.contains("秒")) {
            String[] secondArr = time.split("秒");
            second += Integer.valueOf(secondArr[0]);
        }
        return second;
    }

}
