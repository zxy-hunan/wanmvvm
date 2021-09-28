package com.zyx_hunan.wanmvvm.ui.adapter

import android.content.Context
import cn.jpush.im.android.api.content.TextContent
import com.zyx_hunan.baseview.BaseRecyclerAdapter
import com.zyx_hunan.baseview.RecyclerViewHolder
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.logic.model.ChatBean

class ChatAdapter(private val ctx: Context, val list: List<ChatBean>?) :
    BaseRecyclerAdapter<ChatBean>(ctx, list) {

    override fun getItemLayoutId(viewType: Int): Int {
        return if (viewType == ChatBean.TEXT_SEND) {
            R.layout.item_chat_text_send
        } else if (viewType == ChatBean.TEXT_RECEIVE) {
            R.layout.item_chat_text_receive
        }else{
            R.layout.item_chat_text_send
        }
    }


    override fun bindData(holder: RecyclerViewHolder?, position: Int, item: ChatBean) {
        holder?.let {
            item?.let {
                if (it.itemType==ChatBean.TEXT_SEND){
                    holder.setText(R.id.tv,(it.message.content as TextContent).text)
                }else if (it.itemType==ChatBean.TEXT_RECEIVE){
                    holder.setText(R.id.tv,(it.message.content as TextContent).text)
                }else{

                }
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (getItem(position)?.itemType == ChatBean.TEXT_SEND) {
            ChatBean.TEXT_SEND
        } else if (getItem(position)?.itemType == ChatBean.TEXT_RECEIVE) {
            ChatBean.TEXT_RECEIVE
        } else {
            ChatBean.TEXT_RECEIVE
        }
    }


}