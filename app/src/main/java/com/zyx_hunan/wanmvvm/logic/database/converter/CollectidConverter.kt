package com.zyx_hunan.wanmvvm.logic.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
    fun stringToList(data: Long?): List<Long>? {
        if (data == null) {
            return listOf()
        }

        val listType = object : TypeToken<List<Long>>() {}.type

        return gson.fromJson(data.toString(), listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<Long>): String {
        return if(someObjects==null){
            ""
        }else {
            gson.toJson(someObjects)
        }
    }

}