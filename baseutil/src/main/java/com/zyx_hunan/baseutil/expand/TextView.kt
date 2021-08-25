package com.zyx_hunan.baseutil.expand

import android.text.TextUtils
import android.widget.TextView

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/21 0021,下午 2:09
 */

fun TextView.value() = this.text.toString()

fun TextView.isEmpty()= TextUtils.isEmpty(this.text)
