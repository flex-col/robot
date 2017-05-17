package com.app.turingrobot.core;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.app.turingrobot.BuildConfig;
import com.app.turingrobot.core.dagger.component.AppComponent;
import com.app.turingrobot.core.dagger.component.DaggerAppComponent;
import com.app.turingrobot.core.dagger.module.AppModule;
import com.app.turingrobot.entity.user.User;
import com.app.turingrobot.helper.SpfHelper;
import com.app.turingrobot.utils.TUtil;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

/*
 * Created by Alpha on 2016/3/26 21:49.
 */
public class App extends Application {

    public static App instance;

    public static AppComponent mComponent;

    private static User mUser;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //KLog初始化
        KLog.init(BuildConfig.DEBUG);

        LeakCanary.install(this);

        TUtil.initialize(this);

        PushAgent agent = PushAgent.getInstance(this);
        agent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {

                KLog.i("友盟推送注册成功 device_token ------> " + s);
            }

            @Override
            public void onFailure(String s, String s1) {
                KLog.i(s + " " + s1);
            }
        });
        agent.setDisplayNotificationNumber(0);
        agent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); //声音
        agent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SERVER);//呼吸灯
        agent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SERVER);//振动


        Config.DEBUG = BuildConfig.DEBUG;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);

        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

    }

    public static User getUser() {
        if (mUser == null) {
            mUser = SpfHelper.getByClass(User.class);
        }
        return mUser;
    }

    public static void setUserBean(User userBean) {
        App.mUser = userBean;
    }

    public static AppComponent getComponent() {
        return mComponent;
    }

    static {
        PlatformConfig.setQQZone("1106170056", "7Zc3yWUx3CxH1Jtf");
    }


    public static App get() {
        return instance;
    }


}
