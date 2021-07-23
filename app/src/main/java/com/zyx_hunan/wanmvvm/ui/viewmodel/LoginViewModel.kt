package com.zyx_hunan.wanmvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.zyx_hunan.wanmvvm.WanApplication
import com.zyx_hunan.wanmvvm.logic.Repository
import com.zyx_hunan.wanmvvm.logic.database.AppDataBase
import com.zyx_hunan.wanmvvm.logic.model.Regdata

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/21 0021,下午 2:42
 */
class LoginViewModel : ViewModel() {
    private val loginLiveData = MutableLiveData<Map<String, String>>()
    private val userLiveData = MutableLiveData<String>()
    private val userLocalData = MutableLiveData<List<Regdata>>()

    val liveData = Transformations.switchMap(loginLiveData) {
        Repository.login(it)
    }

    fun login(name: String, pwd: String) {
        val map = mutableMapOf("username" to name, "password" to pwd)
        loginLiveData.value = map
    }

    val userData = Transformations.switchMap(userLiveData) {
        Repository.findLocal()
    }

    val localData = Transformations.switchMap(userLocalData) {
        Repository.findLocal(1)
    }

    fun findLocal(type: Int = 0) =
        if (type != 1) userLiveData.value = "1" else userLocalData.value=null

}