package com.app.turingrobot.utils;

import android.app.Application;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


public final class TUtil {

    private TUtil() {
        //no instance
    }

    public static boolean isShow = true;

    private static Application instanceContext;

    private static Toast toast = null;

    public static void initialize(Application context) {
        instanceContext = context;
    }

    public static void show(String content) {
        checkNotNull();
        if (toast == null) {
            toast = Toast.makeText(instanceContext, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public static void show(int content) {
        checkNotNull();
        if (toast == null) {
            toast = Toast.makeText(instanceContext, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    private static void checkNotNull() {
        checkNotNull(instanceContext);
    }

    static <T> T checkNotNull(T object) {
        if (object == null) {
            throw new NullPointerException("this object is null");
        }
        return object;
    }


    public static void showCus(Context context, String content, @LayoutRes int layout) {
        if (null != context && isShow) {
            View contentView = LayoutInflater.from(context).inflate(layout, null);
            Toast toast = new Toast(context);
            toast.setView(contentView);
            toast.show();
        }
    }

}