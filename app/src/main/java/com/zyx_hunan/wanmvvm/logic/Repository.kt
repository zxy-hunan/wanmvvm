package com.zyx_hunan.wanmvvm.logic

import androidx.lifecycle.liveData
import com.zyx_hunan.wanmvvm.logic.model.Regdata
import com.zyx_hunan.wanmvvm.logic.model.RegisterModel
import com.zyx_hunan.wanmvvm.logic.net.WanNet
import com.zyx_hunan.wanmvvm.logic.net.req.RegisterReq
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import retrofit2.Call
import java.lang.Exception

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/20 0020,下午 4:19
 */
object Repository {

    fun register(data: RegisterReq) = liveData(Dispatchers.IO) {

        val result = try {

            val registerRep: RegisterModel = WanNet.register(data)
            if (registerRep.errorCode == 0) {
                val data = registerRep.data
                Result.success(data)
            } else {
                Result.failure(RuntimeException("response errorCode is${registerRep.errorCode}"))
            }

        } catch (e: Exception) {
            Result.failure<List<Regdata>>(e)
        }
        emit(result)
    }

}