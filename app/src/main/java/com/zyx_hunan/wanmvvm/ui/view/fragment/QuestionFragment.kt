package com.zyx_hunan.wanmvvm.ui.view.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout
import com.zyx_hunan.baseview.BaseFragment
import com.zyx_hunan.wanmvvm.databinding.FragmentHomeBinding
import com.zyx_hunan.wanmvvm.logic.model.AllData
import com.zyx_hunan.wanmvvm.ui.adapter.ArticleListAdapter
import com.zyx_hunan.wanmvvm.ui.listener.HeartListener
import com.zyx_hunan.wanmvvm.ui.viewmodel.QuestionViewModel

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/30 0030,上午 11:33
 */
class QuestionFragment : BaseFragment<FragmentHomeBinding>(),HeartListener {
    private lateinit var adapter: ArticleListAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(QuestionViewModel::class.java) }
    private val listArticleAll = mutableListOf<AllData>()
    private var pullAction: QMUIPullLayout.PullAction? = null

    override fun requestData() {
        viewModel.getQuestionList()
    }

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
            adapter.heartListener=this
        }

        viewModel.weChatLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    listArticleAll.addAll(it as List<AllData>)
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

        viewModel.collectData.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                Toast.makeText(activity, "操作成功", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }



}