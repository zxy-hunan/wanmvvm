package com.zyx_hunan.baseutil.expand

import android.content.Context
import android.widget.Toast

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
