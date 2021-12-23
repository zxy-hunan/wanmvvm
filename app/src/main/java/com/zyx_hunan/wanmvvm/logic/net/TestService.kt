package com.zyx_hunan.wanmvvm.logic.net

import com.zyx_hunan.baseutil.net.util.BaseResponse
import com.zyx_hunan.wanmvvm.logic.model.BannerModel
import retrofit2.http.GET
import io.reactivex.Observable

interface TestService {

    @GET("/banner/json")
    fun bannerList():Observable<BaseResponse<BannerModel>>

}