package com.app.turingrobot.core.dagger.module

import com.app.turingrobot.app.App
import com.app.turingrobot.core.RobotService
import com.app.turingrobot.helper.SpfHelper
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/*
 * Created by sword on 2017/3/27.
 */
@Module
class AppModule(private val mApp: App) {

    @Provides
    @Singleton
    fun provideSpHelper(): SpfHelper {
        return SpfHelper(mApp)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create()
        return gson
    }

    @Provides
    @Singleton
    fun providedOkhttp(): OkHttpClient {
        Stetho.initializeWithDefaults(mApp)
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(StethoInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
        return okHttpClient
    }

    @Provides
    @Singleton
    fun providedService(okHttpClient: OkHttpClient, gson: Gson): RobotService {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://www.tuling123.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(RobotService::class.java)
    }
}
