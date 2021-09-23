package com.zyx_hunan.wanmvvm.compose.video

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zyx_hunan.wanmvvm.R

/**
 * @author: ZXY
 * @date: 2021/9/22
 */
class RecyclerNormalAdapter(var context: Context?,var itemDataList: MutableList<VideoModel>?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View =
            LayoutInflater.from(context).inflate(R.layout.list_video_item, parent, false)
        return RecyclerItemNormalHolder(context, v)
    }


    override fun onBindViewHolder( holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerItemViewHolder: RecyclerItemNormalHolder = holder as RecyclerItemNormalHolder
        recyclerItemViewHolder.setRecyclerBaseAdapter(this)
        recyclerItemViewHolder.onBind(position, itemDataList!![position])
    }

    override fun getItemCount(): Int {
        return itemDataList!!.size
    }


    override fun getItemViewType(position: Int): Int {
        return 1
    }

    fun setListData(data: MutableList<VideoModel>?) {
        itemDataList=data
        notifyDataSetChanged()
    }



}