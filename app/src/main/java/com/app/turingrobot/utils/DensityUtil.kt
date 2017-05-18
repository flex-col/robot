package com.app.turingrobot.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

import java.lang.reflect.Field

/**
 * 单位转换工具类

 * @author 右右
 */
object DensityUtil {

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变

     * @param pxValue （DisplayMetrics类中属性density）
     * *
     * @return
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变

     * @param dipValue （DisplayMetrics类中属性density）
     * *
     * @return
     */
    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值，保证文字大小不变

     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * *
     * @return
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变

     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * *
     * @return
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * 获取SystemBar高度
     */
    fun systemBarHeight(context: Context): Int {
        var sbar = 0
        try {
            val c = Class.forName("com.android.internal.R\$dimen")
            val obj = c.newInstance()
            val field = c.getField("status_bar_height")
            val x = Integer.parseInt(field.get(obj).toString())
            sbar = context.resources.getDimensionPixelSize(x)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return sbar
    }

    /****
     * 获取屏幕宽度和高度
     */
    fun getScreenWidth(context: Activity): IntArray {
        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return intArrayOf(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }
}