package com.zyx_hunan.baseutil.expand

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/22 0022,上午 11:04
 */

fun Any.showToast(context: Context) =
    Toast.makeText(context, this.toString(), Toast.LENGTH_LONG).show()


fun Any.convertDate(): String? {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var date: String? = "null"
    when (this) {
        is Long -> date = sdf.format(Date(this))
        else -> null
    }
    return date
}

fun Any?.isNull() = when (this) {
    this is TextView -> {
        (this as TextView).isEmpty()
    }
    "" -> {
        TextUtils.isEmpty(this.toString())
    }
    null -> {
        TextUtils.isEmpty(this.toString())
    }
    else -> {
        this == null
    }
}
