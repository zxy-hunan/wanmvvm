package com.zyx_hunan.wanmvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.zyx_hunan.wanmvvm.logic.Repository

open class BaseViewModel : ViewModel() {

    private val id = MutableLiveData<MutableList<Long>>()

    val collectData = Transformations.switchMap(id) {
        id.value?.get(0)?.let { it1 -> id.value?.get(1)?.toInt()?.let { it2 ->
            Repository.collect(it1,
                it2
            )
        } }
    }

    fun collect(aid: Long = 0, type: Long = 0) {
        id.value = mutableListOf(aid, type)
    }
}