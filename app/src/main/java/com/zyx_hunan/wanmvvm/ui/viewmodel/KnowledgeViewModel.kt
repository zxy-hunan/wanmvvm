package com.zyx_hunan.wanmvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.zyx_hunan.wanmvvm.logic.net.entrepot.KnowledgeRepository

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/29 0029,上午 11:29
 */
class KnowledgeViewModel : ViewModel() {

    private var requestBol= MutableLiveData<Boolean>()

    val knowledgeLiveData=Transformations.switchMap(requestBol){
        KnowledgeRepository.knowledgeList()
    }

    fun getKnowLedgeList(b:Boolean=true){
        requestBol.value=b
    }


}