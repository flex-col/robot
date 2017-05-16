package com.app.turingrobot.core;

import android.app.Application;

import com.app.turingrobot.BuildConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/*
 * Created by Alpha on 2016/3/26 21:49.
 */
public class RobotApplication extends Application {

    private static RobotApi.ApiService apiService;

    private static RobotApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //KLog初始化
        KLog.init(BuildConfig.DEBUG);

        LeakCanary.install(this);

        //注册Glide请求为OkHttp
        Glide.get(this).register(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(getOkHttpClient()));

        Bugly.init(getApplicationContext(), "408e519c80", BuildConfig.DEBUG);

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

        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng
        // .com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = BuildConfig.DEBUG;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);

    }

    static {
        PlatformConfig.setWeixin("wx43c991b376bb2895", "3baf1193c85774b3fd9d18447d76cab0");
    }


    public static RobotApplication getInstance() {
        return instance;
    }

    /**
     * 请求ApiService
     */
    public RobotApi.ApiService getService() {
        if (apiService == null) {
            synchronized (RobotApi.ApiService.class) {
                if (apiService == null) {
                    apiService = RobotApi.getInstance().getService();
                }
            }
        }
        return apiService;
    }

    /**
     * OkHttp
     */
    private Call.Factory getOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }


}
