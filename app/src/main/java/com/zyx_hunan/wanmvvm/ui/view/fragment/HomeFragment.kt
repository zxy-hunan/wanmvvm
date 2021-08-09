package com.zyx_hunan.wanmvvm.ui.view.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout
import com.zyx_hunan.baseview.BaseFragment
import com.zyx_hunan.wanmvvm.databinding.FragmentHomeBinding
import com.zyx_hunan.wanmvvm.logic.model.Articledata
import com.zyx_hunan.wanmvvm.logic.model.Bannerdata
import com.zyx_hunan.wanmvvm.ui.adapter.ArticleListAdapter
import com.zyx_hunan.wanmvvm.ui.viewmodel.HomeViewModel

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
    private val listArticleAll = mutableListOf<Articledata>()
    private lateinit var adapter: ArticleListAdapter
    private var pullAction: QMUIPullLayout.PullAction?=null

    override fun requestData() {
        viewModel.articleList()
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            adapter = ArticleListAdapter(it, listArticleAll)
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
            binding.recyclerView.adapter = adapter
        }

        viewModel.articleData.observe(this, Observer {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    listArticleAll.addAll(it)
                    adapter.setData(listArticleAll)
                    pullAction?.let {
                        binding.pullLayout.finishActionRun(it)
                    }
                }
            }
        })

        viewModel.bannerData.observe(this, Observer { it ->
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    adapter.setBannerData(it as List<Bannerdata>)
                }
            }
        })

        binding.pullLayout.setActionListener {
            pullAction = it
            if (it.pullEdge == QMUIPullLayout.PULL_EDGE_TOP) {
                viewModel.refreshOrLoadMore(true)
            } else if (it.pullEdge == QMUIPullLayout.PULL_EDGE_BOTTOM) {
                viewModel.refreshOrLoadMore(false)
            }
        }
    }
}