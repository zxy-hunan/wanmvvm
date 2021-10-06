package com.zyx_hunan.wanmvvm.logic.net.entrepot

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
object QuestionRepository {
    fun wendaList(page:Int) = liveData(Dispatchers.IO) {
        val result = try {
            val questionListModel: QuestionListModel = WanNet.wendaList(page)
            if (questionListModel.errorCode == 0) {

                val allData = mutableListOf<AllData>()
                val data = questionListModel.data?.datas
                if (data != null) {
                    for (aData: QuestionModel in data) {
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
                }

                Result.success(allData)
            } else {
                Result.failure(RuntimeException("response errorCode is${questionListModel.errorCode}"))
            }
        } catch (e: Exception) {
            Result.failure<List<QuestionListModel>>(e)
        }
        emit(result)
    }
}