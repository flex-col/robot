package com.app.turingrobot.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * 时间转换工具类

 * @author yoyo
 */
object TimeUtil {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    fun getTime(time: Long): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return format.format(Date(time))
    }

    /**
     * yyyy.MM.dd
     */
    fun getDate(time: Long): String {
        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.format(Date(time))
    }


    /**
     * yyyy-MM-dd
     */
    fun getDate(time: Long, type: Int): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(Date(time))
    }

    /**
     * HH:mm
     */
    fun getMonthAndDay(time: Long): String {
        val format = SimpleDateFormat("MM-dd")
        return format.format(Date(time))
    }

    /**
     * 星期几
     */
    fun getWeek(time: Long): String {
        var result = "星期"
        val c = Calendar.getInstance()
        c.timeInMillis = time
        when (c.get(Calendar.DAY_OF_WEEK)) {
            1 -> result += "天"
            2 -> result += "一"
            3 -> result += "二"
            4 -> result += "三"
            5 -> result += "四"
            6 -> result += "五"
            7 -> result += "六"
        }

        return result
    }

    /**
     * MM月dd日
     */
    fun getMonthDay(time: Long): String {
        val format = SimpleDateFormat("MM月dd日")
        return format.format(Date(time))
    }

    /**
     * HH:mm
     */
    fun getHourMin(time: Long): String {
        val format = SimpleDateFormat("HH:mm")
        return format.format(Date(time))
    }

    /**
     * 将中国格式的时间转化为毫秒

     * @param fString
     * *
     * @return
     */
    @Throws(ParseException::class)
    fun getTime(fString: String): Long {
        var mTime: Long = 0
        val format = SimpleDateFormat("yyyy" + "-" + "MM" + "-" + "dd",
                Locale.CHINA)
        mTime = format.parse(fString).time
        return mTime
    }


    /**
     * 换算新闻时间

     * @param timesamp 时间戳
     * *
     * @return 换算后显示的时间
     */
    fun getNewsTime(timesamp: Long): String {
        var result = ""
        val sdf = SimpleDateFormat("dd")
        val today = Date(System.currentTimeMillis())
        val otherDay = Date(timesamp)
        val temp = today.time - otherDay.time

        val excursionMin = (temp / (1000 * 60)).toInt()

        if (excursionMin <= 5 * 60) {
            result = "刚刚更新"
        } else if (excursionMin > 5 * 60 && excursionMin <= 10 * 60) {
            result = "5小时前"
        } else if (excursionMin > 10 * 60 && excursionMin <= 24 * 60) {
            result = "10小时前"
        } else if (excursionMin > 24 * 60) {
            result = "1天前"
        }


        return result
    }


    /**
     * 换算消息时间

     * @param timesamp 时间戳
     * *
     * @return 换算后显示的时间
     */
    fun getMsgTime(timesamp: Long): String {
        var result = ""
        val sdf = SimpleDateFormat("dd")
        val today = Date(System.currentTimeMillis())
        val otherDay = Date(timesamp)
        val temp = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay))

        when (temp) {
            0 -> result = getHourMin(timesamp)
            1 -> result = "昨天 " + getHourMin(timesamp)
            2 -> result = "前天 " + getHourMin(timesamp)

            else -> result = getMonthDay(timesamp)
        }


        return result
    }

    /****
     * 判断是否是本月

     * @param dateStr
     * *
     * @return
     */
    fun isNowMonth(dateStr: String): String {
        try {
            val dateFormat = SimpleDateFormat("yy年-MM月", Locale.CHINA)
            val dateFormat1 = SimpleDateFormat("yyyy-MM")
            val date = dateFormat1.parse(dateStr)
            if (date.before(Date()) && date.month < Date().month) {
                return dateFormat.format(date)
            } else {
                return "本月"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

    }
}
