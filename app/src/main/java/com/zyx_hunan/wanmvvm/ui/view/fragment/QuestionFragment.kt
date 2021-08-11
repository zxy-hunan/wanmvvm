package com.zyx_hunan.wanmvvm.ui.view.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout
import com.zyx_hunan.baseview.BaseFragment
import com.zyx_hunan.wanmvvm.databinding.FragmentHomeBinding
import com.zyx_hunan.wanmvvm.logic.model.QuestionModel
import com.zyx_hunan.wanmvvm.ui.adapter.QuestionListAdapter
import com.zyx_hunan.wanmvvm.ui.viewmodel.QuestionViewModel

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/30 0030,上午 11:33
 */
class QuestionFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var adapter: QuestionListAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(QuestionViewModel::class.java) }
    private val listArticleAll = mutableListOf<QuestionModel>()
    private var pullAction: QMUIPullLayout.PullAction? = null

    override fun requestData() {
        viewModel.getQuestionList()
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            adapter = QuestionListAdapter(it, listArticleAll)
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
            binding.recyclerView.adapter = adapter
        }

        viewModel.weChatLiveData.observe(this, Observer {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    listArticleAll.addAll(it as List<QuestionModel>)
                    adapter.setData(listArticleAll)
                    pullAction?.let {
                        binding.pullLayout.finishActionRun(it)
                    }
                }
            }
        })
        binding.pullLayout.setActionListener {
            pullAction = it
            if (it.pullEdge == QMUIPullLayout.PULL_EDGE_TOP) {
                viewModel.refreshOrLoadMoreList(true)
            } else if (it.pullEdge == QMUIPullLayout.PULL_EDGE_BOTTOM) {
                viewModel.refreshOrLoadMoreList(false)
            }
        }
    }

}