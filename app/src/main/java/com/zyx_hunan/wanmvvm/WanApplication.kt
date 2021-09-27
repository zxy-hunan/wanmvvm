package com.zyx_hunan.wanmvvm

import android.app.Application
import android.content.Context
import android.util.Log
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.event.ContactNotifyEvent
import com.zyx_hunan.wanmvvm.logic.model.Regdata

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/20 0020,下午 2:38
 */
class WanApplication : Application() {

    companion object {
        lateinit var mContext: Context
        var user: Regdata? =null
    }


    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        JMessageClient.init(this)

    }
}
