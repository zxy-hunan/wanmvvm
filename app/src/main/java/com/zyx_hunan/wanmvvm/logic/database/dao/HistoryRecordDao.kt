package com.zyx_hunan.wanmvvm.logic.database.dao

import androidx.room.*
import com.zyx_hunan.wanmvvm.logic.database.entity.HistoryRecord

@Dao
interface HistoryRecordDao {

    @Insert
    fun inserthistory(historyRecord: HistoryRecord):Long

    @Update
    fun updatehistory(historyRecord: HistoryRecord)

    @Delete
    fun deletehistory(historyRecord: HistoryRecord)

    @Query("SELECT * FROM HistoryRecord ORDER BY ID DESC")
    fun finAllhistory():List<HistoryRecord>
}