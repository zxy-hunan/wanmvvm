package com.zyx_hunan.wanmvvm.ui.view.acy

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.WanApplication
import com.zyx_hunan.wanmvvm.databinding.ActivitySettingBinding
import com.zyx_hunan.wanmvvm.ui.viewmodel.RegisterViewModel

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/21 0021,上午 9:29
 */
class SettingAcy : BaseActivity<ActivitySettingBinding>(),View.OnClickListener {
    private val viewModel by lazy { ViewModelProvider(this).get(RegisterViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.topbar.setTitle("系统设置").setTextColor(resources.getColor(R.color.white))
        binding.topbar.addLeftBackImageButton().setOnClickListener { finish() }
        createView()
    }

    override fun onResume() {
        super.onResume()
    }


    private fun createView() {
        val height =
            QMUIResHelper.getAttrDimen(this, com.qmuiteam.qmui.R.attr.qmui_list_item_height)

        val item1: QMUICommonListItemView =
            binding.groupListView.createItemView(
                ContextCompat.getDrawable(this, R.mipmap.iconsafe),
                "隐私政策",
                "",
                QMUICommonListItemView.VERTICAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON,
                height
            )
        val item2: QMUICommonListItemView =
            binding.groupListView.createItemView(
                ContextCompat.getDrawable(this, R.mipmap.iconquestion),
                "问题反馈",
                "",
                QMUICommonListItemView.VERTICAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON,
                height
            )
        val item3: QMUICommonListItemView =
            binding.groupListView.createItemView(
                ContextCompat.getDrawable(this, R.mipmap.iconabout),
                "关于玩安卓",
                "",
                QMUICommonListItemView.VERTICAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON,
                height
            )


        QMUIGroupListView.newSection(this)
            .addItemView(item1,this)
            .addItemView(item2,this)
            .addItemView(item3,this).addTo(binding.groupListView)

    }

    override fun onClick(v: View?) {
        if (v is QMUICommonListItemView) {
            when (v.text) {
                "关于玩安卓" -> {
                    val intent = Intent(this, ArticleItemAcy::class.java)
                    intent.putExtra("url", "https://www.wanandroid.com/index")
                    intent.putExtra("title", "关于玩安卓")
                    intent.putExtra("collect", false)
                    this.startActivity(intent)
                }
            }
        }


    }


}