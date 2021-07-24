package com.zyx_hunan.wanmvvm.logic.net.entrepot

import android.util.Log
import androidx.lifecycle.liveData
import com.zyx_hunan.wanmvvm.logic.Repository
import com.zyx_hunan.wanmvvm.logic.model.ArticleModel
import com.zyx_hunan.wanmvvm.logic.model.Articledata
import com.zyx_hunan.wanmvvm.logic.model.Regdata
import com.zyx_hunan.wanmvvm.logic.model.RegisterModel
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
object MainRepository {
    fun articleList(page: Int) = liveData(Dispatchers.IO) {

        val result = try {
            val articleModel: ArticleModel = WanNet.articleList(page)
            if (articleModel.errorCode == 0) {
                val data = articleModel.data.articleList
                Log.i("test", data.toString())
                Result.success(data)
            } else {
                Result.failure(RuntimeException("response errorCode is${articleModel.errorCode}"))
            }
        } catch (e: Exception) {
            Log.e("test",e.message+e.toString())
            Result.failure<List<Articledata>>(e)
        }
        emit(result)
    }
}