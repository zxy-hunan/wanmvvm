package com.zyx_hunan.wanmvvm.logic.net

import com.zyx_hunan.wanmvvm.logic.model.RegisterModel
import com.zyx_hunan.wanmvvm.logic.net.req.RegisterReq
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/20 0020,下午 2:44
 */
interface NetService {

    @POST("/user/register")
    fun register(@Body data: RegisterReq): Call<RegisterModel>

}