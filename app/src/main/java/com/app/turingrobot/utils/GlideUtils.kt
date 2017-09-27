package com.app.turingrobot.utils

import android.app.Activity
import android.support.annotation.DrawableRes
import android.view.View
import android.widget.ImageView
import com.app.turingrobot.R
import com.app.turingrobot.app.GlideApp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.socks.library.KLog

/**
 * Description：GlideUtils
 * Created by：CaMnter
 * Time：2016-01-04 22:19
 */
object GlideUtils {

    private val TAG = "GlideUtils"

    /**
     * glide加载图片

     * @param view view
     * *
     * @param url  url
     */
    fun display(view: ImageView, url: String) {
        displayUrl(view, url, R.mipmap.ic_launcher)
    }


    /**
     * glide加载图片

     * @param view         view
     * *
     * @param url          url
     * *
     * @param defaultImage defaultImage
     */
    private fun displayUrl(view: ImageView?, url: String,
                           @DrawableRes defaultImage: Int) {
        if (view == null) {
            KLog.e("GlideUtils -> display -> imageView is null")
            return
        }
        val context = view.context
        if (context is Activity) {
            if (context.isFinishing) {
                return
            }
        }

        try {
            GlideApp.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(defaultImage)
                    .error(R.mipmap.img_default_gray)
                    .into(view)
                    .getSize { width, height ->
                        if (!view.isShown) {
                            view.visibility = View.VISIBLE
                        }
                    }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun displayNative(view: ImageView?, @DrawableRes resId: Int) {
        if (view == null) {
            KLog.e("GlideUtils -> display -> imageView is null")
            return
        }
        val context = view.context
        if (context is Activity) {
            if (context.isFinishing) {
                return
            }
        }

        try {
            GlideApp.with(context)
                    .load(resId)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.img_default_gray)
                    .error(R.mipmap.img_default_gray)
                    .into(view)
                    .getSize { width, height ->
                        if (!view.isShown) {
                            view.visibility = View.VISIBLE
                        }
                    }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun displayCircleHeader(view: ImageView?, url: String) {
        if (view == null) {
            KLog.e("GlideUtils -> display -> imageView is null")
            return
        }
        val context = view.context
        if (context is Activity) {
            if (context.isFinishing) {
                return
            }
        }

        try {
            GlideApp.with(context)
                    .load(url)
                    .centerCrop()
                    .circleCrop()
                    .placeholder(R.mipmap.img_default_gray)
                    .error(R.mipmap.img_default_gray)
                    .into(view)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
