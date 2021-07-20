package com.zyx_hunan.wanmvvm

import android.app.Application
import android.content.Context

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
    }


    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }

}