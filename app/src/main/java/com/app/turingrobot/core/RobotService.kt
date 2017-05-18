package com.app.turingrobot.core


import com.app.turingrobot.entity.CoreEntity


import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
    fun getText(@Field("key") key: String, @Field("info") info: String): Observable<CoreEntity>

    companion object {

        const val openApi = "openapi/api"
    }
}