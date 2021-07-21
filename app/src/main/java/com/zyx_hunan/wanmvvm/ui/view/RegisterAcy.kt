package com.zyx_hunan.wanmvvm.ui.view

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zyx_hunan.baseutil.expand.value
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
class RegisterAcy : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(RegisterViewModel::class.java) }
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.livedata.observe(this, Observer {
            val result = it.getOrNull()?.let { it as Regdata }
            Toast.makeText(this, result?.nickname, Toast.LENGTH_LONG).show()
        })
        val rad=java.util.Random()
        binding.name.setText("zxx"+(rad.nextInt(10000)))
    }


    fun register(view: View) {
        if (TextUtils.isEmpty(binding.name.value()) || TextUtils.isEmpty(binding.pwd.value()) || TextUtils.isEmpty( binding.pwd2.value())
        ) {
            //提示
        } else {
            if (binding.pwd.value() != binding.pwd2.value()) {
                //提示
            } else {
                viewModel.register(binding.name.value(), binding.pwd.value())
            }
        }
    }

}