package com.zyx_hunan.wanmvvm.logic.net.entrepot

import android.util.Log
import androidx.lifecycle.liveData
import com.zyx_hunan.wanmvvm.WanApplication
import com.zyx_hunan.wanmvvm.compose.video.VideoModel
import com.zyx_hunan.wanmvvm.logic.Repository
import com.zyx_hunan.wanmvvm.logic.database.AppDataBase
import com.zyx_hunan.wanmvvm.logic.database.dao.HistoryRecordDao
import com.zyx_hunan.wanmvvm.logic.database.entity.HistoryRecord
import com.zyx_hunan.wanmvvm.logic.model.*
import com.zyx_hunan.wanmvvm.logic.net.DataType
import com.zyx_hunan.wanmvvm.logic.net.OpenNet
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
    private var historyRecordDao: HistoryRecordDao = AppDataBase.getDataBase(WanApplication.mContext).historyRecorDao
    fun articleList(page: Int) = liveData(Dispatchers.IO) {
        val result = try {
            val articleModel: ArticleModel = WanNet.articleList(page)
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

                Log.i("test", allData.toString())
                Result.success(allData)
            } else {
                Result.failure(RuntimeException("response errorCode is${articleModel.errorCode}"))
            }
        } catch (e: Exception) {
            Log.e("test", e.message + e.toString())
            Result.failure<List<Articledata>>(e)
        }
        emit(result)
    }

    fun hotkey() = liveData(Dispatchers.IO) {
        val result = try {
            val hotKeyBean: HotKeyListBean = WanNet.hotkey()
            if (hotKeyBean.errorCode == 0) {
                val data = hotKeyBean.data
                Log.i("test", data.toString())
                Result.success(data)
            } else {
                Result.failure(RuntimeException("response errorCode is${hotKeyBean.errorCode}"))
            }
        } catch (e: Exception) {
            Log.e("test", e.message + e.toString())
            Result.failure<List<HotKeyListBean>>(e)
        }
        emit(result)
    }

    suspend fun articleSearch(page:Int,map: Map<String,String>) = WanNet.articleSearch(page,map)




    fun addHistoryRecord(txt:String){
       historyRecordDao?.inserthistory(HistoryRecord(txt,0))
    }

    fun findHistory() = liveData(Dispatchers.IO) {
        val result = try {
            var his: List<HistoryRecord>? = null
            his = historyRecordDao?.finAllhistory()

            if (his != null) {
                Result.success(his)
            } else {
                Result.failure(RuntimeException("his is null"))
            }
        } catch (e: Exception) {
            Result.failure<List<HistoryRecord>>(e)
        }
        emit(result)
    }


    fun bannerList(num: Int) =
        liveData(Dispatchers.IO) {
            if (num != 0) {
                return@liveData
            }
            val result = try {
                val bannerModel: BannerModel = WanNet.bannerList()
                if (bannerModel.errorCode == 0) {
                    val data = bannerModel.data
                    Log.i("test", data.toString())
                    Result.success(data)
                } else {
                    Result.failure(RuntimeException("response errorCode is${bannerModel.errorCode}"))
                }
            } catch (e: Exception) {
                Log.e("test", e.message + e.toString())
                Result.failure<List<BannerModel>>(e)
            }
            emit(result)
        }

    suspend fun openTabFeed() = OpenNet.openTabFeed()

    suspend fun openTabFeed(date: Long,num: Long) = OpenNet.openTabFeed(date,num)



    fun related(id: Long) =
        liveData(Dispatchers.IO) {
            val result = try {
                val openFeedTab: OpenFeedTab = OpenNet.related(id)
                if (openFeedTab.count.toLong() != 0) {
                    val videoList= mutableListOf<VideoModel>()
                    val data = openFeedTab.itemList
                    data?.let {
                        for (item in it) {
                            if (item.data.id!=0) {
                                var video = VideoModel(
                                    item.data.id,
                                    item.data.title,
                                    item.data.description,
                                    item.data.cover?.feed,
                                    item.data.playUrl
                                )
                                videoList.add(video)
                            }
                        }
                    }
                    Result.success(videoList)
                } else {
                    Result.failure(RuntimeException("response errorCode is"))
                }
            } catch (e: Exception) {
                Log.e("test", e.message + e.toString())
                Result.failure<List<VideoModel>>(e)
            }
            emit(result)
        }
}