package com.zyx_hunan.wanmvvm.ui.view

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.databinding.ActivitySettingBinding
import com.zyx_hunan.wanmvvm.ui.viewmodel.RegisterViewModel

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/21 0021,上午 9:29
 */
class SettingAcy : BaseActivity<ActivitySettingBinding>() {
    private val viewModel by lazy { ViewModelProvider(this).get(RegisterViewModel::class.java) }

    fun loginOrLoginOut(view: View) {
        startActivity(Intent(this,LoginAcy::class.java))
    }

}