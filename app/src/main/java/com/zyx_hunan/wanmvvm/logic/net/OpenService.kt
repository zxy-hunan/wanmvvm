package com.zyx_hunan.wanmvvm.logic.net

import com.zyx_hunan.wanmvvm.logic.model.OpenFeedTab
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author: ZXY
 * @date: 2021/9/20
 */
interface OpenService {
    @GET("v5/index/tab/feed")
    fun openTabFeed(): Call<OpenFeedTab>

    @GET("v5/index/tab/feed")
    fun openTabFeed(@Query("date") date: Long,@Query("num") num: Long): Call<OpenFeedTab>


    //视频
    @GET("v4/video/related")
    fun related(@Query("id") id: Long): Call<OpenFeedTab>


}