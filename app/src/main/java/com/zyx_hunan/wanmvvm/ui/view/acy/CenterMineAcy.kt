package com.zyx_hunan.wanmvvm.ui.view.acy

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.WanApplication
import com.zyx_hunan.wanmvvm.databinding.ActivityCentermineBinding

class CenterMineAcy : BaseActivity<ActivityCentermineBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.topbar.setTitle("个人中心").setTextColor(resources.getColor(R.color.white))
        binding.topbar.addLeftBackImageButton().setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
    }


    fun logout(view: View) {
        WanApplication.user =null
        getSharedPreferences("login", Context.MODE_PRIVATE).edit {
            putString("name", "")
            putString("pwd",  "")
        }
        finish()
    }

}