package com.zyx_hunan.wanmvvm.ui.adapter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.youth.banner.Banner
import com.youth.banner.indicator.RoundLinesIndicator
import com.zyx_hunan.baseutil.expand.convertDate
import com.zyx_hunan.baseutil.expand.isEmpty
import com.zyx_hunan.baseutil.expand.isNull
import com.zyx_hunan.baseview.BaseRecyclerAdapter
import com.zyx_hunan.baseview.RecyclerViewHolder
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.logic.model.Articledata
import com.zyx_hunan.wanmvvm.logic.model.Bannerdata
import com.zyx_hunan.wanmvvm.ui.view.ArticleItemAcy

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/24 0024,下午 2:21
 */
class ArticleListAdapter(private val ctx: Context, val list: List<Articledata>?) :
    BaseRecyclerAdapter<Articledata>(ctx, list) {
    private var vType = 0
    private val bannerData = mutableListOf<Bannerdata>()
    private val bannerAdapter = ImageAdapter(bannerData)
    var banner: Banner<Bannerdata, ImageAdapter>? = null

    override fun getItemLayoutId(viewType: Int): Int {
        vType = viewType
        Log.e("test", "vType:$vType")
        return if (viewType == 0) {
            R.layout.banner_item
        } else {
            R.layout.article_list_item
        }
    }


    override fun bindData(holder: RecyclerViewHolder?, position: Int, articledata: Articledata) {
        Log.e("test", "bindData:vType,$vType;position,$position;item,$articledata;")
        if (vType == 1) {
            var item = list?.get(position - 1)
            item?.run {
                holder?.let {
                    val itemView =
                        it?.getView(R.id.QMUICommonListItemView) as QMUICommonListItemView
                    itemView.run {
                        textView.textSize = 16F
                        textView.letterSpacing = 0.05F
                        text = item.title
                        accessoryType = QMUICommonListItemView.ACCESSORY_TYPE_NONE
                        setTipPosition(QMUICommonListItemView.TIP_POSITION_RIGHT)
                        Log.e("test", "item.fresh=" + item.fresh)
                        showRedDot(item.fresh)
                    }
                    with(it) {
                        var authour = if (item.shareUser.isNull()) item.author else item.shareUser
                        setText(R.id.textView3, authour)
                        setText(R.id.textView4, item.publishTime.convertDate())
                        setText(R.id.textView5, "${item.superChapterName} / ${item.chapterName}")
                        setOnItemClickListener(object : OnItemClickListener {
                            override fun onItemClick(itemView: View?, pos: Int) {
                                val intent = Intent(ctx, ArticleItemAcy::class.java)
                                intent.putExtra("url", list?.get(pos-1)?.link)
                                intent.putExtra("title", list?.get(pos-1)?.title)
                                intent.putExtra("collect", list?.get(pos-1)?.collect)
                                ctx.startActivity(intent)
                            }
                        })
                    }
                }
            }
        } else {
            holder?.let {
                if (banner == null) {
                    banner = it?.getView(R.id.banner) as Banner<Bannerdata, ImageAdapter>
                    banner?.apply {
                        setBannerRound(0f)
                        indicator = RoundLinesIndicator(ctx)
                        setAdapter(bannerAdapter)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        Log.e("test", "position:$position")
        return if (position == 0) {
            0
        } else {
            1
        }
    }

    fun setBannerData(data: List<Bannerdata>) {
        data?.let {
            bannerAdapter.setDatas(data)
        }
    }
}