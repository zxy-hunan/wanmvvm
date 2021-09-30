package com.zyx_hunan.wanmvvm.logic.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author: ZXY
 * @date: 2021/9/30
 */
@Entity
data class HistoryRecord(val title:String,val type:Int) {
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}