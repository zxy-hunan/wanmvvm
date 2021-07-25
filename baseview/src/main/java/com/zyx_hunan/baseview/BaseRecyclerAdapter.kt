package com.zyx_hunan.baseview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/24 0024,下午 2:23
 */
abstract class BaseRecyclerAdapter<T>(ctx: Context, list:List<T>?) :
    RecyclerView.Adapter<RecyclerViewHolder?>() {
    private val mData: MutableList<T> = ArrayList()
    private val mContext: Context
    private val mInflater: LayoutInflater
    private var mClickListener: OnItemClickListener? = null
    private var mLongClickListener: OnItemLongClickListener? = null
    fun setData(list: List<T>?) {
        mData.clear()
        list?.let {
            mData.addAll(it) }
        notifyDataSetChanged()
    }

    fun remove(pos: Int) {
        mData.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val holder = RecyclerViewHolder(
            mContext,
            mInflater.inflate(getItemLayoutId(viewType), parent, false)
        )
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(View.OnClickListener {
                mClickListener!!.onItemClick(
                    holder.itemView,
                    holder.getLayoutPosition()
                )
            })
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(OnLongClickListener {
                mLongClickListener!!.onItemLongClick(holder.itemView, holder.getLayoutPosition())
                true
            })
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        bindData(holder, position, mData[position])
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    fun getItem(pos: Int): T {
        return mData[pos]
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun add(pos: Int, item: T) {
        mData.add(pos, item)
        notifyItemInserted(pos)
    }

    fun prepend(items: List<T>) {
        mData.addAll(0, items)
        notifyDataSetChanged()
    }

    fun append(items: List<T>) {
        mData.addAll(items)
        notifyDataSetChanged()
    }

    fun delete(pos: Int) {
        mData.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {
        mLongClickListener = listener
    }

    abstract fun getItemLayoutId(viewType: Int): Int
    abstract fun bindData(holder: RecyclerViewHolder?, position: Int, item: T)
    interface OnItemClickListener {
        fun onItemClick(itemView: View?, pos: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(itemView: View?, pos: Int)
    }

    init {
        list?.let {
            mData.addAll(it) }
        mContext = ctx
        mInflater = LayoutInflater.from(ctx)
    }
}