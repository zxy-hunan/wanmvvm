package com.zyx_hunan.wanmvvm.ui.adapter

import android.content.Context
import com.zyx_hunan.baseview.BaseRecyclerAdapter
import com.zyx_hunan.baseview.RecyclerViewHolder
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.logic.database.entity.HistoryRecord

class HistoryAdapter(private val ctx: Context, val list: List<HistoryRecord>?) :
    BaseRecyclerAdapter<HistoryRecord>(ctx, list) {

    override fun getItemLayoutId(viewType: Int): Int {
        return  R.layout.history_item
    }


    override fun bindData(holder: RecyclerViewHolder?, position: Int, item: HistoryRecord) {
        holder?.let {
            item?.let {
                holder.setText(R.id.textView16,item.title)
            }
        }
    }
}