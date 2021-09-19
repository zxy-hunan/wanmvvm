package com.zyx_hunan.wanmvvm.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.WanApplication
import com.zyx_hunan.wanmvvm.databinding.FragmentMineBinding
import com.zyx_hunan.wanmvvm.ui.view.LoginAcy
import com.zyx_hunan.wanmvvm.ui.view.SettingAcy
import com.zyx_hunan.wanmvvm.ui.viewmodel.KnowledgeViewModel

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/30 0030,上午 11:05
 */
class MineFragment : Fragment(),View.OnClickListener {
    private lateinit var binding: FragmentMineBinding
    private val viewModel by lazy { ViewModelProvider(this).get(KnowledgeViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMineBinding.inflate(inflater, container, false)
        createView()
        return binding.root
    }

    private fun createView() {
        binding.textlogin.text="当前登录用户:  ${WanApplication.user?.username}"
        val height =
            QMUIResHelper.getAttrDimen(activity, com.qmuiteam.qmui.R.attr.qmui_list_item_height)

        val item1: QMUICommonListItemView =
            binding.groupListView.createItemView(
                ContextCompat.getDrawable(activity!!, R.mipmap.item_icony),
                "收藏文章",
                "",
                QMUICommonListItemView.VERTICAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON,
                height
            )
        val item2: QMUICommonListItemView =
            binding.groupListView.createItemView(
                ContextCompat.getDrawable(activity!!, R.mipmap.item_icone),
                "分享文章",
                "",
                QMUICommonListItemView.VERTICAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON,
                height
            )
        val item3: QMUICommonListItemView =
            binding.groupListView.createItemView(
                ContextCompat.getDrawable(activity!!, R.mipmap.item_icons),
                "收藏网站",
                "",
                QMUICommonListItemView.VERTICAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON,
                height
            )
        val item4: QMUICommonListItemView =
            binding.groupListView.createItemView(
                ContextCompat.getDrawable(activity!!, R.mipmap.item_iconsi),
                "分享项目",
                "",
                QMUICommonListItemView.VERTICAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON,
                height
            )

        QMUIGroupListView.newSection(activity).setTitle("个人中心")
            .addItemView(item1,this)
            .addItemView(item2,this)
            .addItemView(item3,this)
            .addItemView(item4,this).addTo(binding.groupListView)

        val item5: QMUICommonListItemView =
            binding.groupListView.createItemView(
                ContextCompat.getDrawable(activity!!, R.mipmap.item_iconw),
                "系统设置",
                "",
                QMUICommonListItemView.VERTICAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON,
                height
            )

        QMUIGroupListView.newSection(activity).setTitle("设置")
            .addItemView(item5,this).addTo(binding.groupListView)
    }


    override fun onResume() {
        super.onResume()
        binding.textlogin.setOnClickListener {
            startActivity(Intent(activity,LoginAcy::class.java))
        }
    }

    override fun onClick(v: View?) {
        if (v is QMUICommonListItemView){
             when(v.text){
                 "系统设置" -> startActivity(Intent(activity,SettingAcy::class.java))
             }
        }
    }


}