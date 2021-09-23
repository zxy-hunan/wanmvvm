package com.zyx_hunan.wanmvvm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyx_hunan.wanmvvm.lifecycle.ViscosityLiveData
import com.zyx_hunan.wanmvvm.logic.model.BannerModel
import com.zyx_hunan.wanmvvm.logic.model.HotKeyListBean
import com.zyx_hunan.wanmvvm.logic.model.OpenFeedTab
import com.zyx_hunan.wanmvvm.logic.net.OpenNet
import com.zyx_hunan.wanmvvm.logic.net.WanNet
import com.zyx_hunan.wanmvvm.logic.net.entrepot.MainRepository
import kotlinx.coroutines.launch

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/24 0024,下午 2:00
 */
class HomeViewModel : ViewModel() {
     val date = MutableLiveData<Long>()
     val num = MutableLiveData<Long>()

    private val articleLiveData = MutableLiveData<Int>()

    //开眼数据
    val openFeedTabLiveData = ViscosityLiveData<OpenFeedTab>()

    val articleData = Transformations.switchMap(articleLiveData) {
        Log.e("ViewModel", "articleLiveData:${articleLiveData.value}")
        MainRepository.articleList(it)
    }

    val bannerData = Transformations.switchMap(articleLiveData) {
        MainRepository.bannerList(it)
    }

    fun articleList(page: Int = 0) {
        articleLiveData.value = page
    }

    fun refreshOrLoadMore(b: Boolean) = if (b) {
        articleLiveData.value = 0
    } else {
        articleLiveData.value = articleLiveData.value?.plus(1)
        if (date.value != 0L && num.value != 0L) {
            openTabFeed(date.value!!, num.value!!)
        } else {

        }
    }

    //搜索热词
    val hotKeyData = Transformations.switchMap(articleLiveData) {
        MainRepository.hotkey()
    }

    //开眼数据
    fun openTabFeed() {
        viewModelScope.launch {
            val openFeedTab: OpenFeedTab = MainRepository.openTabFeed()
            openFeedTabLiveData.postValue(openFeedTab)
        }
    }

    fun openTabFeed(date: Long, num: Long) {
        viewModelScope.launch {
            val openFeedTab: OpenFeedTab = MainRepository.openTabFeed(date, num)
            openFeedTabLiveData.postValue(openFeedTab)
        }
    }


    private val videoLiveData = MutableLiveData<Long>()


    val videoData = Transformations.switchMap(videoLiveData) {
        MainRepository.related(it)
    }


    fun videoList(id: Long = 0) {
        videoLiveData.value = id
    }

}