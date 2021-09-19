package com.zyx_hunan.wanmvvm.ui.view

import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.databinding.ActivityMainBinding
import com.zyx_hunan.wanmvvm.logic.model.HotKeyBean
import com.zyx_hunan.wanmvvm.ui.adapter.HotKeyAdapter
import com.zyx_hunan.wanmvvm.ui.view.fragment.*
import com.zyx_hunan.wanmvvm.ui.viewmodel.HomeViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var pagerMap: MutableMap<Int, Fragment>? = null
    private val viewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
    private var checkedIndex = 0
    private var tabNames = mutableMapOf(0 to "首页", 1 to "体系", 2 to "问答", 3 to "公众号", 4 to "我的")
    private var hotKeyView: TextView? = null
    private var search: RelativeLayout? = null
    private var listItemHeight = 0
    private val mHandler = Handler(Looper.getMainLooper())
    private lateinit var list: List<HotKeyBean>
    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initTabs()
        initPagers()
        setContentView(binding.root)

        val view = LayoutInflater.from(this).inflate(R.layout.searchlayout, null, false)
        hotKeyView = view.findViewById<TextView>(R.id.hot_key)
        search = view.findViewById<RelativeLayout>(R.id.search)
        binding.topbar.addView(view)

        viewModel.hotKeyData.observe(this, {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    list = it as List<HotKeyBean>
                    list?.let { hotKeyView?.setText(list[0].name)
                    mHandler.postDelayed(task, 1000) }
                }
            }
        })
        viewModel.articleList()
    }

    private val task: Runnable = object : Runnable {
        override fun run() {
            if (index > list.size - 1 || index < 0) {
                index = 0
            }
            if (index == list.size - 1) {
                index--
            } else if (index == 0) {
                index++
            } else if (index != list.size - 1 && index != 0) {
                val random = (0..1).random()
                if (random == 0) {
                    index ++
                } else {
                    index --
                }
            }
            hotKeyView?.setText(list[index].name)
            mHandler.postDelayed(this, 3 * 1000) //延迟5秒,再次执行task本身,实现了5s一次的循环效果
        }
    }


    class PagerAdapter(fm: FragmentManager, private val pagerMap: MutableMap<Int, Fragment>) :
        FragmentPagerAdapter(fm) {
        override fun getCount() = pagerMap.size
        override fun getItem(position: Int): Fragment = pagerMap.get(position)!!
    }

    private fun initPagers() {
        val homeFragment = MainHomeFragment()
        val knowledgeFragment = KnowledgeFragment()
        val questionFragment = QuestionFragment()
        val wechatFragment = WechatFragment()
        val mineFragment = MineFragment()
        pagerMap = mutableMapOf(
            0 to homeFragment,
            1 to knowledgeFragment,
            2 to questionFragment,
            3 to wechatFragment,
            4 to mineFragment
        )
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