package com.app.turingrobot.helper

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.app.turingrobot.app.App
import com.google.gson.Gson

/**
 * SharedPreferences 管理类
 * Created by Alpha on 2016/4/26.
 */
class SpfHelper(context: Context) {

    private val sp: SharedPreferences = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor?

    init {
        editor = sp.edit()
    }

    fun saveNoCommit(key: String, value: Any): SpfHelper {
        checkNotNull(key)
        checkNotNull(value)
        if (value is String) {
            editor!!.putString(key, if (TextUtils.isEmpty(value)) "" else value)
        } else if (value is Int) {
            editor!!.putInt(key, value)
        } else if (value is Boolean) {
            editor!!.putBoolean(key, value)
        }
        return this
    }

    fun remove(key: String) {
        editor!!.remove(key).commit()
    }

    fun apply() {
        editor?.apply()
    }

    fun commit() {
        editor?.commit()
    }

    @JvmOverloads fun getString(key: String, defaule: String = ""): String {
        return sp.getString(key, defaule)
    }

    fun getInt(key: String): Int {
        return sp.getInt(key, 0)
    }

    fun getBoolean(key: String): Boolean? {
        return sp.getBoolean(key, false)
    }

    /**
     * 泛型类型
     */
    @SuppressWarnings("unchecked")
    fun <T> getValue(strName: String): T? {
        checkNotNull(strName)
        val values = sp.all
        if (values[strName] != null) {
            return values[strName] as T
        } else {
            return null
        }
    }

    companion object {

        const val SP_FILE_NAME = "Sp_file"

        val mGson = Gson()

        val mSpf = SpfHelper(App.instance!!)

        /**
         * 保存
         */
        @JvmStatic
        fun <T : Any> toJsonSave(value: T) {
            val key = checkNotNull(value)::class.java.simpleName
            mSpf.saveNoCommit(key, mGson.toJson(value)).commit()
        }

        /**
         * 清除
         */
        @JvmStatic
        fun <T : Any> clear(clz: Class<T>) {
            val key = checkNotNull(clz).simpleName
            mSpf.remove(key)
        }

        /**
         * 获取
         */
        @JvmStatic
        fun <T : Any?> getByClass(clz: Class<T>): T? {
            val key = checkNotNull(clz).simpleName
            val value = mSpf.getString(key)
            if (TextUtils.isEmpty(value)) {
                return null
            } else {
                return mGson.fromJson(value, clz)
            }
        }

    }

}
