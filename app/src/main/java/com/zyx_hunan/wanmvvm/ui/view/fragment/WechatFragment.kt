package com.zyx_hunan.wanmvvm.ui.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zyx_hunan.baseview.BaseFragment
import com.zyx_hunan.baseview.BaseRecyclerAdapter
import com.zyx_hunan.wanmvvm.databinding.FragmentWechatBinding
import com.zyx_hunan.wanmvvm.logic.model.AllData
import com.zyx_hunan.wanmvvm.logic.model.Articledata
import com.zyx_hunan.wanmvvm.logic.model.WcData
import com.zyx_hunan.wanmvvm.ui.adapter.ArticleListAdapter
import com.zyx_hunan.wanmvvm.ui.adapter.WechatListAdapter
import com.zyx_hunan.wanmvvm.ui.adapter.WechatListDetailAdapter
import com.zyx_hunan.wanmvvm.ui.listener.HeartListener
import com.zyx_hunan.wanmvvm.ui.viewmodel.WeChatViewModel

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/30 0030,上午 11:32
 */
class WechatFragment : BaseFragment<FragmentWechatBinding>(), HeartListener {
    private val viewModel by lazy { ViewModelProvider(this).get(WeChatViewModel::class.java) }
    private val listWeChatAll = mutableListOf<WcData>()
    private val listArticleAll = mutableListOf<AllData>()
    private lateinit var adapter: WechatListAdapter
    private lateinit var adapter1: ArticleListAdapter
    private lateinit var cid: String
    private var tempCid: String = ""
    private var page: Int = 0

    override fun requestData() {
        Log.e("BaseFragment", "requestData()")
        viewModel.getWechatList()
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
        adapter1.notifyItemChanged(pos)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            adapter = WechatListAdapter(it, listWeChatAll)
            binding.recyclerView2.layoutManager = LinearLayoutManager(activity)
            binding.recyclerView2.adapter = adapter
            adapter1 = ArticleListAdapter(it, listArticleAll)
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
            binding.recyclerView.adapter = adapter1
            adapter1.heartListener = this
            setOnclick()

            viewModel.collectData.observe(viewLifecycleOwner, Observer {
                if (it.isSuccess) {
                    Toast.makeText(activity, "操作成功", Toast.LENGTH_SHORT).show()
                }
            })



            viewModel.weChatLiveData.observe(it, Observer {
                it?.let {
                    if (it.isSuccess) {
                        it.getOrNull()?.let {
                            val listTrans = mutableListOf<WcData>()
                            val list = it as List<WcData>
                            list?.run {
                                var i = 0
                                while (i < size) {
                                    if (i == 0) {
                                        this[i].selected = true
                                    }
                                    this[i].selected = false
                                    listTrans.add(this[i])
                                    i++
                                }
                            }
                            listWeChatAll.clear()
                            listWeChatAll.addAll(listTrans)
                            adapter.setData(listWeChatAll)
                            list?.let {
                                cid = it[0].id.toString()
                                page = 1
                                viewModel.weChatDetail(cid, page)
                            }
                        }
                    }
                }
            })

            viewModel.weChatarticleLiveData.observe(it, Observer {
                if (it.isSuccess) {
                    it.getOrNull()?.let {
                        if (cid != tempCid) {
                            listArticleAll.clear()
                        }
                        tempCid = cid
                        listArticleAll.addAll(it as List<AllData>)
                        adapter1.setData(listArticleAll)
                    }
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setOnclick() {
        adapter.setOnItemClickListener(object : BaseRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(itemView: View?, pos: Int) {
                adapter.setDefaultItem(pos)
                page = 1
                cid = listWeChatAll.get(pos).id.toString()
                viewModel.weChatDetail(cid, page)
            }
        })
    }

}