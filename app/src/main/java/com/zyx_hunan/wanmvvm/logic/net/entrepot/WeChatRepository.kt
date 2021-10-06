package com.zyx_hunan.wanmvvm.logic.net.entrepot

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.zyx_hunan.wanmvvm.logic.model.*
import com.zyx_hunan.wanmvvm.logic.net.DataType
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
object WeChatRepository {
    fun weChatChapters() = liveData(Dispatchers.IO) {
        val result = try {
            val weChatListModel: WeChatListModel = WanNet.weChatChapters()
            if (weChatListModel.errorCode == 0) {
                val data = weChatListModel.data
                Log.i("test", data.toString())
                Result.success(data)
            } else {
                Result.failure(RuntimeException("response errorCode is${weChatListModel.errorCode}"))
            }
        } catch (e: Exception) {
            Log.e("test", e.message + e.toString())
            Result.failure<List<WeChatListModel>>(e)
        }
        emit(result)
    }


    fun weChatDetail(map: MutableMap<String, Int>) = liveData(Dispatchers.IO) {
        var cid: String = ""
        var page: Int = 0
        val result = try {
            map?.forEach {
                cid = it.key
                page = it.value
            }
            val articleModel: ArticleModel = WanNet.weChatDetail(cid, page)
            if (articleModel.errorCode == 0) {
                val allData = mutableListOf<AllData>()
                val data = articleModel.data.articleList
                for (aData: Articledata in data) {
                    val tidyData = AllData(
                        DataType.WANARTICLE,
                        aData.author,
                        aData.chapterName,
                        aData.collect,
                        aData.fresh,
                        aData.id,
                        aData.link,
                        aData.publishTime,
                        aData.shareUser,
                        aData.superChapterName,
                        aData.title,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                    allData.add(tidyData)
                }

                Result.success(allData)
            } else {
                Result.failure(RuntimeException("response errorCode is${articleModel.errorCode}"))
            }
        } catch (e: Exception) {
            Log.e("test", e.message + e.toString())
            Result.failure<List<AllData>>(e)
        }
        emit(result)
    }


}