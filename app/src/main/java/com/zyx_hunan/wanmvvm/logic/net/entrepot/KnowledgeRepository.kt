package com.zyx_hunan.wanmvvm.logic.net.entrepot

import android.util.Log
import androidx.lifecycle.liveData
import com.zyx_hunan.wanmvvm.logic.model.*
import com.zyx_hunan.wanmvvm.logic.net.WanNet
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/24 0024,下午 1:57
 */
object KnowledgeRepository {
    fun knowledgeList() = liveData(Dispatchers.IO) {
        val result = try {
            val knowledgeModel: KnowledgeModel = WanNet.knowledgeList()
            if (knowledgeModel.errorCode == 0) {
                val data = knowledgeModel.data
                Log.i("test", data.toString())
                Result.success(data)
            } else {
                Result.failure(RuntimeException("response errorCode is${knowledgeModel.errorCode}"))
            }
        } catch (e: Exception) {
            Log.e("test", e.message + e.toString())
            Result.failure<List<KnowledgeModel>>(e)
        }
        emit(result)
    }




}