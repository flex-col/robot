package com.app.turingrobot.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间转换工具类
 *
 * @author yoyo
 */
public class TimeUtil {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(time));
    }

    /**
     * yyyy.MM.dd
     */
    public static String getDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(new Date(time));
    }


    /**
     * yyyy-MM-dd
     */
    public static String getDate(long time, int type) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(time));
    }

    /**
     * HH:mm
     */
    public static String getMonthAndDay(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(new Date(time));
    }

    /**
     * 星期几
     */
    public static String getWeek(long time) {
        String result = "星期";
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                result += "天";
                break;
            case 2:
                result += "一";
                break;
            case 3:
                result += "二";
                break;
            case 4:
                result += "三";
                break;
            case 5:
                result += "四";
                break;
            case 6:
                result += "五";
                break;
            case 7:
                result += "六";
                break;
        }

        return result;
    }

    /**
     * MM月dd日
     */
    public static String getMonthDay(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        return format.format(new Date(time));
    }

    /**
     * HH:mm
     */
    public static String getHourMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 将中国格式的时间转化为毫秒
     *
     * @param fString
     * @return
     */
    public static long getTime(String fString) throws ParseException {
        long mTime = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy" + "-" + "MM" + "-" + "dd", Locale.CHINA);
        mTime = format.parse(fString).getTime();
        return mTime;
    }


    /**
     * 换算新闻时间
     *
     * @param timesamp 时间戳
     * @return 换算后显示的时间
     */
    public static String getNewsTime(long timesamp) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(timesamp);
        long temp = today.getTime() - otherDay.getTime();

        int excursionMin = (int) (temp / (1000 * 60));

        if (excursionMin <= 5 * 60) {
            result = "刚刚更新";
        } else if (excursionMin > (5 * 60) && excursionMin <= (10 * 60)) {
            result = "5小时前";
        } else if (excursionMin > (10 * 60) && excursionMin <= (24 * 60)) {
            result = "10小时前";
        } else if (excursionMin > (24 * 60)) {
            result = "1天前";
        }


        return result;
    }


    /**
     * 换算消息时间
     *
     * @param timesamp 时间戳
     * @return 换算后显示的时间
     */
    public static String getMsgTime(long timesamp) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(timesamp);
        int temp = Integer.parseInt(sdf.format(today))
                - Integer.parseInt(sdf.format(otherDay));

        switch (temp) {
            case 0:
                result = getHourMin(timesamp);
                break;
            case 1:
                result = "昨天 " + getHourMin(timesamp);
                break;
            case 2:
                result = "前天 " + getHourMin(timesamp);
                break;

            default:
                result = getMonthDay(timesamp);
                break;
        }


        return result;
    }

    /****
     * 判断是否是本月
     *
     * @param dateStr
     * @return
     */
    public static String isNowMonth(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yy年-MM月", Locale.CHINA);
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM");
            Date date = dateFormat1.parse(dateStr);
            if (date.before(new Date()) && (date.getMonth() < new Date().getMonth())) {
                return dateFormat.format(date);
            } else {
                return "本月";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
