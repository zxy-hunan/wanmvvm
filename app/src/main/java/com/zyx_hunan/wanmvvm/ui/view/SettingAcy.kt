package com.zyx_hunan.wanmvvm.ui.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zyx_hunan.baseutil.expand.value
import com.zyx_hunan.wanmvvm.databinding.ActivityRegisterBinding
import com.zyx_hunan.wanmvvm.databinding.ActivitySettingBinding
import com.zyx_hunan.wanmvvm.logic.model.Regdata
import com.zyx_hunan.wanmvvm.ui.viewmodel.RegisterViewModel

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/21 0021,上午 9:29
 */
class SettingAcy : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(RegisterViewModel::class.java) }
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    fun loginOrLoginOut(view: View) {
        startActivity(Intent(this,LoginAcy::class.java))
    }

}