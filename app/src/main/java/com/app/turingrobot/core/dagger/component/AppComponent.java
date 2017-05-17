package com.app.turingrobot.core.dagger.component;


import com.app.turingrobot.core.App;
import com.app.turingrobot.core.RobotService;
import com.app.turingrobot.core.dagger.module.AppModule;
import com.app.turingrobot.helper.SpfHelper;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/*
 * Created by sword on 2017/3/27.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    Gson getGson();

    SpfHelper getSpfHelper();

    App getApp();

    OkHttpClient getOkhttp();

    RobotService getService();

}
