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

    private val articleLiveData = MutableLiveData<Int>()

    //开眼数据
    val openFeedTabLiveData = ViscosityLiveData<OpenFeedTab>()

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

    fun openTabFeed(){
        viewModelScope.launch{
            val openFeedTab: OpenFeedTab = MainRepository.openTabFeed()
            openFeedTabLiveData.postValue(openFeedTab)
        }
    }

}