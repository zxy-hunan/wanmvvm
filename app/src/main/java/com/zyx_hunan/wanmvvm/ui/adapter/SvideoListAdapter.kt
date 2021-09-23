package com.zyx_hunan.wanmvvm.ui.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zyx_hunan.baseview.BaseRecyclerAdapter
import com.zyx_hunan.baseview.RecyclerViewHolder
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.compose.video.VideoModel
import com.zyx_hunan.wanmvvm.ui.view.acy.VideoPagerActivity
import java.io.Serializable

class SvideoListAdapter(private val ctx: Context, list: List<String>?,private val videos: List<VideoModel>?) :
    BaseRecyclerAdapter<String>(ctx, list) {

    override fun getItemLayoutId(viewType: Int) = R.layout.svideo_item_child

    override fun bindData(holder: RecyclerViewHolder?, position: Int, item: String) {
        holder?.let {
            val imageView = it?.getView(R.id.image) as ImageView
            holder.run {
                val playUrl = item.substring(0, item.indexOf("*>"))
                val imgUrl = item.substring(item.indexOf("*>")+2, item.indexOf("<*"))
                val title = item.substring(item.indexOf("<*")+2, item.length)
                Glide.with(itemView)
                    .load(imgUrl)
                    .into(imageView)
                setText(R.id.textView9,title)
            }
        }
        setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(itemView: View?, pos: Int) {
                val intent = Intent(ctx, VideoPagerActivity::class.java)
                var bundle=Bundle()
                bundle.putSerializable("videos",(videos as Serializable))
                bundle.putInt("pos", pos)
                intent.putExtras(bundle)
                ctx.startActivity(intent)
            }
        })
    }

}