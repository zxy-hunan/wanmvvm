package com.zyx_hunan.wanmvvm.ui.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout
import com.zyx_hunan.baseview.BaseFragment
import com.zyx_hunan.wanmvvm.databinding.FragmentHomeBinding
import com.zyx_hunan.wanmvvm.logic.model.AllData
import com.zyx_hunan.wanmvvm.logic.net.DataType
import com.zyx_hunan.wanmvvm.ui.adapter.ArticleListAdapter
import com.zyx_hunan.wanmvvm.ui.viewmodel.HomeViewModel
import java.util.*

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/23 0023,下午 4:10
 */
class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    private val viewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
    private val listArticleAll = mutableListOf<AllData>()
    private lateinit var adapter: ArticleListAdapter
    private var pullAction: QMUIPullLayout.PullAction?=null
    private var wanSuccess=false
    private var openSuccess=false
    private lateinit var wanData:List<AllData>
    private lateinit var openData:List<AllData>

    override fun requestData() {
        viewModel.articleList()
        viewModel.openTabFeed()
    }

    private val mHandler=object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            when(msg.what){
                DataType.WANARTICLE.hashCode()->{
                    wanSuccess=true
                    wanData=msg.obj as List<AllData>
                    this.sendEmptyMessage(0)
                }

                DataType.OPENPHOTO.hashCode()->{
                    openSuccess=true
                    openData=msg.obj as List<AllData>
                    this.sendEmptyMessage(0)
                }

                0->{
                    if(wanSuccess&&openSuccess)
//                        if(wanSuccess)
                    tidyData(wanData,openData)
                }
            }
        }
    }

    private fun tidyData(wanData:List<AllData>,openData:List<AllData>){
        wanSuccess=false
        openSuccess=false
        val listTidyData = mutableListOf<AllData>()
        listTidyData.addAll(wanData)
        listTidyData.addAll(openData)
        listTidyData.shuffle()
        listArticleAll.addAll(listTidyData)
        adapter.setData(listArticleAll)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {
            adapter = ArticleListAdapter(it, listArticleAll)
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
            binding.recyclerView.adapter = adapter
        }

        //wanAndroid文章数据列表
        viewModel.articleData.observe(viewLifecycleOwner, Observer {
            Log.e("test","openTab:wanandroid")
                if (it.isSuccess) {
                    it.getOrNull()?.let {
//                        listArticleAll.addAll(it)
                        mHandler.sendMessage(getMessage(DataType.WANARTICLE.hashCode(),it))

                        pullAction?.let {
                            binding.pullLayout.finishActionRun(it)
                        }
                    }
                }
        })

        //banner
 /*       viewModel.bannerData.observe(this, Observer { it ->
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    adapter.setBannerData(it as List<Bannerdata>)
                }
            }
        })*/



        //开眼数据列表
        viewModel.openFeedTabLiveData.observe(viewLifecycleOwner, Observer {
            Log.e("test","openTab:${it.nextPageUrl}")
            var openDataList=mutableListOf<AllData>()
            it?.let {
                for (item in it.itemList){
                    item.data.content?.data?.let {
                        var openData:AllData?=null
                        if (it.dataType=="VideoBeanForClient"){
                            openData= AllData(DataType.OPENVIDEO,it.id,it.title,it.description
                            ,it.playUrl,it.url,it.urls,it.cover?.feed)
                        }else if (it.dataType=="UgcPictureBean"){
                            openData= AllData(DataType.OPENPHOTO,it.id,it.title,it.description
                                ,it.playUrl,it.url,it.urls,it.cover?.feed)
                        }
                        /*else{
                            openData= AllData(DataType.OPENCARD,it.id,it.title,it.description
                                ,it.playUrl,it.url,it.urls,it.cover?.feed)
                        }*/
                        if (openData != null) {
                            openDataList.add(openData)
                        }
                    }
                }
            }
            mHandler.sendMessage(getMessage(DataType.OPENPHOTO.hashCode(),openDataList))
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


    private fun getMessage(dataType:Int, any: Any):Message{
        var msg=Message.obtain()
        msg.what=dataType
        msg.obj=any
        return msg
    }
}