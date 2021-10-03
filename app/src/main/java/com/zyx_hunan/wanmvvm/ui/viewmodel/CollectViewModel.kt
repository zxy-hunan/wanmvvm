package com.zyx_hunan.wanmvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.zyx_hunan.wanmvvm.logic.net.entrepot.MainRepository


class CollectViewModel : BaseViewModel() {
    private val articleData = MutableLiveData<Int>()

    val collectList = Transformations.switchMap(articleData) {
        MainRepository.articleCollectList(it)
    }


    fun articleList(page: Int = 0) {
        articleData.value = page
    }

    fun refreshOrLoadMore(b: Boolean) = if (b) {
        articleData.value = 0
    } else {
        articleData.value = articleData.value?.plus(1)

    }

}