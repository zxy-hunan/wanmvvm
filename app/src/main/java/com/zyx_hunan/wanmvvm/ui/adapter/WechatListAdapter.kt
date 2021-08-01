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

    override fun getItemLayoutId(viewType: Int) = R.layout.simple_list_item


    override fun bindData(holder: RecyclerViewHolder?, position: Int, item: WcData) {
        holder?.let {
            with(it) {
                setText(R.id.text, item.name)
                if (!item.selected){
                    setBackgroundColor(R.id.text,R.color.btn_ghost_blue_border_normal)
                }else{
                    setBackgroundColor(R.id.text,R.color.white)
                }
            }
        }
    }

}