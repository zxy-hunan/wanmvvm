package com.zyx_hunan.wanmvvm.ui.view.acy

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import cn.jpush.im.android.api.ContactManager
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.event.ContactNotifyEvent
import cn.jpush.im.api.BasicCallback
import com.qmuiteam.qmui.skin.QMUISkinManager
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.dialog.QMUIDialog.MessageDialogBuilder
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.databinding.ActivityMainBinding
import com.zyx_hunan.wanmvvm.logic.model.HotKeyBean
import com.zyx_hunan.wanmvvm.ui.view.fragment.*
import com.zyx_hunan.wanmvvm.ui.viewmodel.HomeViewModel
import java.io.Serializable

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var pagerMap: MutableMap<Int, Fragment>? = null
    private val viewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
    private var checkedIndex = 0
    private var tabNames = mutableMapOf(0 to "首页", 1 to "视频", 2 to "聊天", 3 to "我的")
    private var hotKeyView: TextView? = null
    private var search: RelativeLayout? = null
    private var add:ImageView?=null
    private var listItemHeight = 0
    private val mmHandler = Handler(Looper.getMainLooper())
    private lateinit var list: List<HotKeyBean>
    var index = 0
    val bReceiver = ImChatGroupFragment().UpdateFriendListReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTabs()
        initPagers()
        setContentView(binding.root)
        JMessageClient.registerEventReceiver(this, 0)
        registerBc(bReceiver)
        val view = LayoutInflater.from(this).inflate(R.layout.searchlayout, null, false)
        hotKeyView = view.findViewById<TextView>(R.id.hot_key)
        search = view.findViewById<RelativeLayout>(R.id.search)
        add = view.findViewById<ImageView>(R.id.add)
        binding.topbar.addView(view)

        viewModel.hotKeyData.observe(this, {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    list = it as List<HotKeyBean>
                    list?.let {
                        hotKeyView?.setText(list[0].name)
                        mmHandler.postDelayed(mmtask, 1000)
                    }
                }
            }
        })
        viewModel.articleList()

        search?.setOnClickListener {
            val intent = Intent(this, SearchAcy::class.java)
            if (!list.isNullOrEmpty()){
                val bundle=Bundle()
                bundle.putSerializable("list",list as Serializable)
                intent.putExtra("bundle",bundle)
            }
            startActivity(intent)
        }

        add?.setOnClickListener {
            startActivity(Intent(this, ShareArticleAcy::class.java))
        }

    }

    private val mmtask: Runnable = object : Runnable {
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
                    index++
                } else {
                    index--
                }
            }
            hotKeyView?.setText(list[index].name)
            mmHandler.postDelayed(this, 3 * 1000) //延迟5秒,再次执行task本身,实现了5s一次的循环效果
        }
    }


    class PagerAdapter(fm: FragmentManager, private val pagerMap: MutableMap<Int, Fragment>) :
        FragmentPagerAdapter(fm) {
        override fun getCount() = pagerMap.size
        override fun getItem(position: Int): Fragment = pagerMap.get(position)!!
    }

    private fun initPagers() {
        val homeFragment = MainHomeFragment()
        val videoFragment = VideoFragment()
        val imChatFragment = MainImChatFragment()
        val mineFragment = MineFragment()
        pagerMap = mutableMapOf(
            0 to homeFragment,
            1 to videoFragment,
            2 to imChatFragment,
            3 to mineFragment
        )
        binding.pager.adapter = PagerAdapter(
            supportFragmentManager,
            pagerMap!!
        )
        binding.pager.offscreenPageLimit = 5
        binding.tabs.setupWithViewPager(binding.pager, false)
    }

    private fun initTabs() {
        val builder: QMUITabBuilder = binding.tabs.tabBuilder()
        builder.setTypeface(null, Typeface.DEFAULT)
        builder.setSelectedIconScale(1.0f)
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
            .setNormalIconSizeInfo(50,50)
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


/*        val wechat = builder
            .setNormalDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_wechat))
            .setSelectedDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.mipmap.icon_tabbar_wechat_selected
                )
            )
            .setText(tabNames[3])
            .build(this)*/


        val mine = builder
            .setNormalDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_mine))
            .setSelectedDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.mipmap.icon_tabbar_mine_selected
                )
            )
            .setText(tabNames[3])
            .build(this)

        binding.tabs
            .addTab(component)
            .addTab(util)
            .addTab(lab)
//            .addTab(wechat)
            .addTab(mine)
    }


    fun onEvent(event: ContactNotifyEvent) {
        Log.e("im", "applevent:${event.type}")
        when (event.type) {
            ContactNotifyEvent.Type.invite_received -> {
                showMessagePositiveDialog(event)
            }
        }
    }


    private fun showMessagePositiveDialog(event: ContactNotifyEvent) {
        MessageDialogBuilder(this)
            .setTitle("好友申请")
            .setMessage("是否通过好友申请？")
            .setSkinManager(QMUISkinManager.defaultInstance(this))
            .addAction(
                "取消"
            ) { dialog, index -> dialog.dismiss() }
            .addAction(
                0, "确定", QMUIDialogAction.ACTION_PROP_POSITIVE
            ) { dialog, index ->
                dialog.dismiss()
                ContactManager.acceptInvitation(
                    event.fromUsername,
                    event.getfromUserAppKey(),
                    object : BasicCallback() {
                        override fun gotResult(responseCode: Int, responseMessage: String) {
                            if (0 == responseCode) {
                                Log.e("im", "applialog:添加成功")
                                sendBdMessage()
                            } else {
                            }
                        }
                    })
            }
            .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        unRegisterBc(bReceiver)
    }

}