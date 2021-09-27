package com.zyx_hunan.wanmvvm.ui.view.acy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.jpush.im.android.api.model.UserInfo
import cn.jpush.im.api.BasicCallback
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.databinding.ActivityUserImdetailBinding
import com.zyx_hunan.wanmvvm.ui.adapter.ImChatAcy
import com.zyx_hunan.wanmvvm.ui.viewmodel.ImChatViewModel
import java.util.*

/**
 * @author: ZXY
 * @date: 2021/9/27
 */
class FriendDetailAcy : BaseActivity<ActivityUserImdetailBinding>() {
    private val viewModel by lazy { ViewModelProvider(this).get(ImChatViewModel::class.java) }
    var userName = ""
    var userInfo: UserInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userName = intent.getStringExtra("name").toString()

        binding.topbar.setTitle("详细信息").setTextColor(resources.getColor(R.color.white))
        binding.topbar.addLeftBackImageButton().setOnClickListener { finish() }

        viewModel.getUserInfo(userName)

        viewModel.userInfo.observe(this, Observer {
            if (it == null) {

            } else {
                userInfo = it
            }
        })

    }


    fun delete(view: View) {
        userInfo?.let {
            it.removeFromFriendList(object : BasicCallback() {
                override fun gotResult(p0: Int, p1: String?) {

                    if (p0 == 0) {
                        showDialog("删除成功")
                    } else {
                        showDialog("删除失败")
                    }
                }
            })
        }
    }

    fun sendmsg(view: View) {
        val intent = Intent(this, ImChatAcy::class.java)
        intent.putExtra("type", 0)
        intent.putExtra("name", userInfo?.userName)
        intent.putExtra("nickname", userInfo?.nickname)
        this.startActivity(intent)
    }

    fun showDialog(text: String) {
        val tipDialog = QMUITipDialog.Builder(this)
            .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
            .setTipWord(text)
            .create()
        tipDialog.show()
        handler.postDelayed(Runnable { tipDialog.dismiss() }, 1500)
    }


    val handler: Handler = Handler(Looper.getMainLooper())

}