package com.app.turingrobot.ui.core

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.app.turingrobot.R
import com.app.turingrobot.core.RobotService
import com.app.turingrobot.app.App
import com.app.turingrobot.helper.SpfHelper
import com.app.turingrobot.utils.StatusBarUtil
import com.google.gson.Gson
import com.umeng.analytics.MobclickAgent
import com.umeng.message.PushAgent

import javax.inject.Inject

import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient

/**
 * Created by Alpha on 2016/3/26 20:56.
 */
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: RobotService

    @Inject
    lateinit var mSpf: SpfHelper

    @Inject
    lateinit var okHttp: OkHttpClient

    @Inject
    lateinit var gson: Gson

    val mDisp = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()
        inject()
    }

    private fun inject() {
        App.component.inject(this)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setStatusBarColor()
    }

    /**
     * 设置状态栏颜色
     */
    protected fun setStatusBarColor() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark))
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    override fun onDestroy() {
        mDisp.dispose()
        super.onDestroy()
    }

    companion object {

        fun start(th: Context, activity: Class<*>, bundle: Bundle?) {
            val intent = Intent(th, activity)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            th.startActivity(intent)
        }
    }
}
