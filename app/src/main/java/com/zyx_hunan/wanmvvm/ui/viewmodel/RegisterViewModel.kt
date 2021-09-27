package com.zyx_hunan.wanmvvm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.options.RegisterOptionalUserInfo
import cn.jpush.im.api.BasicCallback
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
    val registerIm=MutableLiveData<Int>()

    val livedata=Transformations.switchMap(registerLiveData){
        Repository.register(it)
    }

    fun register(name:String,pwd:String){
        val map= mutableMapOf("username" to name,"password" to pwd,"repassword" to pwd)
        registerLiveData.value=map
    }

    fun regImChat(name:String,pwd:String,info:RegisterOptionalUserInfo?){
        JMessageClient.register(name,pwd,object : BasicCallback() {
            override fun gotResult(p0: Int, p1: String?) {
             Log.e("im","p0:${p0},p1:${p1}")
                if (p0==0){
                    registerIm.postValue(p0)
                }
            }
        })
    }

}