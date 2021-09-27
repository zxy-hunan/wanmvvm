package com.zyx_hunan.wanmvvm.ui.adapter

import android.os.Bundle
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.databinding.ActivityChatBinding

/**
 * @author: ZXY
 * @date: 2021/9/27
 */
class ImChatAcy : BaseActivity<ActivityChatBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.topbar.setTitle("详细信息").setTextColor(resources.getColor(R.color.white))
        binding.topbar.addLeftBackImageButton().setOnClickListener { finish() }
    }
}