package com.zyx_hunan.wanmvvm.logic.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zyx_hunan.wanmvvm.logic.model.ChapterTop
import com.zyx_hunan.wanmvvm.logic.model.Collectid
import java.util.*

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/22 0022,下午 1:50
 */
class CollectidConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<Collectid>? {
        if (data == null) {
            return listOf()
        }

        val listType = object : TypeToken<List<Collectid>>() {}.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<Collectid>): String {
        return if(someObjects==null){
            ""
        }else {
            gson.toJson(someObjects)
        }
    }

}