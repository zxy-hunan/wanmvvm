package com.zyx_hunan.baseutil.expand

import android.content.SharedPreferences

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/8/7 0007,上午 9:28
 */
class SharedPreferences {
}


fun SharedPreferences.open(block: SharedPreferences.Editor.() -> Unit){
    val edit=this.edit()
    edit.block()
    edit.apply()
}