package com.zyx_hunan.wanmvvm.logic.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/20 0020,下午 2:47
 */
object ServiceCreator {
    private val client = OkHttpClient.Builder()
    private val log = HttpLoggingInterceptor()

    init {
        log.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(log)
            .readTimeout(15000L, TimeUnit.MILLISECONDS)
            .writeTimeout(15000L, TimeUnit.MILLISECONDS)
            .connectTimeout(15000L, TimeUnit.MILLISECONDS)
    }

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(Constant.WANANDROID_BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

    private val retrofit1 =
        Retrofit.Builder()
            .baseUrl(Constant.OPENEYE_BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

    fun <T> create(baseUrl: String, serviceClass: Class<T>): T =
        if (baseUrl == Constant.WANANDROID_BASEURL)
            retrofit.create(serviceClass)
        else retrofit1.create(serviceClass)


    inline fun <reified T> create(baseUrl: String): T = create(baseUrl, T::class.java)
}