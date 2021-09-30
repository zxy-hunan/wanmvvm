package com.zyx_hunan.wanmvvm.ui.view.acy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.qmuiteam.qmui.skin.QMUISkinManager
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.popup.QMUIPopup
import com.qmuiteam.qmui.widget.popup.QMUIPopups
import com.zyx_hunan.baseutil.expand.isEmpty
import com.zyx_hunan.baseutil.expand.showToast
import com.zyx_hunan.baseutil.expand.value
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.WanApplication
import com.zyx_hunan.wanmvvm.databinding.ActivityLoginBinding
import com.zyx_hunan.wanmvvm.logic.model.Regdata
import com.zyx_hunan.wanmvvm.ui.adapter.UserAdapter
import com.zyx_hunan.wanmvvm.ui.viewmodel.LoginViewModel

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/21 0021,下午 2:32
 */
class LoginAcy : BaseActivity<ActivityLoginBinding>() {
    private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    private lateinit var mNormalPopup: QMUIPopup
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
                    if (wanSuccess && imSuccess) {
                        wanSuccess = false
                        imSuccess = false
                        Toast.makeText(this@LoginAcy, "登录成功,正在退出，请稍后。。。", Toast.LENGTH_LONG).show()
                        this.post(task)
                    }
                }
                -1 -> {
                    finish()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.topbar.setTitle("登录").setTextColor(resources.getColor(R.color.white))
        binding.topbar.addLeftBackImageButton().setOnClickListener { finish() }
        viewModel.findLocal()
        viewModel.liveData.observe(this, Observer {
            if (it.isSuccess) {
                val result = it.getOrNull()?.let {
//                    startActivity(Intent(this, MainActivity::class.java))
                    WanApplication.user = it as Regdata
                    mHandler!!.sendEmptyMessage(1)
                    getSharedPreferences("login", Context.MODE_PRIVATE).edit {
                        putString("name", it.username)
                        putString("pwd",  binding.pwd.value())
                    }
                }
            } else {
                it.showToast(this)
            }
        })
        viewModel.userData.observe(this, Observer {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    it as Regdata
                }?.let {
                    binding.name.setText(it.username)
                    binding.pwd.setText("11111111")
//                    Toast.makeText(this, it.nickname, Toast.LENGTH_LONG).show()
                }
            }
        })


        viewModel.imData.observe(this, Observer {
            if (it == 0) {
                mHandler!!.sendEmptyMessage(2)
            }
        })



        viewModel.localData.observe(this, Observer {
            if (it.isSuccess) {
                it.getOrNull()?.let {

                    val adapter =
                        UserAdapter(this, R.layout.simple_list_item, it as List<Regdata>)
                    val onItemClickListener =
                        AdapterView.OnItemClickListener { parent, view, position, id ->
                            binding.name.setText(it[position].username)
                            if (mNormalPopup != null) {
                                mNormalPopup.dismiss()
                            }
                        }
                    mNormalPopup = QMUIPopups.listPopup(
                        this,
                        QMUIDisplayHelper.dp2px(this, 150),
                        QMUIDisplayHelper.dp2px(this, 200),
                        adapter,
                        onItemClickListener
                    )
                        .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                        .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                        .shadow(true)
                        .offsetYIfTop(QMUIDisplayHelper.dp2px(this, 5))
                        .skinManager(QMUISkinManager.defaultInstance(this))
                        .onDismiss {

                        }
                        .show(binding.more)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

    }

    fun login(view: View) {
        if (binding.name.isEmpty() || binding.pwd.isEmpty()) {

        } else {
            viewModel.login(binding.name.value(), binding.pwd.value())
            viewModel.loginIm(binding.name.value(), binding.pwd.value())
        }
    }

    fun registers(view: View) {
        startActivity(Intent(this, RegisterAcy::class.java))
    }


    fun more(view: View) {
        viewModel.findLocal(1)
    }

}