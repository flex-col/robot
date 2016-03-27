
package com.app.turingrobot.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

import com.app.turingrobot.R;
import com.app.turingrobot.widget.GlideCircleTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.socks.library.KLog;

/**
 * Description：GlideUtils
 * Created by：CaMnter
 * Time：2016-01-04 22:19
 */
public class GlideUtils {

    private static final String TAG = "GlideUtils";

    /**
     * glide加载图片
     *
     * @param view view
     * @param url  url
     */
    public static void display(ImageView view, String url) {
        displayUrl(view, url, R.mipmap.ic_launcher);
    }


    /**
     * glide加载图片
     *
     * @param view         view
     * @param url          url
     * @param defaultImage defaultImage
     */
    private static void displayUrl(final ImageView view, String url, @DrawableRes int defaultImage) {
        if (view == null) {
            KLog.e("GlideUtils -> display -> imageView is null");
            return;
        }
        Context context = view.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(defaultImage)
                    .crossFade()
                    .error(R.mipmap.img_default_gray)
                    .into(view)
                    .getSize((width, height) -> {
                        if (!view.isShown()) {
                            view.setVisibility(View.VISIBLE);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayNative(final ImageView view, @DrawableRes int resId) {
        if (view == null) {
            KLog.e("GlideUtils -> display -> imageView is null");
            return;
        }
        Context context = view.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                    .load(resId)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .placeholder(R.mipmap.img_default_gray)
                    .error(R.mipmap.img_default_gray)
                    .into(view)
                    .getSize((width, height) -> {
                        if (!view.isShown()) {
                            view.setVisibility(View.VISIBLE);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void displayCircleHeader(ImageView view, String url) {
        if (view == null) {
            KLog.e("GlideUtils -> display -> imageView is null");
            return;
        }
        Context context = view.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.mipmap.img_default_gray)
                    .error(R.mipmap.img_default_gray)
                    .bitmapTransform(new GlideCircleTransform(context))
                    .crossFade()
                    .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
