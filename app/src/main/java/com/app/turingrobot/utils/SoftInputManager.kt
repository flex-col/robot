package com.app.turingrobot.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Alpha on 2016/3/27 0:03.
 */
object SoftInputManager {
    /**
     * 强制隐藏输入法键盘
     */
    fun hideInput(context: Context, view: View) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
