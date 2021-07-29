package com.zyx_hunan.wanmvvm.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayout
import com.zyx_hunan.baseview.BaseRecyclerAdapter
import com.zyx_hunan.baseview.RecyclerViewHolder
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.logic.model.Children
import java.util.*

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/29 0029,上午 11:44
 */
class KnowledgeAdapter(private val ctx: Context, list: List<Children>?) :
    BaseRecyclerAdapter<Children>(ctx, list) {
    private val mFlexItemTextViewCaches: Queue<TextView> = LinkedList()
    private var mInflater: LayoutInflater? = null
    private var mOnItemClickListener: OnItemClickListener? = null
    internal fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onClick(bean: Children, pos: Int)
    }

    override fun getItemLayoutId(viewType: Int) = R.layout.item_knowledge


    override fun bindData(holder: RecyclerViewHolder?, position: Int, item: Children) {
        holder?.let {
            with(it) {
                setText(R.id.tvknowledge, item.name)
            }
            val fbl: FlexboxLayout = holder?.getView(R.id.flexbox) as FlexboxLayout
            item.children.let {
                for (i in 0 until it.size) {
                    val childTextView = createOrGetCacheFlexItemTextView(fbl)
                    childTextView.text = it[i].name
                    childTextView.setOnClickListener {
                        mOnItemClickListener?.onClick(item, i)
                    }
                    fbl.addView(childTextView)
                }
            }
        }
    }

    private fun createOrGetCacheFlexItemTextView(fbl: FlexboxLayout): TextView {
        val tv = mFlexItemTextViewCaches.poll()
        return tv ?: createFlexItemTextView(fbl)
    }

    private fun createFlexItemTextView(fbl: FlexboxLayout): TextView {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(fbl.context)
        }
        return mInflater!!.inflate(R.layout.item_knowledge_text, fbl, false) as TextView
    }


}