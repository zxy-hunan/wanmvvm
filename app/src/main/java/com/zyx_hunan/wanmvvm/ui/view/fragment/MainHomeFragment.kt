package com.zyx_hunan.wanmvvm.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder
import com.qmuiteam.qmui.widget.tab.QMUITabIndicator
import com.qmuiteam.qmui.widget.tab.QMUITabSegment
import com.zyx_hunan.baseview.BaseFragment
import com.zyx_hunan.wanmvvm.databinding.FragmentMainhomeBinding

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/23 0023,下午 4:10
 */
class MainHomeFragment: BaseFragment<FragmentMainhomeBinding>() {
    private var pagerMap: MutableMap<Int, Fragment>? = null
    private var tabNames = mutableMapOf(0 to "推荐", 1 to "体系", 2 to "问答", 3 to "公众号")
    override fun requestData() {

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initPagers()
    }


    private fun initPagers() {
        val homeFragment = HomeFragment()
        val knowledgeFragment = KnowledgeFragment()
        val questionFragment = QuestionFragment()
        val wechatFragment = WechatFragment()
        pagerMap = mutableMapOf(
            0 to homeFragment,
            1 to knowledgeFragment,
            2 to questionFragment,
            3 to wechatFragment
        )
        binding.contentViewPager.adapter = fragmentManager?.let {
            PagerAdapter(
                it,
                pagerMap!!
            )
        }
        binding.contentViewPager.setCurrentItem(0, false)

        val tabBuilder: QMUITabBuilder = binding.tabSegment.tabBuilder()
        for (element in tabNames){
            binding.tabSegment.addTab(tabBuilder.setText(element.value).build(context))
        }

        val space = QMUIDisplayHelper.dp2px(context, 16)
        binding.tabSegment.setIndicator(
            QMUITabIndicator(
                QMUIDisplayHelper.dp2px(context, 2), false, true
            )
        )
        binding.tabSegment.setMode(QMUITabSegment.MODE_SCROLLABLE)
        binding.tabSegment.setItemSpaceInScrollMode(space)
        binding.tabSegment.setupWithViewPager(binding.contentViewPager, false)
        binding.tabSegment.setPadding(space, 0, space, 0)
        binding.tabSegment.addOnTabSelectedListener(object : QMUITabSegment.OnTabSelectedListener {
            override fun onTabSelected(index: Int) {
                Toast.makeText(context, "select index $index", Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(index: Int) {
                Toast.makeText(context, "unSelect index $index", Toast.LENGTH_SHORT).show()
            }

            override fun onTabReselected(index: Int) {
                Toast.makeText(context, "reSelect index $index", Toast.LENGTH_SHORT).show()
            }

            override fun onDoubleTap(index: Int) {
                Toast.makeText(context, "double tap index $index", Toast.LENGTH_SHORT).show()
            }
        })

    }


    class PagerAdapter(fm: FragmentManager, private val pagerMap: MutableMap<Int, Fragment>) :
        FragmentPagerAdapter(fm) {
        override fun getCount() = pagerMap.size
        override fun getItem(position: Int): Fragment = pagerMap.get(position)!!
    }
}