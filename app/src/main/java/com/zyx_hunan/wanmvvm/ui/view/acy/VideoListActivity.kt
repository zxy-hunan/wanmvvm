package com.zyx_hunan.wanmvvm.ui.view.acy

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.gsyvideoplayer.utils.CommonUtil
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.compose.video.RecyclerNormalAdapter
import com.zyx_hunan.wanmvvm.compose.video.ScrollCalculatorHelper
import com.zyx_hunan.wanmvvm.compose.video.VideoModel
import com.zyx_hunan.wanmvvm.databinding.ActivityVideolistBinding
import java.util.*

/**
 * @author: ZXY
 * @date: 2021/9/22
 */
class VideoListActivity : BaseActivity<ActivityVideolistBinding>() {
   lateinit var linearLayoutManager: LinearLayoutManager
    var dataList= mutableListOf<VideoModel>()
    var mFull = false

    var scrollCalculatorHelper: ScrollCalculatorHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        resolveData()
        //限定范围为屏幕一半的上下偏移180
        val playTop = CommonUtil.getScreenHeight(this) / 2 - CommonUtil.dip2px(this, 180f)
        val playBottom = CommonUtil.getScreenHeight(this) / 2 + CommonUtil.dip2px(this, 180f)
        //自定播放帮助类
        scrollCalculatorHelper = ScrollCalculatorHelper(R.id.video_item_player, playTop, playBottom)

        val recyclerNormalAdapter = RecyclerNormalAdapter(this, dataList)
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
    private fun resolveData() {
        for (i in 0..18) {
            val videoModel = VideoModel()
            dataList.add(videoModel)
        }
    }

}