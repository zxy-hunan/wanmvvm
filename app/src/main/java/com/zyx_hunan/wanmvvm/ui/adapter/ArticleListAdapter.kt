package com.zyx_hunan.wanmvvm.ui.adapter

import android.content.Context
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.zyx_hunan.baseutil.expand.convertDate
import com.zyx_hunan.baseview.BaseRecyclerAdapter
import com.zyx_hunan.baseview.RecyclerViewHolder
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.logic.model.Articledata

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/24 0024,下午 2:21
 */
class ArticleListAdapter(ctx: Context, list: List<Articledata>?) :
    BaseRecyclerAdapter<Articledata>(ctx, list) {
    override fun getItemLayoutId(viewType: Int) = R.layout.article_list_item


    override fun bindData(holder: RecyclerViewHolder?, position: Int, item: Articledata) {
        holder?.let {
            val itemView = it?.getView(R.id.QMUICommonListItemView) as QMUICommonListItemView
            itemView.run {
                text = item.title
                accessoryType = QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
            }
            with(it) {
                if (item.fresh){ setText(R.id.textView2, "新")}
                setText(R.id.textView3, item.shareUser)
                setText(R.id.textView4, item.publishTime.convertDate())
                setText(R.id.textView5, "${item.superChapterName} / ${item.chapterName}")

            }
        }
    }
}