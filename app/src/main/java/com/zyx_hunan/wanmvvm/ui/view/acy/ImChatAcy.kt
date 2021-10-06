package com.zyx_hunan.wanmvvm.ui.view.acy

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.content.TextContent
import cn.jpush.im.android.api.enums.ContentType
import cn.jpush.im.android.api.enums.ConversationType
import cn.jpush.im.android.api.enums.MessageDirect
import cn.jpush.im.android.api.enums.MessageStatus
import cn.jpush.im.android.api.event.MessageEvent
import cn.jpush.im.android.api.event.MessageRetractEvent
import cn.jpush.im.android.api.event.OfflineMessageEvent
import cn.jpush.im.android.api.model.Conversation
import cn.jpush.im.android.api.model.Message
import cn.jpush.im.android.api.model.UserInfo
import cn.jpush.im.api.BasicCallback
import com.zyx_hunan.baseview.BaseActivity
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.databinding.ActivityChatBinding
import com.zyx_hunan.wanmvvm.logic.model.ChatBean
import com.zyx_hunan.wanmvvm.ui.adapter.ChatAdapter

/**
 * @author: ZXY
 * @date: 2021/9/27
 */
class ImChatAcy : BaseActivity<ActivityChatBinding>() {
    var type = 0//0为单聊，1为群聊
    var userName = ""
    var nickname = ""
    var conversation: Conversation? = null
    var list: MutableList<ChatBean> = mutableListOf()
    var adapter: ChatAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = intent.getIntExtra("type", 0)
        userName = intent.getStringExtra("name").toString()
        nickname = intent.getStringExtra("nickname").toString()
        JMessageClient.registerEventReceiver(this, 0)
        binding.topbar.addLeftBackImageButton().setOnClickListener { finish() }
        initData(type)
    }

    override fun onResume() {
        super.onResume()
        JMessageClient.enterSingleConversation(userName)
    }


    override fun onPause() {
        super.onPause()
        JMessageClient.exitConversation()
    }

    override fun onDestroy() {
        super.onDestroy()
        JMessageClient.unRegisterEventReceiver(this)
    }

    /***********JMessageClient接收消息的几个关键函数**************/
    fun onEventMainThread(event: MessageEvent) {
        addMessage(event.message)
    }

    //离线消息
    fun onEvent(event: OfflineMessageEvent) {
        for (bean in event.offlineMessageList) {
            addMessage(bean)
        }
    }

    //消息被对方撤回通知事件
    fun onEvent(event: MessageRetractEvent) {
        for (bean in list) {
            if (event.retractedMessage.id == bean.message.id) {
                bean.itemType = ChatBean.RETRACT
            }
        }

    }


    //发送消息
    fun sendTextMessage(view: View) {
        var content = binding.et.text.toString()
        if (!content.isNullOrEmpty()) {
            var textContent = TextContent(content)
            var m = conversation?.createSendMessage(textContent)
            var bean = ChatBean(m, ChatBean.TEXT_SEND)
            list.add(bean)
            adapter?.itemCount?.let { adapter?.add(it, bean) }
            m?.setOnSendCompleteCallback(object : BasicCallback() {
                override fun gotResult(p0: Int, p1: String?) {
                    Log.e("chat", "result:"+p1+";p0"+p0)
                    if (p0 == 0) {
                        Toast.makeText(this@ImChatAcy,"发送成功",Toast.LENGTH_SHORT).show()
//                        adapter?.notifyItemChanged(list.size-1)
                    }
                }
            })
            JMessageClient.sendMessage(m)
        }
    }

    private fun initData(type: Int) {
        if (type == 0) {

            if (userName.isNullOrEmpty()) {
                binding.topbar.setTitle(nickname).setTextColor(resources.getColor(R.color.white))
            } else {
                binding.topbar.setTitle(userName).setTextColor(resources.getColor(R.color.white))
            }
            conversation = JMessageClient.getSingleConversation(userName)
            if (conversation == null) {
                conversation = Conversation.createSingleConversation(userName)
            }
        } else {

        }
        //获取通话记录
        if (conversation?.allMessage != null) {
            for (bean in conversation?.allMessage!!.toMutableList()) {
                addMessage(bean)
            }
        }

        initView()

    }

    private fun initView() {
        initList()

        binding.et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNullOrEmpty()) {
                    binding.tvSend.visibility = View.GONE
                } else {
                    binding.tvSend.visibility = View.VISIBLE
                }
            }

        })

    }


    private fun initList() {
        adapter = ChatAdapter(this, list)
        binding.recycleChat.adapter = adapter
        val lmanager=LinearLayoutManager(this)
        lmanager.stackFromEnd=true
        binding.recycleChat.layoutManager = lmanager
    }


    //消息加入和刷新界面
    private fun addMessage(message: Message) {
        if (message.status == MessageStatus.send_fail) {
            return
        }
        if (message.contentType == ContentType.eventNotification) {
            return
        }
        if (message.targetType == ConversationType.single) {
            val userInfo = message.targetInfo as UserInfo
            val targetId = userInfo.userName
            if (!targetId.equals(userName)) {
                return
            }
        }
        when (message.contentType) {
            ContentType.text -> {
                if (message.direct == MessageDirect.send) {
                    list.add(ChatBean(message, ChatBean.TEXT_SEND))
                } else {
                    list.add(ChatBean(message, ChatBean.TEXT_RECEIVE))
                }
                adapter?.setData(list)
            }
        }
    }

}