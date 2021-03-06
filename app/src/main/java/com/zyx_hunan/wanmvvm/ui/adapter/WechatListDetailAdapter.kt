package com.zyx_hunan.wanmvvm.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.zyx_hunan.baseutil.expand.convertDate
import com.zyx_hunan.baseutil.expand.isNull
import com.zyx_hunan.baseview.BaseRecyclerAdapter
import com.zyx_hunan.baseview.RecyclerViewHolder
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.logic.model.Articledata
import com.zyx_hunan.wanmvvm.ui.view.acy.ArticleItemAcy

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/30 0030,下午 3:17
 */
class WechatListDetailAdapter(private val ctx: Context, val list: List<Articledata>?) :
    BaseRecyclerAdapter<Articledata>(ctx, list) {

    override fun getItemLayoutId(viewType: Int): Int {
        return R.layout.article_list_item
    }


    override fun bindData(holder: RecyclerViewHolder?, position: Int, articledata: Articledata) {
        holder?.let {
            var item = list?.get(position)
            item?.run {
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
        setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(itemView: View?, pos: Int) {
                val intent = Intent(ctx, ArticleItemAcy::class.java)
                intent.putExtra("url", list?.get(pos)?.link)
                intent.putExtra("title", list?.get(pos)?.title)
                intent.putExtra("collect", list?.get(pos)?.collect)
                ctx.startActivity(intent)
            }
        })
    }
}