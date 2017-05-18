package com.app.turingrobot.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.app.turingrobot.BuildConfig
import com.app.turingrobot.core.dagger.component.AppComponent
import com.app.turingrobot.core.dagger.component.DaggerAppComponent
import com.app.turingrobot.core.dagger.module.AppModule
import com.app.turingrobot.entity.user.User
import com.app.turingrobot.helper.SpfHelper
import com.app.turingrobot.utils.TUtil
import com.socks.library.KLog
import com.squareup.leakcanary.LeakCanary
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.MsgConstant
import com.umeng.message.PushAgent
import com.umeng.socialize.Config
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.common.QueuedWork

class App : Application() {


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
        instance = this
        KLog.init(BuildConfig.DEBUG)
        TUtil.initialize(this)

        val agent = PushAgent.getInstance(this)
        agent.register(object : IUmengRegisterCallback {
            override fun onSuccess(s: String) {
                KLog.i("友盟推送注册成功 device_token ------> " + s)
            }

            override fun onFailure(s: String, s1: String) {
                KLog.i(s + " " + s1)
            }
        })

        agent.displayNotificationNumber = 0
        agent.notificationPlaySound = MsgConstant.NOTIFICATION_PLAY_SERVER //声音
        agent.notificationPlayLights = MsgConstant.NOTIFICATION_PLAY_SERVER //呼吸灯
        agent.notificationPlayVibrate = MsgConstant.NOTIFICATION_PLAY_SERVER //振动

        Config.DEBUG = BuildConfig.DEBUG
        QueuedWork.isUseThreadPool = false
        UMShareAPI.get(this)

        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    companion object {

        lateinit var instance: App

        lateinit var component: AppComponent

        var mUser: User? = null

        init {
            PlatformConfig.setQQZone("1106170056", "7Zc3yWUx3CxH1Jtf")
        }

        fun getUser(): User? {
            if (mUser == null) {
                mUser = SpfHelper.getByClass(User::class.java)
            }
            return mUser
        }

        fun get(): App {
            return instance!!
        }

        fun setUserBean(user: User) {
            mUser = user
        }
    }


}
