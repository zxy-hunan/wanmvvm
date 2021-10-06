package com.zyx_hunan.wanmvvm.ui.view.acy

import android.os.Bundle
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.databinding.ActivitySharearticleBinding

/**
 * @author: ZXY
 * @date: 2021/10/4
 */
class ShareArticleAcy : BaseActivity<ActivitySharearticleBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.topbar.setTitle("分享到站内").setTextColor(resources.getColor(R.color.white))
        binding.topbar.addLeftBackImageButton().setOnClickListener { finish() }
    }
}