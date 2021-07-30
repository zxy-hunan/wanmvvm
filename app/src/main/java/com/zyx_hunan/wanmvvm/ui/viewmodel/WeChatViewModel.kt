package com.zyx_hunan.wanmvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.zyx_hunan.wanmvvm.logic.net.entrepot.WeChatRepository

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/30 0030,下午 1:59
 */
class WeChatViewModel : ViewModel() {
    private val articleLiveData = MutableLiveData<MutableMap<String, Int>>()
    private var requestBol = MutableLiveData<Boolean>()

    val weChatLiveData = Transformations.switchMap(requestBol) {
        WeChatRepository.weChatChapters()
    }

    val weChatarticleLiveData = Transformations.switchMap(articleLiveData) {
        WeChatRepository.weChatDetail(it)
    }


    fun getWechatList(b: Boolean = true) {
        requestBol.value = b
    }

    fun weChatDetail(cid: String, page: Int) {
        articleLiveData.value = mutableMapOf(cid to page)
    }


}