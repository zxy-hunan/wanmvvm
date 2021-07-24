package com.zyx_hunan.wanmvvm.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout
import com.zyx_hunan.wanmvvm.databinding.FragmentHomeBinding
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
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
    private lateinit var adapter: ArticleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        activity?.let { adapter = ArticleListAdapter(it, null)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter
        }
        viewModel.articleList(1)
        viewModel.articleData.observe(this, Observer {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    adapter.setData(it)
                }
            }
        })


        binding.pullLayout.setActionListener {

            if (it.pullEdge == QMUIPullLayout.PULL_EDGE_TOP) {
                viewModel.refreshOrLoadMore(true)
            } else if (it.pullEdge == QMUIPullLayout.PULL_EDGE_BOTTOM) {
                viewModel.refreshOrLoadMore(false)
            }
            binding.pullLayout.finishActionRun(it)

        }
    }
}