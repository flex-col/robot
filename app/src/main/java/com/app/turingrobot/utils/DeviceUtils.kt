package com.app.turingrobot.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

/**
 * Created by Alpha on 2016/3/27 21:45.
 */
object DeviceUtils {

    fun getVersion(context: Context): String {
        val packageManager = context.packageManager
        try {
            val info = packageManager.getPackageInfo(context.packageName, 0)
            return info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return ""
        }

    }
}
