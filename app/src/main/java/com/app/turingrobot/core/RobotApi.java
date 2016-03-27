package com.app.turingrobot.core;


import com.app.turingrobot.common.Constants;
import com.app.turingrobot.entity.CoreEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Retrofit API请求管理服务
 * <p>
 * Created by 右右 on 2015/4/29.
 */
public class RobotApi {
    private static final String endpoint = Constants.BASE_URL;


    private static final String openApi = "openapi/api";


    private Retrofit retrofit;
    private ApiService service;

    private static RobotApi mRobotApi;

    private RobotApi() {

    }


    /**
     * 获取单例
     */
    public static RobotApi getInstance() {
        if (mRobotApi == null) {
            synchronized (RobotApi.class) {
                if (mRobotApi == null) {
                    mRobotApi = new RobotApi();
                }
            }
        }
        return mRobotApi;
    }


    /***
     * 单例 全局使用一个实例
     *
     * @return
     */
    public ApiService getService() {
        if (retrofit == null) {

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(endpoint)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        if (service == null) {
            synchronized (ApiService.class) {
                if (service == null) {
                    service = retrofit.create(ApiService.class);
                }
            }
        }
        return service;
    }


    /**
     * 请求接口
     */
    public interface ApiService {
        /**
         * 请求文字类型
         *
         * @param key  APPKEY
         * @param info 提交的内容
         * @return
         */
        @FormUrlEncoded
        @POST(openApi)
        Observable<CoreEntity> getText(@Field("key") String key,
                                       @Field("info") String info);
    }

}
