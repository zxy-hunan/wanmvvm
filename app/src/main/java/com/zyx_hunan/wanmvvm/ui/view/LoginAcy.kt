package com.zyx_hunan.wanmvvm.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.qmuiteam.qmui.skin.QMUISkinManager
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.popup.QMUIPopup
import com.qmuiteam.qmui.widget.popup.QMUIPopups
import com.zyx_hunan.baseutil.expand.isEmpty
import com.zyx_hunan.baseutil.expand.showToast
import com.zyx_hunan.baseutil.expand.value
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.databinding.ActivityLoginBinding
import com.zyx_hunan.wanmvvm.logic.model.Regdata
import com.zyx_hunan.wanmvvm.ui.adapter.UserAdapter
import com.zyx_hunan.wanmvvm.ui.viewmodel.LoginViewModel
import java.util.*

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/21 0021,下午 2:32
 */
class LoginAcy : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mNormalPopup: QMUIPopup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.liveData.observe(this, Observer {
            if (it.isSuccess) {
                val result = it.getOrNull()?.let {
                    it as Regdata
                    startActivity(Intent(this, MainActivity::class.java))
                    this.finish()
                }
            }else{
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
                    Toast.makeText(this, it.nickname, Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.localData.observe(this, Observer {
            if (it.isSuccess) {
                it.getOrNull()?.let {

                    val adapter =
                        UserAdapter(this, R.layout.simple_list_item, it as List<Regdata>)
                    val onItemClickListener =
                        AdapterView.OnItemClickListener {  parent,view,position,id->
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
        viewModel.findLocal()
    }

    fun login(view: View) {
        if (binding.name.isEmpty() || binding.pwd.isEmpty()) {

        } else {
            viewModel.login(binding.name.value(), binding.pwd.value())
        }
    }

    fun registers(view: View) {
        startActivity(Intent(this, RegisterAcy::class.java))
    }


    fun more(view: View) {
        viewModel.findLocal(1)
    }

}