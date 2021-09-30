package com.zyx_hunan.wanmvvm.ui.view.acy

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zyx_hunan.baseutil.expand.value
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.databinding.ActivityRegisterBinding
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
class RegisterAcy : BaseActivity<ActivityRegisterBinding>() {
    private val viewModel by lazy { ViewModelProvider(this).get(RegisterViewModel::class.java) }
    private var wanSuccess = false//wanandroid
    private var imSuccess = false//im

    override var mHandler: Handler? = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1 -> {
                    wanSuccess = true
                    this.sendEmptyMessage(0)
                }

                2 -> {
                    imSuccess = true
                    this.sendEmptyMessage(0)
                }

                0 -> {
                    if (wanSuccess && imSuccess)
                        wanSuccess = false
                    imSuccess = false
                    Toast.makeText(this@RegisterAcy,"注册成功,正在退出，请稍后。。。", Toast.LENGTH_LONG).show()
                    this.post(task)
                }
                -1 -> {
                    finish()
                }
            }
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.topbar.setTitle("注册").setTextColor(resources.getColor(R.color.white))
        binding.topbar.addLeftBackImageButton().setOnClickListener { finish() }
        viewModel.livedata.observe(this, Observer {
            val result = it.getOrNull()?.let { it as Regdata }
//            Toast.makeText(this, result?.nickname, Toast.LENGTH_LONG).show()
            result?.let {
                mHandler!!.sendEmptyMessage(1)
            }
        })

        viewModel.registerIm.observe(this, Observer {
            if(it==0){
                mHandler!!.sendEmptyMessage(2)
            }
        })

    }

    override fun onResume() {
        super.onResume()
        val rad = java.util.Random()
        binding.name.setText("changsha" + (rad.nextInt(100000)))
    }


    fun register(view: View) {
        if (TextUtils.isEmpty(binding.name.value()) || TextUtils.isEmpty(binding.pwd.value()) || TextUtils.isEmpty(
                binding.pwd2.value()
            )
        ) {
            //提示
        } else {
            if (binding.pwd.value() != binding.pwd2.value()) {
                //提示
            } else {
                viewModel.register(binding.name.value(), binding.pwd.value())
                viewModel.regImChat(binding.name.value(), binding.pwd.value(), null)
            }
        }
    }

}