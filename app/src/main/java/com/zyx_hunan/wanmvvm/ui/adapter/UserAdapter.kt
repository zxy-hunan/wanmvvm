package com.zyx_hunan.wanmvvm.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.zyx_hunan.wanmvvm.logic.model.Regdata

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/23 0023,下午 1:42
 */
class UserAdapter(val activity: Activity, val resId: Int,val data: List<Regdata>) :
    ArrayAdapter<Regdata>(activity, resId, data) {

    inner class ViewHolder(val strName: TextView)


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resId, parent, false)
            val regName : TextView = view.findViewById(com.zyx_hunan.wanmvvm.R.id.text)
            viewHolder = ViewHolder(regName)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val regdata = getItem(position) // 获取当前项的Fruit实例
        if (regdata != null) {
            viewHolder.strName.text = regdata.username
        }
        return view
    }
}