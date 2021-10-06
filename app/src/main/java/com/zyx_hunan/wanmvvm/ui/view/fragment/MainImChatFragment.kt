package com.zyx_hunan.wanmvvm.ui.view.fragment

import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder
import com.qmuiteam.qmui.widget.tab.QMUITabIndicator
import com.qmuiteam.qmui.widget.tab.QMUITabSegment
import com.zyx_hunan.baseview.BaseFragment
import com.zyx_hunan.wanmvvm.databinding.FragmentMainchatBinding

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/23 0023,下午 4:10
 */
class MainImChatFragment: BaseFragment<FragmentMainchatBinding>() {
    private var pagerMap: MutableMap<Int, Fragment>? = null
    private var tabNames = mutableMapOf(0 to "对话", 1 to "联系人")
    override fun requestData() {
        initPagers()
    }

    override fun onResume() {
        super.onResume()
    }


    private fun initPagers() {
        val homeFragment = ImChatFragment()
        val knowledgeFragment = ImChatGroupFragment()
        pagerMap = mutableMapOf(
            0 to homeFragment,
            1 to knowledgeFragment,
        )
        binding.imPager.adapter = fragmentManager?.let {
            PagerAdapter(
                it,
                pagerMap!!
            )
        }
        binding.imPager.setCurrentItem(1, false)

        val tabBuilder: QMUITabBuilder = binding.imTabSegment.tabBuilder().setGravity(Gravity.CENTER)
        for (element in tabNames){
            binding.imTabSegment.addTab(tabBuilder.setText(element.value).build(context))
        }

        val space = QMUIDisplayHelper.dp2px(context, 16)
        binding.imTabSegment.setIndicator(
            QMUITabIndicator(
                QMUIDisplayHelper.dp2px(context, 2), false, true
            )
        )
        binding.imTabSegment.setMode(QMUITabSegment.MODE_FIXED)
        binding.imTabSegment.setItemSpaceInScrollMode(space)
        binding.imTabSegment.setIndicator(QMUITabIndicator(QMUIDisplayHelper.dp2px(context, 2),false,false))
        binding.imTabSegment.setupWithViewPager(binding.imPager, false)
        binding.imTabSegment.setPadding(space, 0, space, 0)
        binding.imTabSegment.addOnTabSelectedListener(object : QMUITabSegment.OnTabSelectedListener {
            override fun onTabSelected(index: Int) {
            }

            override fun onTabUnselected(index: Int) {
            }

            override fun onTabReselected(index: Int) {
            }

            override fun onDoubleTap(index: Int) {
            }
        })

    }


    class PagerAdapter(fm: FragmentManager, private val pagerMap: MutableMap<Int, Fragment>) :
        FragmentPagerAdapter(fm) {
        override fun getCount() = pagerMap.size
        override fun getItem(position: Int): Fragment = pagerMap.get(position)!!
    }
}