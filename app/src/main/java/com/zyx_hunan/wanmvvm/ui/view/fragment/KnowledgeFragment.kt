package com.zyx_hunan.wanmvvm.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zyx_hunan.wanmvvm.databinding.FragmentKnowledgeBinding
import com.zyx_hunan.wanmvvm.logic.model.Children
import com.zyx_hunan.wanmvvm.ui.adapter.KnowledgeAdapter
import com.zyx_hunan.wanmvvm.ui.viewmodel.KnowledgeViewModel

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/29 0029,上午 10:55
 */
class KnowledgeFragment : Fragment() {
    private lateinit var binding: FragmentKnowledgeBinding
    private val viewModel by lazy { ViewModelProvider(this).get(KnowledgeViewModel::class.java) }
    private val listKnowledgeAll = mutableListOf<Children>()
    private lateinit var adapter: KnowledgeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKnowledgeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            adapter = KnowledgeAdapter(it, listKnowledgeAll)
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
            binding.recyclerView.adapter = adapter
        }
        viewModel.getKnowLedgeList()
        viewModel.knowledgeLiveData.observe(this, Observer {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    listKnowledgeAll.addAll(it as List<Children>)
                    adapter.setData(listKnowledgeAll)
                }
            }
        })
    }
}