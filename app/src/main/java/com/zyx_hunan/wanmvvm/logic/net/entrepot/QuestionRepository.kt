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
object QuestionRepository {
    fun wendaList(page:Int) = liveData(Dispatchers.IO) {
        val result = try {
            val questionListModel: QuestionListModel = WanNet.wendaList(page)
            if (questionListModel.errorCode == 0) {
                val data = questionListModel.data?.datas
                Log.i("test", data.toString())
                Result.success(data)
            } else {
                Result.failure(RuntimeException("response errorCode is${questionListModel.errorCode}"))
            }
        } catch (e: Exception) {
            Log.e("test", e.message + e.toString())
            Result.failure<List<QuestionListModel>>(e)
        }
        emit(result)
    }
}