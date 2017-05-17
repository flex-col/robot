package com.app.turingrobot.core.dagger.module;

import com.app.turingrobot.common.Constants;
import com.app.turingrobot.core.App;
import com.app.turingrobot.core.RobotService;
import com.app.turingrobot.helper.SpfHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Created by sword on 2017/3/27.
 */
@Module
public class AppModule {

    private App mApp;

    public AppModule(App application) {
        this.mApp = application;
    }

    @Provides
    @Singleton
    App provideApp() {
        return mApp;
    }


    @Provides
    @Singleton
    SpfHelper provideSpHelper() {
        return new SpfHelper(mApp);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        return gson;
    }

    @Provides
    @Singleton
    OkHttpClient providedOkhttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    @Provides
    @Singleton
    RobotService providedService(OkHttpClient okHttpClient, Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(RobotService.class);
    }
}
