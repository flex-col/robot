package com.app.turingrobot.core;

import android.app.Application;

import com.app.turingrobot.BuildConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
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


        PushAgent agent = PushAgent.getInstance(this);
        agent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {

                KLog.i("友盟推送注册成功");
            }

            @Override
            public void onFailure(String s, String s1) {
                KLog.i(s + " " + s1);
            }
        });

    }


    public static RobotApplication getInstance() {
        return instance;
    }

    /**
     * 请求ApiService
     *
     * @return
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
     *
     * @return
     */
    private Call.Factory getOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }


}
