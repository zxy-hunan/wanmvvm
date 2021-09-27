package com.zyx_hunan.wanmvvm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.jpush.im.android.api.ContactManager
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.callback.GetUserInfoCallback
import cn.jpush.im.android.api.callback.GetUserInfoListCallback
import cn.jpush.im.android.api.model.UserInfo
import cn.jpush.im.api.BasicCallback
import com.zyx_hunan.wanmvvm.logic.database.entity.User
import com.zyx_hunan.wanmvvm.logic.net.entrepot.QuestionRepository

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/30 0030,下午 1:59
 */
class ImChatViewModel : ViewModel() {
    var contactList = MutableLiveData<MutableList<UserInfo>>()
    var userInfo = MutableLiveData<UserInfo>()
    var friendSend = MutableLiveData<Int>()

    //获得联系人
    fun getContactList() {
        ContactManager.getFriendList(object : GetUserInfoListCallback() {
            override fun gotResult(i: Int, s: String, data: MutableList<UserInfo>) {
                Log.e("im","gotResult:${i},p1:${s}")
                if (i == 0) {
                    contactList.postValue(data)
                }
            }
        })
    }


    fun getUserInfo(num: String) {
        JMessageClient.getUserInfo(num, object : GetUserInfoCallback() {
            override fun gotResult(p0: Int, p1: String?, info: UserInfo?) {
                Log.e("im","getUserInfo:${p0},p1:${p1}")
                    info?.let {
                        userInfo.postValue(it)
                    }
            }
        })
    }

    fun sendFriend(info: UserInfo, text: String) {
        ContactManager.sendInvitationRequest(info.userName,
            "d96241a0ef6d2eadfeb97e13",
            text, object : BasicCallback() {
                override fun gotResult(p0: Int, p1: String?) {
                    Log.e("im","sendFriend:${p0},p1:${p1}")
                    if (p0 == 0) {
                        friendSend.postValue(p0)
                    }
                }
            })
    }


}