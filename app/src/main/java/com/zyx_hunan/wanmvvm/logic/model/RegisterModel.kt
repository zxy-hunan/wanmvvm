package com.zyx_hunan.wanmvvm.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.zyx_hunan.wanmvvm.logic.database.converter.CollectidConverter

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/20 0020,下午 3:54
 */
data class RegisterModel(val data: Regdata, val errorCode: Int, val errorMsg: String)

@Entity
data class Regdata(
    val admin: Boolean,
    val chapterTops: List<ChapterTop> = listOf(),
    val coinCount: Int,
//    @SerializedName("collectIds")
//    @TypeConverters(CollectidConverter::class)
//    var collectIds: List<Long>,
    val icon: String, val id: Int,
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

data class Collectid(val t: Long)

data class ChapterTop(val t: String)