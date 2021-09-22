package com.zyx_hunan.wanmvvm.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.youth.banner.Banner
import com.youth.banner.indicator.RoundLinesIndicator
import com.zyx_hunan.baseutil.expand.convertDate
import com.zyx_hunan.baseutil.expand.isNull
import com.zyx_hunan.baseview.BaseRecyclerAdapter
import com.zyx_hunan.baseview.RecyclerViewHolder
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.logic.model.AllData
import com.zyx_hunan.wanmvvm.logic.model.Bannerdata
import com.zyx_hunan.wanmvvm.logic.model.Data
import com.zyx_hunan.wanmvvm.logic.net.DataType
import com.zyx_hunan.wanmvvm.ui.view.ArticleItemAcy
import com.zyx_hunan.wanmvvm.ui.view.acy.VideoListActivity

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/24 0024,下午 2:21
 */
class ArticleListAdapter(private val ctx: Context, val list: List<AllData>?) :
    BaseRecyclerAdapter<AllData>(ctx, list) {
    private val bannerData = mutableListOf<AllData>()
    private val bannerAdapter = ImageAdapter(bannerData)
    var banner: Banner<AllData, ImageAdapter>? = null

    override fun getItemLayoutId(viewType: Int): Int {
        return if (viewType == DataType.WANARTICLE.hashCode()) {
            R.layout.article_list_item
        } else if (viewType == DataType.OPENVIDEO.hashCode()) {
            R.layout.openvideolayout
        } else if (viewType == DataType.OPENPHOTO.hashCode()) {
            R.layout.banner_item
        } else {
            R.layout.openvideolayout
        }
    }


    override fun bindData(holder: RecyclerViewHolder?, position: Int, item: AllData) {
        if (item.type == DataType.WANARTICLE) {
            item?.run {
                holder?.let {
                    val freshText = it?.getView(R.id.textView2) as TextView
                    with(it) {
                        if (item.fresh) freshText.visibility =
                            View.VISIBLE else freshText.visibility = View.GONE

                        var authour = if (item.shareUser.isNull()) item.author else item.shareUser
                        setText(R.id.QMUICommonListItemView, item.title)
                        setText(R.id.textView3, authour)
                        setText(R.id.textView4, item.publishTime?.convertDate())
                        setText(R.id.textView5, "${item.superChapterName} / ${item.chapterName}")
                    }
                }
            }
        } else if (item.type == DataType.OPENVIDEO) {
            item?.run {
                holder?.let {
                    val image = it?.getView(R.id.im_cover) as ImageView
                    with(it) {
                        setText(R.id.tv_title, item.title)
                        setText(R.id.tv_desc, item.description)
                        Glide.with(holder.itemView)
                            .load(item.link)
                            .into(image)
                    }
                }
            }
        } else if (item.type == DataType.OPENPHOTO) {
            holder?.let {
                with(it) {
                    if (banner == null) {
                        banner = it?.getView(R.id.banner) as Banner<AllData, ImageAdapter>
                        banner?.apply {
                            setBannerRound(0f)
                            indicator = RoundLinesIndicator(ctx)
                            setAdapter(bannerAdapter)
                            setBannerData()

                        }
                    }
                    if (item.description.isNullOrEmpty()) {
                        setText(R.id.text, item.title)
                    } else {
                        setText(R.id.text, item.description)
                    }
                }
            }
        } else {

        }

        setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(itemView: View?, pos: Int) {
                Log.e("datatype", "WANARTICLE点击了")
                Log.e(
                    "datatype",
                    "onItemClick;pos,$pos;list?.get(pos):,${list?.get(pos)?.toString()};"
                )
                list?.get(pos)?.let {
                    if (it.type == DataType.WANARTICLE) {
                        val intent = Intent(ctx, ArticleItemAcy::class.java)
                        intent.putExtra("url", it.link)
                        intent.putExtra("title", it.title)
                        intent.putExtra("collect", it.collect)
                        ctx.startActivity(intent)
                    }else if (it.type == DataType.OPENVIDEO){
                        val intent = Intent(ctx, VideoListActivity::class.java)
                        ctx.startActivity(intent)
                    }else{

                    }
                }

            }
        })
    }

    override fun getItemViewType(position: Int): Int {
        Log.e("test", "position:$position")
        return if (list?.get(position)?.type == DataType.WANARTICLE) {
            DataType.WANARTICLE.hashCode()
        } else if (list?.get(position)?.type == DataType.OPENVIDEO) {
            DataType.OPENVIDEO.hashCode()
        } else if (list?.get(position)?.type == DataType.OPENPHOTO) {
            DataType.OPENPHOTO.hashCode()
        } else {
            DataType.OPENCARD.hashCode()
        }
    }

    private fun setBannerData() {
        list?.let {
            for (imageData in list) {
                if (imageData.type == DataType.OPENPHOTO) {
                    bannerData.add(imageData)
                }
            }
            bannerAdapter.setDatas(bannerData)
        }
    }
}