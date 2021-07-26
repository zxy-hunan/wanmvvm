package com.zyx_hunan.wanmvvm.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/20 0020,下午 3:54
 */
data class BannerModel(val data: List<Bannerdata>, val errorCode: Int, val errorMsg: String)

data class Bannerdata(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)