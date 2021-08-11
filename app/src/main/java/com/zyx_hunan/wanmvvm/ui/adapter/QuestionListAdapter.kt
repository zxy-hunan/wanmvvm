package com.zyx_hunan.wanmvvm.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.zyx_hunan.baseutil.expand.convertDate
import com.zyx_hunan.baseview.BaseRecyclerAdapter
import com.zyx_hunan.baseview.RecyclerViewHolder
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.logic.model.QuestionModel
import com.zyx_hunan.wanmvvm.ui.view.ArticleItemAcy

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/24 0024,下午 2:21
 */
class QuestionListAdapter(private val ctx: Context, list: List<QuestionModel>?) :
    BaseRecyclerAdapter<QuestionModel>(ctx, list) {

    override fun getItemLayoutId(viewType: Int)= R.layout.article_list_item



    override fun bindData(holder: RecyclerViewHolder?, position: Int, item: QuestionModel) {
            holder?.let {
                val itemView = it?.getView(R.id.QMUICommonListItemView) as QMUICommonListItemView
                itemView.run {
                    textView.textSize = 16F
                    textView.letterSpacing = 0.05F
                    text = item.title
                    accessoryType = QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
                    setTipPosition(QMUICommonListItemView.TIP_POSITION_LEFT)
                    showNewTip(item.fresh)
                }
                with(it) {
                    setText(R.id.textView3, item.shareUser)
                    setText(R.id.textView4, item.publishTime.convertDate())
                    setText(R.id.textView5, "${item.superChapterName} / ${item.chapterName}")
                    setOnItemClickListener(object : OnItemClickListener {
                        override fun onItemClick(itemView: View?, pos: Int) {
                            val intent = Intent(ctx, ArticleItemAcy::class.java)
                            intent.putExtra("url", item.link)
                            ctx.startActivity(intent)
                        }
                    })
                }
            }
    }

}