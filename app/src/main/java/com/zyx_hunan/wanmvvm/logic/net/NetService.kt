package com.zyx_hunan.wanmvvm.logic.net

import com.zyx_hunan.wanmvvm.logic.model.*
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

    @GET("/banner/json")
    fun bannerList(): Call<BannerModel>

    @GET("/tree/json")
    fun knowledgeList(): Call<KnowledgeModel>

    @GET("/wxarticle/chapters/json")
    fun weChatChapters(): Call<WeChatListModel>

    @GET("/wxarticle/list/{cid}/{page}/json")
    fun weChatDetail(@Path("cid") cid: String,@Path("page") page: Int): Call<ArticleModel>

    @GET("/wenda/list/{page}/json")
    fun wendaList(@Path("page") page: Int): Call<QuestionListModel>

    @POST("/lg/collect/{id}/json")
    fun collectArticle(@Path("id") id: Long):Call<QuestionListModel>

    @POST("/lg/uncollect_originId/{id}/json")
    fun unCollectArticle(@Path("id") id: Long):Call<QuestionListModel>

    @GET("hotkey/json")
    fun hotkey():Call<HotKeyListBean>

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    fun articleSearch(@Path("page") page: Int,@FieldMap map: Map<String, String>): Call<ArticleModel>


}