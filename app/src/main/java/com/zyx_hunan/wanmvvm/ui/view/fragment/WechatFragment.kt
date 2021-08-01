package com.zyx_hunan.wanmvvm.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zyx_hunan.baseview.BaseRecyclerAdapter
import com.zyx_hunan.wanmvvm.databinding.FragmentWechatBinding
import com.zyx_hunan.wanmvvm.logic.model.Articledata
import com.zyx_hunan.wanmvvm.logic.model.WcData
import com.zyx_hunan.wanmvvm.ui.adapter.WechatListAdapter
import com.zyx_hunan.wanmvvm.ui.adapter.WechatListDetailAdapter
import com.zyx_hunan.wanmvvm.ui.viewmodel.WeChatViewModel

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/30 0030,上午 11:32
 */
class WechatFragment : Fragment() {
    private lateinit var binding: FragmentWechatBinding
    private val viewModel by lazy { ViewModelProvider(this).get(WeChatViewModel::class.java) }
    private val listWeChatAll = mutableListOf<WcData>()
    private val listArticleAll = mutableListOf<Articledata>()
    private lateinit var adapter: WechatListAdapter
    private lateinit var adapter1: WechatListDetailAdapter
    private lateinit var cid: String
    private var tempCid: String=""
    private var page: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWechatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            adapter = WechatListAdapter(it, listWeChatAll)
            binding.recyclerView2.layoutManager = LinearLayoutManager(activity)
            binding.recyclerView2.adapter = adapter

            adapter1 = WechatListDetailAdapter(it, listArticleAll)
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
            binding.recyclerView.adapter = adapter1

            setOnclick()
        }

        viewModel.getWechatList()
        viewModel.weChatLiveData.observe(this, Observer {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    val listTrans= mutableListOf<WcData>()
                    val list = it as List<WcData>
                    list?.run {
                        var i=0
                        while (i<size){
                            if (i==0){
                                this[i].selected=true
                            }
                            this[i].selected=false
                            listTrans.add(this[i])
                            i++
                        }
                    }
                    listWeChatAll.addAll(listTrans)
                    adapter.setData(listWeChatAll)
                    list?.let {
                        cid = it[0].id.toString()
                        page = 1
                        viewModel.weChatDetail(cid, page)
                    }
                }
            }
        })

        viewModel.weChatarticleLiveData.observe(this, Observer {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    if (cid != tempCid){
                        listArticleAll.clear()
                    }
                    tempCid=cid
                    listArticleAll.addAll(it as List<Articledata>)
                    adapter1.setData(listArticleAll)
                }
            }
        })
    }

    private fun setOnclick() {
        adapter.setOnItemClickListener(object : BaseRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(itemView: View?, pos: Int) {
                listWeChatAll?.run {
                    var i=0
                    while (i<size){
                        this[i].selected = i==pos
                        i++
                    }
                }
                adapter.notifyDataSetChanged()

                page=1
                cid=listWeChatAll.get(pos).id.toString()
                viewModel.weChatDetail(cid, page)
            }
        })
    }
}