package com.zyx_hunan.wanmvvm.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import cn.jpush.im.android.api.content.TextContent
import cn.jpush.im.android.api.enums.ContentType
import cn.jpush.im.android.api.model.Conversation
import cn.jpush.im.android.api.model.UserInfo
import com.zyx_hunan.baseview.BaseRecyclerAdapter
import com.zyx_hunan.baseview.RecyclerViewHolder
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.ui.view.acy.ImChatAcy

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/29 0029,上午 11:44
 */
class ImChatAdapter(private val ctx: Context, list: List<Conversation>?) :
    BaseRecyclerAdapter<Conversation>(ctx, list) {

    override fun getItemLayoutId(viewType: Int) = R.layout.item_chat


    override fun bindData(holder: RecyclerViewHolder?, position: Int, item: Conversation) {
        holder?.let {
            with(it) {
                val userInfo = item.targetInfo as UserInfo
                if (userInfo?.nickname.isNullOrEmpty()){
                    setText(R.id.tv_name, userInfo.userName)
                }else{
                    setText(R.id.tv_name, userInfo.nickname)
                }
                val lastMsg = item.latestMessage
                lastMsg?.let {
                 when(it.contentType){

                     ContentType.image ->{

                     }
                     else ->{
                         setText(R.id.tv_content, (lastMsg.content as TextContent).text)
                     }
                 }
                }
            }
            setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(itemView: View?, pos: Int) {
                    val info= getItem(pos).targetInfo as UserInfo
                    val intent = Intent(ctx, ImChatAcy::class.java)
                    intent.putExtra("type", 0)
                    intent.putExtra("name", info?.userName)
                    intent.putExtra("nickname", info?.nickname)
                    ctx.startActivity(intent)
                }

            })
        }

    }

}