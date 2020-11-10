package com.jingpai.pos.customer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Create by liujinheng
 * date 2020/5/29
 * function
 */
public class DateUtil {
    private static final SimpleDateFormat FORMAT_MMDD = new SimpleDateFormat(
            "MM月dd日");
    public static final SimpleDateFormat FORMAT_YYMMDD2 = new SimpleDateFormat(
            "yyyy-MM-dd");
    private static final SimpleDateFormat FORMAT_YYMMDDHHMM2 = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat FORMAT_YYMMDDHHMM5 = new SimpleDateFormat(
            "MM-dd HH:mm");
    private static final SimpleDateFormat FORMAT_YYMMDDHHMM3 = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat FORMAT_YYMMDDHHMM4 = new SimpleDateFormat(
            "yyyyMMddHHmmss");
    private static final SimpleDateFormat FORMAT_XC_YYMMDDHHMM = new SimpleDateFormat(
            "yyyy/MM/dd HHmmss");

    private static final String TODAY = "今天";
    private static final String YESTERDAY = "昨天";

    private static final String DATE_YEAR = "年";
    private static final String DATE_MONTH = "月";
    private static final String DATE_DAY = "日";

    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;
    /**
     * 获取当前小时是否在晚上9：00-22:00之间
     * @return
     */
    public static boolean isTipHour() {
        Date d = new Date();
        int hours = d.getHours();
        return hours > 9 && hours < 22;
    }

    public static String getToday(){
        Date curDate =  new Date(System.currentTimeMillis());
        return FORMAT_YYMMDD2.format(curDate);
    }
    public static boolean beforeDay(String day){
        Date curDate =  new Date(System.currentTimeMillis());
        Date someDate= null;
        try {
            someDate = FORMAT_YYMMDD2.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (curDate.before(someDate)){
            return true;
        }
        return false;
    }
    public static boolean afterDay(String day){
        Date curDate =  new Date(System.currentTimeMillis());
        Date someDate= null;
        try {
            someDate = FORMAT_YYMMDD2.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (curDate.after(someDate)){
            return true;
        }
        return false;
    }
    /**
     * 根据日期获取毫秒数 仅针对：yyyy-MM-dd HH:mm:ss格式
     * @param time
     * @return
     */
    public static final long getTime(String time) {
        //先把字符串转成Date类型
        //此处会抛异常
        Date date = null;
        try {
            date = FORMAT_YYMMDDHHMM3.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        //获取毫秒数
        return date.getTime();
    }

    public static final String formatStrYYMMDD(String time) {
        SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat sdf2= new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);


        String format = null;
        try {
            format = FORMAT_YYMMDD2.format(FORMAT_YYMMDDHHMM2.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return format;
    }

    /**
     * 将时间字符串格式化成毫秒
     * @param str
     */
    public static final long formatStrToTime(String str) {
        try {
            return FORMAT_YYMMDDHHMM2.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将时间字符串格式化成毫秒
     * @param str
     */
    public static final long formatStrToTime2(String str) {
        try {
            return FORMAT_YYMMDDHHMM3.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将毫秒格式化成时间字符串(2008年8月)
     */
    public static final String formatTimeToYearMonth(String time) {
        try {
            return FORMAT_MMDD.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将毫秒格式化成时间字符串(2008-8-08 10:02)
     */
    public static final String formatTimeToStr(long time) {
        try {
            return FORMAT_YYMMDDHHMM2.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将毫秒格式化成时间字符串(2008-8-08 10:02:56)
     */
    public static final String formatTimeToStr1(long time) {
        try {
            return FORMAT_YYMMDDHHMM3.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将毫秒格式化成时间字符串(8-08 10:02:56)
     */
    public static final String formatTimeToStr5(long time) {
        try {
            return FORMAT_YYMMDDHHMM5.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将毫秒格式化成时间字符串(20080808100256)
     *
     */
    public static final String formatTimeToStr4(long time) {
        try {
            return FORMAT_YYMMDDHHMM4.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将毫秒格式化成时间字符串(8/08)
     */
    public static final String formatTimeOnlyDay(long time) {
        try {
            String date = FORMAT_YYMMDD2.format(new Date(time));
            // 创建 Calendar 对象
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            String[] dates = date.split("-");
            if (dates[1].startsWith("0")) {
                dates[1] = dates[1].substring(1);
            }
            if (dates[2].startsWith("0")) {
                dates[2] = dates[2].substring(1);
            }
            if (Integer.parseInt(dates[0]) == year) {
                return dates[1] + DATE_MONTH + dates[2] + DATE_DAY;
            }
            return dates[0] + DATE_YEAR + dates[1] + DATE_MONTH + dates[2] + DATE_DAY;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将毫秒格式化成时间字符串(8/08)
     */
    public static final String getTimeYMD(long time){
        String data="";
        try {
            data=	FORMAT_YYMMDD2.format(new Date(time));
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;

    }

}
