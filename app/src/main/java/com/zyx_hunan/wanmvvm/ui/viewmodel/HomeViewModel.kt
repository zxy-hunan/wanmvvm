package com.zyx_hunan.wanmvvm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.zyx_hunan.wanmvvm.logic.model.HotKeyListBean
import com.zyx_hunan.wanmvvm.logic.net.entrepot.MainRepository

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/24 0024,下午 2:00
 */
class HomeViewModel : ViewModel() {

    private val articleLiveData = MutableLiveData<Int>()

    val articleData = Transformations.switchMap(articleLiveData) {
        Log.e("ViewModel","articleLiveData:${articleLiveData.value}")
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
    }

    //搜索热词
    val hotKeyData = Transformations.switchMap(articleLiveData) {
        MainRepository.hotkey()
    }

}