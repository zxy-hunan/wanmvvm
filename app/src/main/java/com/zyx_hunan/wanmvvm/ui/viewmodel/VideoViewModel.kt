package com.zyx_hunan.wanmvvm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyx_hunan.wanmvvm.lifecycle.ViscosityLiveData
import com.zyx_hunan.wanmvvm.logic.model.OpenFeedTab
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
class VideoViewModel : ViewModel() {

    private val videoLiveData = MutableLiveData<Long>()


    val videoData = Transformations.switchMap(videoLiveData) {
        MainRepository.related(it)
    }


    fun videoList(id: Long = 0) {
        videoLiveData.value = id
    }

    fun refreshOrLoadMore(b: Boolean) = if (b) {
        videoLiveData.value = 0
    } else {
        videoLiveData.value = videoLiveData.value?.plus(1)
    }





}