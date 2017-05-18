package kernel

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.app.turingrobot.app.App
import com.app.turingrobot.utils.TUtil
import com.socks.library.KLog

/**
 * 项目全局扩展方法
 * @author sword on 2017/4/18.
 */
/*
 * 字符串添加扩展方法，直接Toast
 */
internal fun String.toast() = TUtil.show(this)

/*
 * 字符串添加扩展方法，直接log
 */
internal fun String.log() = KLog.i("KLog", this)

/*
 * 全局添加color方法
 */
fun Activity.colorRes(id: Int): Int = ContextCompat.getColor(App.instance, id)

fun Fragment.colorRes(id: Int): Int = ContextCompat.getColor(App.instance, id)

fun android.app.Fragment.colorRes(id: Int): Int = ContextCompat.getColor(App.instance, id)

fun Context.colorRes(id: Int): Int = ContextCompat.getColor(App.instance, id)
