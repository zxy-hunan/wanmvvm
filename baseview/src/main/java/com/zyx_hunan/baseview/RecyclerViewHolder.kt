package com.zyx_hunan.baseview

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/24 0024,下午 2:24
 */
class RecyclerViewHolder(ctx: Context?, itemView: View?) :
    RecyclerView.ViewHolder(itemView!!) {
    private val mViews: SparseArray<View?>
    private fun <T : View?> findViewById(viewId: Int): T? {
        var view = mViews[viewId]
        if (view == null) {
            view = itemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T?
    }

    fun getView(viewId: Int): View {
        return findViewById<View>(viewId)!!
    }

    fun getTextView(viewId: Int): TextView {
        return getView(viewId) as TextView
    }

    fun getButton(viewId: Int): Button {
        return getView(viewId) as Button
    }

    fun getImageView(viewId: Int): ImageView {
        return getView(viewId) as ImageView
    }

    fun getImageButton(viewId: Int): ImageButton {
        return getView(viewId) as ImageButton
    }

    fun getEditText(viewId: Int): EditText {
        return getView(viewId) as EditText
    }

    fun setText(viewId: Int, value: String?): RecyclerViewHolder {
        val view = findViewById<TextView>(viewId)!!
        view.text = value
        return this
    }

    fun setBackground(viewId: Int, resId: Int): RecyclerViewHolder {
        val view = findViewById<View>(viewId)!!
        view.setBackgroundResource(resId)
        return this
    }

    fun setBackgroundColor(viewId: Int, resId: Int): RecyclerViewHolder {
        val view = findViewById<View>(viewId)!!
        view.setBackgroundColor(resId)
        return this
    }



    fun setClickListener(viewId: Int, listener: View.OnClickListener?): RecyclerViewHolder {
        val view = findViewById<View>(viewId)!!
        view.setOnClickListener(listener)
        return this
    }

    init {
        mViews = SparseArray()
    }
}
