package com.zyx_hunan.wanmvvm.logic.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/21 0021,下午 5:07
 */
@Entity
data class User(
    val admin: Boolean, val chapterTops: MutableList<String>, val coinCount: Int,
    val collectIds: MutableList<String>, val icon: String, val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
) {
    @PrimaryKey(autoGenerate = true)
    var c_id: Long = 0
}