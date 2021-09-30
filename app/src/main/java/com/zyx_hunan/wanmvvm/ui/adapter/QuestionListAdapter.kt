package com.zyx_hunan.wanmvvm.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.zyx_hunan.baseutil.expand.convertDate
import com.zyx_hunan.baseutil.expand.isNull
import com.zyx_hunan.baseview.BaseRecyclerAdapter
import com.zyx_hunan.baseview.RecyclerViewHolder
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.logic.model.QuestionModel

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

}