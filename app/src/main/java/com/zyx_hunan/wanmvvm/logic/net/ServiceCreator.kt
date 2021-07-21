package com.zyx_hunan.wanmvvm.logic.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/20 0020,下午 2:47
 */
object ServiceCreator {
    private val BASEURL = "https://www.wanandroid.com"
    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}