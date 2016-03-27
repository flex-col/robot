package com.app.turingrobot.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Alpha on 2016/3/27 0:03.
 */
public class SoftInputManager {
    /**
     * 强制隐藏输入法键盘
     */
    public static void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
