package com.zyx_hunan.wanmvvm.ui.view.acy

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.jpush.im.android.api.model.UserInfo
import com.qmuiteam.qmui.skin.QMUISkinManager
import com.qmuiteam.qmui.widget.dialog.QMUIDialog.EditTextDialogBuilder
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.databinding.LayoutImchatSearchBinding
import com.zyx_hunan.wanmvvm.ui.viewmodel.ImChatViewModel

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/21 0021,上午 9:29
 */
class AddFriendAcy : BaseActivity<LayoutImchatSearchBinding>() {
    private val viewModel by lazy { ViewModelProvider(this).get(ImChatViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.topbar.setTitle("添加新朋友").setTextColor(resources.getColor(R.color.white))
        binding.topbar.addLeftBackImageButton().setOnClickListener { finish() }
        binding.editnum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNullOrEmpty()) {
                    binding.llsearch.visibility = View.GONE
                } else {
                    binding.llsearch.visibility = View.VISIBLE
                    binding.searchnum.text = s.toString()
                }
            }

        })
        binding.llsearch.setOnClickListener {
            viewModel.getUserInfo(binding.searchnum.text.toString())
        }
        viewModel.userInfo.observe(this, Observer {
            if (it == null) {

            } else {
                if (it.isFriend) {//原本就是好友

                } else {
                    showEditTextDialog(it)
                }
            }
        })
        viewModel.friendSend.observe(this, Observer {
            Toast.makeText(this@AddFriendAcy, "申请发送成功", Toast.LENGTH_LONG).show()
        })
    }

    override fun onResume() {
        super.onResume()

    }

    private fun showEditTextDialog(info: UserInfo) {
        val builder = EditTextDialogBuilder(this)
        builder.setTitle("添加好友")
            .setSkinManager(QMUISkinManager.defaultInstance(baseContext))
            .setPlaceholder("请输入验证信息")
            .setInputType(InputType.TYPE_CLASS_TEXT)
            .addAction(
                "取消"
            ) { dialog, index -> dialog.dismiss() }
            .addAction(
                "确定"
            ) { dialog, index ->
                val text: CharSequence? = builder.editText.text
                if (text != null && text.isNotEmpty()) {
                    viewModel.sendFriend(info, text.toString())
                    dialog.dismiss()
                } else {
                    Toast.makeText(this, "请输入验证信息", Toast.LENGTH_SHORT).show()
                }
            }
            .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show()
    }


}