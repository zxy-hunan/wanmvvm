package com.zyx_hunan.wanmvvm.ui.view.acy

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.databinding.ActivitySearchBinding
import com.zyx_hunan.wanmvvm.logic.database.entity.HistoryRecord
import com.zyx_hunan.wanmvvm.logic.model.AllData
import com.zyx_hunan.wanmvvm.logic.model.Articledata
import com.zyx_hunan.wanmvvm.logic.model.HotKeyBean
import com.zyx_hunan.wanmvvm.logic.net.DataType
import com.zyx_hunan.wanmvvm.ui.adapter.ArticleListAdapter
import com.zyx_hunan.wanmvvm.ui.adapter.HistoryAdapter
import com.zyx_hunan.wanmvvm.ui.viewmodel.SearchViewModel

/**
 * @author: ZXY
 * @date: 2021/9/30
 */
class SearchAcy : BaseActivity<ActivitySearchBinding>(), TextView.OnEditorActionListener {

    private var listOfften: List<HotKeyBean>? = null
    private val viewModel by lazy { ViewModelProvider(this).get(SearchViewModel::class.java) }
    private lateinit var adapter: ArticleListAdapter
    private lateinit var hisAdapter: HistoryAdapter
    private val listArticleAll = mutableListOf<AllData>()
    private val listHisAll = mutableListOf<HistoryRecord>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.getBundleExtra("bundle")
        listOfften = bundle?.getSerializable("list") as List<HotKeyBean>
        initView()
        if (!listOfften.isNullOrEmpty()) {
            binding.history.visibility = View.VISIBLE
            binding.list.visibility = View.GONE
            drawOfften()
        } else {
            binding.history.visibility = View.GONE
            binding.list.visibility = View.VISIBLE
        }

        viewModel.historyData.observe(this, Observer {
            if(it.isSuccess){
                it.getOrNull()?.let {
                    listHisAll.addAll(it)
                    hisAdapter.setData(listHisAll)
                }
            }
        })

        viewModel.searchLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                binding.history.visibility = View.GONE
                binding.list.visibility = View.VISIBLE
                for (aData: Articledata in it) {
                    val tidyData = AllData(
                        DataType.WANARTICLE,
                        aData.author,
                        aData.chapterName,
                        aData.collect,
                        aData.fresh,
                        aData.id,
                        aData.link,
                        aData.publishTime,
                        aData.shareUser,
                        aData.superChapterName,
                        aData.title,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                    listArticleAll.add(tidyData)
                }
                adapter.setData(listArticleAll)
            }
        })
    }

    private fun initView() {
        adapter = ArticleListAdapter(this, listArticleAll)
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter
        binding.hotKey.setOnEditorActionListener(this)
        binding.textCancel.setOnClickListener {
            binding.history.visibility = View.VISIBLE
            binding.list.visibility = View.GONE
        }

        hisAdapter = HistoryAdapter(this, listHisAll)
        binding.historyList.layoutManager = LinearLayoutManager(this)
        binding.historyList.adapter = hisAdapter

    }

    private fun drawOfften() {
        binding.fbl.removeAllViews()
        listOfften?.forEach {
            val inflater = LayoutInflater.from(binding.fbl.context)
            val tv = inflater.inflate(R.layout.item_knowledge_text, binding.fbl, false) as TextView
            tv.text = it.name
            tv.setOnClickListener {
                viewModel.searchData(0, mapOf("k" to tv.text.toString()))
                binding.hotKey.setText(tv.text.toString())
            }
            binding.fbl.addView(tv)
        }
        viewModel.findHistory()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

        when (actionId) {
            EditorInfo.IME_ACTION_SEARCH -> {
                viewModel.searchData(0, mapOf("k" to binding.hotKey.text.toString()))
            }
        }
        return true
    }

}