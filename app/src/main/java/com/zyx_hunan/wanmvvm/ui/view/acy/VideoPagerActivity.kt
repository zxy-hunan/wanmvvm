package com.zyx_hunan.wanmvvm.ui.view.acy

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.compose.video.RecyclerItemNormalHolder
import com.zyx_hunan.wanmvvm.compose.video.VideoModel
import com.zyx_hunan.wanmvvm.compose.video.ViewPagerAdapter
import com.zyx_hunan.wanmvvm.databinding.ActivityVideopagerBinding

/**
 * @author: ZXY
 * @date: 2021/9/22
 */
class VideoPagerActivity : BaseActivity<ActivityVideopagerBinding>() {
    private var dataList = mutableListOf<VideoModel>()
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var pos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val list = bundle?.getSerializable("videos") as MutableList<VideoModel>
        pos = bundle?.getInt("pos")
        initView(list)
    }

    private fun initView(list: MutableList<VideoModel>) {
        resolveData(list)
        viewPagerAdapter = ViewPagerAdapter(this, dataList)
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.viewPager2.adapter = viewPagerAdapter
        binding.viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //大于0说明有播放
                val playPosition = GSYVideoManager.instance().playPosition
                if (playPosition >= 0) {
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().playTag == RecyclerItemNormalHolder.TAG && position != playPosition) {
                        playPosition(position)
                    }
                }
            }
        })
        binding.viewPager2.post { playPosition(0) }
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
        GSYVideoManager.onResume(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    private fun resolveData(list: MutableList<VideoModel>) {
        dataList.addAll(list)
        viewPagerAdapter?.notifyDataSetChanged()
    }

    private fun playPosition(position: Int) {
        val viewHolder =
            (binding.viewPager2.getChildAt(0) as RecyclerView).findViewHolderForAdapterPosition(
                position
            )
        if (viewHolder != null) {
            val recyclerItemNormalHolder: RecyclerItemNormalHolder =
                viewHolder as RecyclerItemNormalHolder
            recyclerItemNormalHolder.getPlayer().startPlayLogic()
        }
    }

}