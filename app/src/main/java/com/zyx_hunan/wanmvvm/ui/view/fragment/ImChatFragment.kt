package com.zyx_hunan.wanmvvm.ui.view.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout
import com.zyx_hunan.baseview.BaseFragment
import com.zyx_hunan.wanmvvm.databinding.FragmentImchatGroupBinding
import com.zyx_hunan.wanmvvm.logic.model.QuestionModel
import com.zyx_hunan.wanmvvm.ui.adapter.QuestionListAdapter
import com.zyx_hunan.wanmvvm.ui.viewmodel.ImChatViewModel

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/30 0030,上午 11:33
 */
class ImChatFragment : BaseFragment<FragmentImchatGroupBinding>() {
    private lateinit var adapter: QuestionListAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(ImChatViewModel::class.java) }
    private val listArticleAll = mutableListOf<QuestionModel>()
    private var pullAction: QMUIPullLayout.PullAction? = null

    override fun requestData() {
    }

    override fun onResume() {
        super.onResume()

    }

}