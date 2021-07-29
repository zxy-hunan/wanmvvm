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
import com.zyx_hunan.wanmvvm.ui.view.fragment.KnowledgeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var pagerMap: MutableMap<Int, Fragment>? = null
    private var checkedIndex=0
    private var tabNames= mutableMapOf(0 to "首页",1 to "体系",2 to "问答",3 to "公众号",4 to "我的")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initTabs()
        initPagers()
        setContentView(binding.root)
        binding.topbar.setTitle(tabNames[checkedIndex]).setTextColor(resources.getColor(R.color.qmui_config_color_gray_7))
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
        val homeFragment1 = KnowledgeFragment()
        val homeFragment2 = HomeFragment()
        val homeFragment3 = HomeFragment()
        val homeFragment4 = HomeFragment()
        pagerMap = mutableMapOf(0 to homeFragment, 1 to homeFragment1, 2 to homeFragment2, 3 to homeFragment3, 4 to homeFragment4)

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
                    R.mipmap.icon_tabbar_home
                )
            )
            .setSelectedDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.mipmap.icon_tabbar_home_selected
                )
            )
            .setText(tabNames[0])
            .build(this)
        val util = builder
            .setNormalDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_knowlage))
            .setSelectedDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.mipmap.icon_tabbar_knowlage_selected
                )
            )
            .setText(tabNames[1])
            .build(this)
        val lab = builder
            .setNormalDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_question))
            .setSelectedDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.mipmap.icon_tabbar_question_selected
                )
            )
            .setText(tabNames[2])
            .build(this)


        val wechat = builder
            .setNormalDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_wechat))
            .setSelectedDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.mipmap.icon_tabbar_wechat_selected
                )
            )
            .setText(tabNames[3])
            .build(this)


        val mine = builder
            .setNormalDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_mine))
            .setSelectedDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.mipmap.icon_tabbar_mine_selected
                )
            )
            .setText(tabNames[4])
            .build(this)

        binding.tabs
            .addTab(component)
            .addTab(util)
            .addTab(lab)
            .addTab(wechat)
            .addTab(mine)
    }
}