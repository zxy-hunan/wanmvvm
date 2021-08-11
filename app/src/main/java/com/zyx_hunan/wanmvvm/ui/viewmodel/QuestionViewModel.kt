package com.zyx_hunan.wanmvvm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.zyx_hunan.wanmvvm.logic.net.entrepot.QuestionRepository
import com.zyx_hunan.wanmvvm.logic.net.entrepot.WeChatRepository

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/30 0030,下午 1:59
 */
class QuestionViewModel : ViewModel() {
    private var requestPage = MutableLiveData<Int>()

    val weChatLiveData = Transformations.switchMap(requestPage) {
        QuestionRepository.wendaList(it)
    }

    fun getQuestionList(value: Int = 1) {
        requestPage.value = value
    }

    fun refreshOrLoadMoreList(b: Boolean) = if (b) requestPage.value = 1 else requestPage.value =
        requestPage.value?.plus(1)

}