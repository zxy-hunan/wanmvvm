package com.zyx_hunan.wanmvvm.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zyx_hunan.baseutil.expand.isEmpty
import com.zyx_hunan.baseutil.expand.value
import com.zyx_hunan.wanmvvm.databinding.ActivityLoginBinding
import com.zyx_hunan.wanmvvm.logic.model.Regdata
import com.zyx_hunan.wanmvvm.logic.model.RegisterModel
import com.zyx_hunan.wanmvvm.ui.viewmodel.LoginViewModel

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/21 0021,下午 2:32
 */
class LoginAcy:AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.liveData.observe(this, Observer {
            if (it.isSuccess){
                val result=it.getOrNull()?.let {
                   it as Regdata
                }
            }
        })
        viewModel.userData.observe(this, Observer {
            if (it.isSuccess){
                val result=it.getOrNull()?.let {
                    it as Regdata

                }
                Toast.makeText(this,result.toString(),Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.findLocal()
    }

    fun login(view:View){
        if(binding.name.isEmpty()||binding.pwd.isEmpty()){

        }else{
           viewModel.login(binding.name.value(),binding.pwd.value())
        }
    }

    fun registers(view: View){
         startActivity(Intent(this,RegisterAcy::class.java))
    }

}