package com.zyx_hunan.wanmvvm.logic.net

import com.zyx_hunan.wanmvvm.logic.net.req.RegisterReq
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/20 0020,下午 4:06
 */
object WanNet {
    private val netService = ServiceCreator.create<NetService>()

    suspend fun register(map: Map<String,String>) = netService.register(map).await()

    suspend fun login(map: Map<String,String>) = netService.login(map).await()

    suspend fun articleList(page: Int) = netService.articleList(page).await()

    suspend fun bannerList() = netService.bannerList().await()

    suspend fun knowledgeList() = netService.knowledgeList().await()

    suspend fun weChatChapters() = netService.weChatChapters().await()

    suspend fun weChatDetail(cid:String,page:Int) = netService.weChatDetail(cid,page).await()

    suspend fun wendaList(page:Int) = netService.wendaList(page).await()


    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) it.resume(body) else it.resumeWithException(RuntimeException("response is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }

            })
        }
    }

}