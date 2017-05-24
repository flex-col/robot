package com.app.turingrobot.core


import com.app.turingrobot.entity.CoreEntity
import com.app.turingrobot.entity.mob.data.GetResponse
import com.app.turingrobot.entity.mob.data.PutResponse


import io.reactivex.Observable
import retrofit2.http.*

/**
 * 请求接口
 */
interface RobotService {

    /**
     * 请求文字类型

     * @param key  APPKEY
     * *
     * @param info 提交的内容
     * *
     * @return
     */
    @FormUrlEncoded
    @POST(openApi)
    fun sendMsg(@Field("key") key: String, @Field("info") info: String): Observable<CoreEntity>

    /**
     * 存储数据
     */
    @GET("http://apicloud.mob.com/ucache/put")
    fun put(
            @Query("key") key: String,
            @Query("table") table: String,
            @Query("k") k: String,
            @Query("v") v: String): Observable<PutResponse>

    /**
     * 查询数据
     */
    @GET("http://apicloud.mob.com/ucache/get")
    fun get(
            @Query("key") key: String,
            @Query("table") table: String,
            @Query("k") k: String): Observable<GetResponse>

    companion object {

        const val openApi = "openapi/api"
    }
}