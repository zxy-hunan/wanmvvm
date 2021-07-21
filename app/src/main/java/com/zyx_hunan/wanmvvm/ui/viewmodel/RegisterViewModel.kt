package com.zyx_hunan.wanmvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.zyx_hunan.wanmvvm.logic.Repository

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/20 0020,下午 4:55
 */
class RegisterViewModel :ViewModel() {
    private val registerLiveData=MutableLiveData<Map<String,String>>()

    val livedata=Transformations.switchMap(registerLiveData){
        Repository.register(it)
    }

    fun register(name:String,pwd:String){
        val map= mutableMapOf("username" to name,"password" to pwd,"repassword" to pwd)
        registerLiveData.value=map
    }

}