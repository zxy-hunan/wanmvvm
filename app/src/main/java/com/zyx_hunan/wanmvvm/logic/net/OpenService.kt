package com.zyx_hunan.wanmvvm.logic.net

import com.zyx_hunan.wanmvvm.logic.model.OpenFeedTab
import retrofit2.Call
import retrofit2.http.GET

/**
 * @author: ZXY
 * @date: 2021/9/20
 */
interface OpenService {
    @GET("v5/index/tab/feed")
    fun openTabFeed(): Call<OpenFeedTab>
}