package com.zyx_hunan.wanmvvm.ui.view

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.tab.QMUIBasicTabSegment
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.databinding.ActivityMainBinding
import com.zyx_hunan.wanmvvm.ui.view.fragment.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var pagerMap: MutableMap<Int, Fragment>? = null
    private var checkedIndex=0
    private var tabNames= mutableMapOf(0 to "首页",1 to "项目",2 to "问答")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initTabs()
        initPagers()
        setContentView(binding.root)
        binding.topbar.setTitle(tabNames[checkedIndex])
        binding.tabs.addOnTabSelectedListener(object :QMUIBasicTabSegment.OnTabSelectedListener{
            //被选中
            override fun onTabSelected(index: Int) {
                binding.topbar.setTitle(tabNames[index])
            }

            //被取消
            override fun onTabUnselected(index: Int) {
            }

            //选中状态下再次被选中
            override fun onTabReselected(index: Int) {
            }

            //双击时
            override fun onDoubleTap(index: Int) {
            }

        })
    }

    class PagerAdapter(fm: FragmentManager, val pagerMap: MutableMap<Int, Fragment>) :
        FragmentPagerAdapter(fm) {
        override fun getCount() = pagerMap.size
        override fun getItem(position: Int): Fragment = pagerMap.get(position)!!
    }


    private fun initPagers() {
        val homeFragment = HomeFragment()
        val homeFragment1 = HomeFragment()
        val homeFragment2 = HomeFragment()
        pagerMap = mutableMapOf(0 to homeFragment, 1 to homeFragment1, 2 to homeFragment2)

        binding.pager.adapter = PagerAdapter(
            supportFragmentManager,
            pagerMap!!
        )
        binding.tabs.setupWithViewPager(binding.pager, false)
    }

    private fun initTabs() {
        val builder: QMUITabBuilder = binding.tabs.tabBuilder()
        builder.setTypeface(null, Typeface.DEFAULT_BOLD)
        builder.setSelectedIconScale(1.2f)
            .setTextSize(
                QMUIDisplayHelper.sp2px(this, 13),
                QMUIDisplayHelper.sp2px(this, 15)
            )
            .setDynamicChangeIconColor(false)
        val component = builder
            .setNormalDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.mipmap.icon_tabbar_component
                )
            )
            .setSelectedDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.mipmap.icon_tabbar_component_selected
                )
            )
            .setText(tabNames[0])
            .build(this)
        val util = builder
            .setNormalDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_util))
            .setSelectedDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.mipmap.icon_tabbar_util_selected
                )
            )
            .setText(tabNames[1])
            .build(this)
        val lab = builder
            .setNormalDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_lab))
            .setSelectedDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.mipmap.icon_tabbar_lab_selected
                )
            )
            .setText(tabNames[2])
            .build(this)
        binding.tabs.addTab(component)
            .addTab(util)
            .addTab(lab)
    }
}