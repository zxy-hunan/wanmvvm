package com.zyx_hunan.wanmvvm.ui.view

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.databinding.ActivityMainBinding
import com.zyx_hunan.wanmvvm.ui.view.fragment.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var pagerMap: MutableMap<Int, Fragment>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initTabs()
        initPagers()
        setContentView(binding.root)
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
            .setText("首页")
            .build(this)
        val util = builder
            .setNormalDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_util))
            .setSelectedDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.mipmap.icon_tabbar_util_selected
                )
            )
            .setText("项目")
            .build(this)
        val lab = builder
            .setNormalDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_lab))
            .setSelectedDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.mipmap.icon_tabbar_lab_selected
                )
            )
            .setText("问答")
            .build(this)
        binding.tabs.addTab(component)
            .addTab(util)
            .addTab(lab)
    }
}