package com.zyx_hunan.wanmvvm.logic.net

import com.zyx_hunan.wanmvvm.logic.model.ArticleModel
import com.zyx_hunan.wanmvvm.logic.model.RegisterModel
import retrofit2.Call
import retrofit2.http.*

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/20 0020,下午 2:44
 */
interface NetService {

    @FormUrlEncoded
    @POST("/user/register")
    fun register(@FieldMap map: Map<String, String>): Call<RegisterModel>

    @FormUrlEncoded
    @POST("/user/login")
    fun login(@FieldMap map: Map<String, String>): Call<RegisterModel>

    @GET("/article/list/{page}/json")
    fun articleList(@Path("page") page: Int): Call<ArticleModel>

}