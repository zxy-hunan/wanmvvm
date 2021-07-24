package com.zyx_hunan.wanmvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
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
        MainRepository.articleList(it)
    }

    fun articleList(page: Int) {
        articleLiveData.value = page
    }

    fun refreshOrLoadMore(b: Boolean)=if (b) articleLiveData.value=
        articleLiveData.value?.minus(1) else articleLiveData.value= articleLiveData.value?.plus(1)

}