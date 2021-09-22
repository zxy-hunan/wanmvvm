package com.zyx_hunan.wanmvvm.logic.net

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author: ZXY
 * @date: 2021/9/20
 */
object OpenNet {
    private val netService = ServiceCreator.create<OpenService>(Constant.OPENEYE_BASEURL)

    suspend fun openTabFeed() = netService.openTabFeed().await()


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