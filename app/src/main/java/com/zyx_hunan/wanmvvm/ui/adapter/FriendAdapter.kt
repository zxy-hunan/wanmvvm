package com.zyx_hunan.wanmvvm.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import cn.jpush.im.android.api.model.UserInfo
import com.zyx_hunan.baseview.BaseRecyclerAdapter
import com.zyx_hunan.baseview.RecyclerViewHolder
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.ui.view.acy.FriendDetailAcy

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/29 0029,上午 11:44
 */
class FriendAdapter(private val ctx: Context, list: List<UserInfo>?) :
    BaseRecyclerAdapter<UserInfo>(ctx, list) {

    override fun getItemLayoutId(viewType: Int) = R.layout.item_group_chat


    override fun bindData(holder: RecyclerViewHolder?, position: Int, item: UserInfo) {
        holder?.let {
            with(it) {
                if (item.nickname.isNullOrEmpty()) {
                    setText(R.id.tv, item.userName)
                } else {
                    setText(R.id.tv, item.nickname)
                }
            }
            setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(itemView: View?, pos: Int) {
                    val intent = Intent(ctx, FriendDetailAcy::class.java)
                    intent.putExtra("name",getItem(pos).userName)
                    ctx.startActivity(intent)
                }

            })
        }

    }

}