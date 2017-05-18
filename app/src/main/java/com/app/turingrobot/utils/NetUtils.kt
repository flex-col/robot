package com.app.turingrobot.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by Yili270 on 2015/9/16.
 * Email : yili270@163.com
 * Description : 判断网络是否可用的工具类
 */
class NetUtils {

    /**
     * 判断WIFI网络是否可用

     * @param context
     * *
     * @return
     */

    fun isWifiConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable
            }
        }
        return false
    }

    /**
     * 判断MOBILE网络是否可用

     * @param context
     * *
     * @return
     */
    fun isMobileConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable
            }
        }
        return false
    }

    companion object {

        /**
         * 判断是否有网络连接

         * @param context
         * *
         * @return
         */
        fun isNetworkConnected(context: Context?): Boolean {
            if (context != null) {
                val mConnectivityManager = context
                        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val mNetworkInfo = mConnectivityManager.activeNetworkInfo
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable
                }
            }
            return false
        }

        /**
         * 获取当前网络连接的类型信息

         * @param context
         * *
         * @return
         */
        fun getConnectedType(context: Context?): Int {
            if (context != null) {
                val mConnectivityManager = context
                        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val mNetworkInfo = mConnectivityManager.activeNetworkInfo
                if (mNetworkInfo != null && mNetworkInfo.isAvailable) {
                    return mNetworkInfo.type
                }
            }
            return -1
        }


    }
}
