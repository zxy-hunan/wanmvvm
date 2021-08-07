package com.zyx_hunan.wanmvvm.ui.adapter

import android.content.Context
import com.zyx_hunan.baseview.BaseRecyclerAdapter
import com.zyx_hunan.baseview.RecyclerViewHolder
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.logic.model.WcData

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/29 0029,上午 11:44
 */
class WechatListAdapter(private val ctx: Context, list: List<WcData>?) :
    BaseRecyclerAdapter<WcData>(ctx, list) {
    private var defaultItem: Int = -1

    override fun getItemLayoutId(viewType: Int) = R.layout.wechat_list_item


    override fun bindData(holder: RecyclerViewHolder?, position: Int, item: WcData) {
        holder?.let {
            with(it) {
                setText(R.id.text, item.name)
                if (defaultItem != -1) {
                    if (defaultItem == position) {
                        setBackground(R.id.layout, R.color.btn_ghost_blue_border_normal)
                    } else {
                        setBackground(R.id.layout, R.color.white)
                    }
                }
            }
        }
    }

    fun setDefaultItem(position: Int) {
        defaultItem = position
        notifyDataSetChanged()
    }

}