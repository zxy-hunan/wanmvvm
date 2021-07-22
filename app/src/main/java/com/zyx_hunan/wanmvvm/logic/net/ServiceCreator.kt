package com.zyx_hunan.wanmvvm.logic.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    private val client=OkHttpClient.Builder()
    private val log=HttpLoggingInterceptor()
    init {
        log.level=HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(log)
    }

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}