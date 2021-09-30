package com.zyx_hunan.wanmvvm.ui.view.acy

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.zyx_hunan.baseutil.expand.isNull
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.WanApplication
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


    override fun onResume() {
        super.onResume()
        if (WanApplication.user.isNull()) {
            binding.button.text = "立即登录"
        } else {
            binding.button.text = "退出登录"
        }
    }

    fun loginOrLoginOut(view: View) {
        if (WanApplication.user.isNull()) {
            startActivity(Intent(this, LoginAcy::class.java))
        } else {
            WanApplication.user = null
            binding.button.text = "立即登录"
        }

    }

}