package com.zyx_hunan.wanmvvm.ui.view.acy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zyx_hunan.baseutil.expand.showToast
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.WanApplication
import com.zyx_hunan.wanmvvm.databinding.AcyWelcomeBinding
import com.zyx_hunan.wanmvvm.logic.model.Regdata
import com.zyx_hunan.wanmvvm.ui.viewmodel.LoginViewModel
import java.util.*

/**
 * @author: ZXY
 * @date: 2021/9/27
 */
class WelcomeWanAndroidAcy : BaseActivity<AcyWelcomeBinding>() {
    private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    lateinit var name: String
    lateinit var pwd: String

    var handler: Handler? = Handler(Looper.getMainLooper())

    private val timerTask: Runnable = Runnable {
        startActivity(Intent(this@WelcomeWanAndroidAcy, MainActivity::class.java))
        this@WelcomeWanAndroidAcy.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("login", Context.MODE_PRIVATE)
        name = prefs.getString("name", "").toString()
        pwd = prefs.getString("pwd", "").toString()
        login(name, pwd)
        viewModel.liveData.observe(this, Observer {
            if (it.isSuccess) {
                val result = it.getOrNull()?.let {
//                    startActivity(Intent(this, MainActivity::class.java))
                    WanApplication.user = it as Regdata
                    getSharedPreferences("login", Context.MODE_PRIVATE).edit {
                        putString("name", name)
                        putString("pwd", pwd)
                    }
                    Toast.makeText(this@WelcomeWanAndroidAcy, "wanAndroid登录成功", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                it.showToast(this)
            }
        })


        viewModel.imData.observe(this, Observer {
            Toast.makeText(this@WelcomeWanAndroidAcy, "im登录成功", Toast.LENGTH_SHORT).show()
        })
        handler?.postDelayed(timerTask, 3000)

    }


    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacks(timerTask)
        handler = null
    }


    override fun onResume() {
        super.onResume()

    }

    private fun login(name: String, pwd: String) {
        Log.e("login", "name:${name},pwd:${pwd}")
        if (name.isEmpty() || pwd.isEmpty()) {

        } else {
            viewModel.login(name, pwd)
            viewModel.loginIm(name, pwd)
        }
    }

}