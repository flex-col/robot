package com.app.turingrobot.core;

import android.app.Application;

import com.app.turingrobot.BuildConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;

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
        KLog.init(BuildConfig.LOG_DEBUG);

        LeakCanary.install(this);

        //注册Glide请求为OkHttp
        Glide.get(this).register(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(getOkHttpClient()));

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
