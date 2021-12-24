package com.zyx_hunan.wanmvvm

import android.app.Application
import android.content.Context
import cn.jpush.im.android.api.JMessageClient
import com.zyx_hunan.baseutil.net.util.NetUtil
import com.zyx_hunan.wanmvvm.logic.model.Regdata
import com.zyx_hunan.wanmvvm.logic.net.TestService

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
        NetUtil.options().setApiPath(TestService::class.java)
                .setDefault_time(10)
                .setUrl("https://www.wanandroid.com")
    }
}
