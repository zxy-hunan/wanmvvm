package com.zyx_hunan.wanmvvm.ui.view.acy

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.databinding.ActivityCollectBinding
import com.zyx_hunan.wanmvvm.logic.model.AllData
import com.zyx_hunan.wanmvvm.ui.adapter.ArticleListAdapter
import com.zyx_hunan.wanmvvm.ui.viewmodel.CollectViewModel


class CollectShareAcy : BaseActivity<ActivityCollectBinding>() {
    private val viewModel by lazy { ViewModelProvider(this).get(CollectViewModel::class.java) }
    private val listArticleAll = mutableListOf<AllData>()
    private lateinit var adapter: ArticleListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.topbar.setTitle("我的收藏").setTextColor(resources.getColor(R.color.white))
        binding.topbar.addLeftBackImageButton().setOnClickListener { finish() }
        adapter = ArticleListAdapter(this, listArticleAll)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        viewModel.articleList(0)
        viewModel.collectList.observe(this, Observer {
            Log.e("test", "openTab:wanandroid")
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    listArticleAll.addAll(it as List<AllData>)
                    adapter.setData(listArticleAll)
                }
            }
        })

    }

    override fun onResume() {
        super.onResume()
    }

}