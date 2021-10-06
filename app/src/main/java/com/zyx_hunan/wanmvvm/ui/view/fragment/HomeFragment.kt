package com.zyx_hunan.wanmvvm.ui.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout
import com.zyx_hunan.baseview.BaseFragment
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.databinding.FragmentHomeBinding
import com.zyx_hunan.wanmvvm.logic.model.AllData
import com.zyx_hunan.wanmvvm.logic.net.DataType
import com.zyx_hunan.wanmvvm.ui.adapter.ArticleListAdapter
import com.zyx_hunan.wanmvvm.ui.listener.HeartListener
import com.zyx_hunan.wanmvvm.ui.viewmodel.HomeViewModel

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/23 0023,下午 4:10
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>(), HeartListener {
    private val viewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
    private val listArticleAll = mutableListOf<AllData>()
    private lateinit var adapter: ArticleListAdapter
    private var pullAction: QMUIPullLayout.PullAction? = null
    private var wanSuccess = false//wanandroid
    private var openSuccess = false//开眼
    private var openVideoSuccess = false//小视频
    private lateinit var wanData: List<AllData>
    private lateinit var openData: List<AllData>
    private lateinit var sVideoData: List<AllData>
    private var sVideoId: Long = 0

    override fun requestData() {
        viewModel.articleList()
        viewModel.openTabFeed()
    }

    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                DataType.WANARTICLE.hashCode() -> {
                    wanSuccess = true
                    wanData = msg.obj as List<AllData>
                    this.sendEmptyMessage(0)
                }

                DataType.OPENPHOTO.hashCode() -> {
                    openSuccess = true
                    openData = msg.obj as List<AllData>
                    if (sVideoId.toInt() != 0) {
                        viewModel.videoList(sVideoId)
                    } else {
                        openVideoSuccess = true
                    }
                    this.sendEmptyMessage(0)
                }

                DataType.OPENSVIDEO.hashCode() -> {
                    openVideoSuccess = true
                    sVideoData = msg.obj as List<AllData>
                    this.sendEmptyMessage(0)
                }

                0 -> {
                    if (wanSuccess && openSuccess && openVideoSuccess)
//                        if(wanSuccess)
                        tidyData(wanData, openData, sVideoData)
                }
            }
        }
    }

    //整理数据
    private fun tidyData(
        wanData: List<AllData>,
        openData: List<AllData>,
        sVideoData: List<AllData>
    ) {
        wanSuccess = false
        openSuccess = false
        openVideoSuccess = false
        val listTidyData = mutableListOf<AllData>()
        listTidyData.addAll(wanData)
        listTidyData.addAll(openData)
        listTidyData.addAll(sVideoData)
        listTidyData.shuffle()
        listArticleAll.addAll(listTidyData)
        adapter.setData(listArticleAll)
    }


    //收藏按钮
    override fun click(pos: Int, item: Any) {
        item as AllData
        if (item.collect == true) {
            item.collect = false
            item.id?.let {
                viewModel.collect(item.id, 1)
            }
        } else {
            item.collect = true
            item.id?.let {
                viewModel.collect(item.id, 0)
            }
        }
        adapter.notifyItemChanged(pos)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {
            adapter = ArticleListAdapter(it, listArticleAll)
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
            binding.recyclerView.adapter = adapter
            adapter.heartListener = this
        }

        viewModel.collectData.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                Toast.makeText(activity, "操作成功", Toast.LENGTH_SHORT).show()
            }
        })

        //wanAndroid文章数据列表
        viewModel.articleData.observe(viewLifecycleOwner, Observer {
            Log.e("test", "openTab:wanandroid")
            if (it.isSuccess) {
                it.getOrNull()?.let {
//                        listArticleAll.addAll(it)
                    mHandler.sendMessage(getMessage(DataType.WANARTICLE.hashCode(), it))

                    pullAction?.let {
                        binding.pullLayout.finishActionRun(it)
                    }
                }
            }
        })

        //开眼数据列表
        viewModel.openFeedTabLiveData.observe(viewLifecycleOwner, Observer {
            Log.e("test", "openTab:${it.nextPageUrl}")
            var openDataList = mutableListOf<AllData>()
            it?.let {
                var nextPage = it.nextPageUrl
                if (nextPage.isNullOrEmpty()) {
                    viewModel.date.postValue(0)
                    viewModel.num.postValue(0)
                } else {
                    var date =
                        nextPage.substring(nextPage.indexOf("date=") + 5, nextPage.indexOf("&"))
                    var num = nextPage.substring(nextPage.indexOf("&num=") + 5, nextPage.length)
                    viewModel.date.postValue(date?.toLong())
                    viewModel.num.postValue(num?.toLong())
                }

                for (item in it.itemList) {
                    item.data.content?.data?.let {
                        var openData: AllData? = null
                        if (it.dataType == "VideoBeanForClient") {
                            if (it.id.toInt() != 0) {
                                sVideoId = it.id
                            }
                            openData = AllData(
                                DataType.OPENVIDEO,
                                it.id,
                                it.title,
                                it.description,
                                it.playUrl,
                                it.url,
                                it.urls,
                                it.cover?.feed
                            )
                        } else if (it.dataType == "UgcPictureBean") {
                            openData = AllData(
                                DataType.OPENPHOTO,
                                it.id,
                                it.title,
                                it.description,
                                it.playUrl,
                                it.url,
                                it.urls,
                                it.cover?.feed
                            )
                        }
                        if (openData != null) {
                            openDataList.add(openData)
                        }
                    }
                }
            }
            mHandler.sendMessage(getMessage(DataType.OPENPHOTO.hashCode(), openDataList))
        })


        //小视频数据
        viewModel.videoData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    var sVideoList = mutableListOf<AllData>()
                    var urls = mutableListOf<String>()
                    for (item in it) {
                        if (item.id?.toInt() != 0) {
                            item.playUrl?.let { urls.add(item.playUrl + "*>" + item.feed + "<*" + item.title) }
                        }
                    }
                    var openData = AllData(
                        DataType.OPENSVIDEO,
                        urls, it
                    )
                    sVideoList.add(openData)
                    mHandler.sendMessage(getMessage(DataType.OPENSVIDEO.hashCode(), sVideoList))
                }
            }
        })


        //刷新或加载
        binding.pullLayout.setActionListener {
            pullAction = it
            if (it.pullEdge == QMUIPullLayout.PULL_EDGE_TOP) {
                viewModel.refreshOrLoadMore(true)
            } else if (it.pullEdge == QMUIPullLayout.PULL_EDGE_BOTTOM) {
                viewModel.refreshOrLoadMore(false)
            }
        }
    }


    private fun getMessage(dataType: Int, any: Any): Message {
        var msg = Message.obtain()
        msg.what = dataType
        msg.obj = any
        return msg
    }


}