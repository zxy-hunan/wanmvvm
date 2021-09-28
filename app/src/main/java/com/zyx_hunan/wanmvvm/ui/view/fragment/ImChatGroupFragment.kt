package com.zyx_hunan.wanmvvm.ui.view.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.jpush.im.android.api.model.UserInfo
import com.zyx_hunan.baseview.BaseFragment
import com.zyx_hunan.wanmvvm.databinding.FragmentImchatGroupBinding
import com.zyx_hunan.wanmvvm.ui.adapter.FriendAdapter
import com.zyx_hunan.wanmvvm.ui.view.acy.AddFriendAcy
import com.zyx_hunan.wanmvvm.ui.viewmodel.ImChatViewModel

/**
 *
 */
class ImChatGroupFragment : BaseFragment<FragmentImchatGroupBinding>() {
    private lateinit var adapter: FriendAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(ImChatViewModel::class.java) }
    var list:MutableList<UserInfo> = mutableListOf()

    override fun requestData() {
        viewModel.getContactList()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {
            viewModel.contactList.observe(it, Observer {
                it?.let {
                    list.clear()
                    list.addAll(it)
                    adapter.setData(list)
                }
            })
        }

        activity?.let {
            adapter = FriendAdapter(it, list)
            binding.recyc.layoutManager = LinearLayoutManager(activity)
            binding.recyc.adapter = adapter
        }

        binding.llNewFriend.setOnClickListener {
            startActivity(Intent(activity,AddFriendAcy::class.java))
        }


    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser){
            viewModel.getContactList()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    val handler:Handler=object:Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
        }
    }

    inner class UpdateFriendListReceiver() : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
           handler.sendEmptyMessage(0)
        }
    }

}