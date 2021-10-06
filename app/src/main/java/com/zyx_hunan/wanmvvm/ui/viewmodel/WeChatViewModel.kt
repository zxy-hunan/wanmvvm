package com.zyx_hunan.wanmvvm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.zyx_hunan.baseutil.expand.SingleLiveData
import com.zyx_hunan.wanmvvm.logic.net.entrepot.WeChatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withTimeout
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/30 0030,下午 1:59
 */
class WeChatViewModel : BaseViewModel() {
    private val articleLiveData = SingleLiveData<MutableMap<String, Int>>()
    private var requestBol = SingleLiveData<Boolean>()
    private var articleData = SingleLiveData<Result<List<Any>>>()



    val weChatLiveData= Transformations.switchMap(requestBol) {
        Log.e("wechat","requestBol:"+requestBol.value)
       WeChatRepository.weChatChapters()

    }

    val weChatarticleLiveData = Transformations.switchMap(articleLiveData) {
        Log.e("wechat", "articleLiveData:" + articleLiveData.value)
        WeChatRepository.weChatDetail(it)
    }


    fun getWechatList(b: Boolean = true) {
        requestBol.value = b
    }

    fun weChatDetail(cid: String, page: Int) {
        articleLiveData.value = mutableMapOf(cid to page)
    }


}