package com.zyx_hunan.wanmvvm.ui.view.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.enums.ConversationType
import cn.jpush.im.android.api.event.MessageEvent
import cn.jpush.im.android.api.model.Conversation
import cn.jpush.im.android.api.model.UserInfo
import com.zyx_hunan.baseview.BaseFragment
import com.zyx_hunan.wanmvvm.databinding.FragmentImchatBinding
import com.zyx_hunan.wanmvvm.logic.model.QuestionModel
import com.zyx_hunan.wanmvvm.ui.adapter.ImChatAdapter
import com.zyx_hunan.wanmvvm.ui.viewmodel.ImChatViewModel

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/30 0030,上午 11:33
 */
class ImChatFragment : BaseFragment<FragmentImchatBinding>() {
    private lateinit var adapter: ImChatAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(ImChatViewModel::class.java) }
    private val listArticleAll = mutableListOf<QuestionModel>()
    var list = mutableListOf<Conversation>()

    override fun requestData() {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        JMessageClient.registerEventReceiver(this)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        JMessageClient.unRegisterEventReceiver(this)
    }

    override fun onResume() {
        super.onResume()
    }


    public fun onEventMainThread(event: MessageEvent) {
        var handlable = false
        var msg = event.message
        if (msg.getTargetType() == ConversationType.single) {
            var userInfo = msg.targetInfo as UserInfo
            for (bean in list) {
                if (bean.type == ConversationType.single) {
                    var user = bean.targetInfo as UserInfo

                    if (user.userName.equals(userInfo.userName)) {

                        bean.updateConversationExtra("new")

                        handlable = true

                        adapter?.notifyItemChanged(list.indexOf(bean))

                    }
                }
            }

            if (!handlable) {
                var conversation = JMessageClient.getSingleConversation(userInfo.userName)
                if (conversation.targetInfo is UserInfo) {
                    var bean = conversation
                    bean.updateConversationExtra("new")
                    list.add(bean)

                }
//                adapter?.notifyItemInserted(list.size - 1)
            }
        }

//        checkNew()

    }


    private fun initView() {
        activity?.let {
            adapter = ImChatAdapter(it, list)
            binding.recyc.layoutManager = LinearLayoutManager(activity)
            binding.recyc.adapter = adapter
            binding.recyc.setHasFixedSize(true)
            loadData()
        }
    }

    private fun loadData() {
        list.clear()
        var rList = JMessageClient.getConversationList()
        rList?.let {
            list.addAll(it)
            adapter.setData(list)
        }
    }

}