package com.zyx_hunan.wanmvvm.ui.view.acy

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.CommonUtil
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.compose.video.RecyclerNormalAdapter
import com.zyx_hunan.wanmvvm.compose.video.ScrollCalculatorHelper
import com.zyx_hunan.wanmvvm.compose.video.VideoModel
import com.zyx_hunan.wanmvvm.databinding.ActivityVideolistBinding
import com.zyx_hunan.wanmvvm.ui.viewmodel.VideoViewModel

/**
 * @author: ZXY
 * @date: 2021/9/22
 */
class VideoListActivity : BaseActivity<ActivityVideolistBinding>() {
    lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel by lazy { ViewModelProvider(this).get(VideoViewModel::class.java) }
    private var dataList = mutableListOf<VideoModel>()
    var mFull = false
    var scrollCalculatorHelper: ScrollCalculatorHelper? = null
    private lateinit var videotex: VideoModel
    private lateinit var recyclerNormalAdapter: RecyclerNormalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        videotex = intent.getSerializableExtra("videotex") as VideoModel
        initView()
        videotex.id?.let {
            viewModel.videoList(it)
        }
        viewModel.videoData.observe(this, androidx.lifecycle.Observer {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    dataList.addAll(it)
                    recyclerNormalAdapter.setListData(dataList)
                }
            }
        })
    }

    private fun initView() {
        dataList.add(videotex)
        //限定范围为屏幕一半的上下偏移180
        val playTop = CommonUtil.getScreenHeight(this) / 2 - CommonUtil.dip2px(this, 180f)
        val playBottom = CommonUtil.getScreenHeight(this) / 2 + CommonUtil.dip2px(this, 180f)
        //自定播放帮助类
        scrollCalculatorHelper = ScrollCalculatorHelper(R.id.video_item_player, playTop, playBottom)

        recyclerNormalAdapter = RecyclerNormalAdapter(this, dataList)
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = recyclerNormalAdapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var firstVisibleItem = 0
            var lastVisibleItem = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrollCalculatorHelper?.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                firstVisibleItem = linearLayoutManager?.findFirstVisibleItemPosition()
                lastVisibleItem = linearLayoutManager?.findLastVisibleItemPosition()

                //这是滑动自动播放的代码
                if (!mFull) {
                    scrollCalculatorHelper?.onScroll(
                        recyclerView,
                        firstVisibleItem,
                        lastVisibleItem,
                        lastVisibleItem - firstVisibleItem
                    )
                }
            }
        })


    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        mFull = newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER
    }

    override fun onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

}