package com.app.turingrobot.extra

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Base64
import com.app.turingrobot.app.App
import com.app.turingrobot.utils.TUtil
import com.socks.library.KLog
import java.nio.charset.Charset

/**
 * 项目全局扩展方法
 * @author sword on 2017/4/18.
 */
/*
 * 字符串添加扩展方法，直接Toast
 */
fun String.toast() = TUtil.show(this)

/*
 * 字符串添加扩展方法，直接log
 */
fun String.log() = KLog.i("KLog", this)

/*
 * 全局添加color方法
 */
fun Activity.colorRes(id: Int): Int = ContextCompat.getColor(App.instance, id)

fun Fragment.colorRes(id: Int): Int = ContextCompat.getColor(App.instance, id)

fun android.app.Fragment.colorRes(id: Int): Int = ContextCompat.getColor(App.instance, id)

fun Context.colorRes(id: Int): Int = ContextCompat.getColor(App.instance, id)

fun String.toBase64(): String = String(Base64.encode(this.toByteArray(), Base64.URL_SAFE), Charset.forName("UTF-8")).replace("=", "").replace("\n", "")
