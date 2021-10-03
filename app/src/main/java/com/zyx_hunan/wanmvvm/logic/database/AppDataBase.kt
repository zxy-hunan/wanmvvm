package com.zyx_hunan.wanmvvm.logic.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zyx_hunan.wanmvvm.logic.database.converter.ChapterTopConverter
import com.zyx_hunan.wanmvvm.logic.database.converter.CollectidConverter
import com.zyx_hunan.wanmvvm.logic.database.dao.HistoryRecordDao
import com.zyx_hunan.wanmvvm.logic.database.dao.UserDao
import com.zyx_hunan.wanmvvm.logic.database.entity.HistoryRecord
import com.zyx_hunan.wanmvvm.logic.model.Regdata

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/21 0021,下午 5:15
 */
@Database(version = 2, entities = [Regdata::class,HistoryRecord::class],exportSchema = false)
@TypeConverters(ChapterTopConverter::class,CollectidConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val historyRecorDao: HistoryRecordDao

    companion object {
        private var instance: AppDataBase? = null

        @Synchronized
        fun getDataBase(context: Context): AppDataBase {
            instance?.let {
                return it
            }

            return Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "app_database"
            ).build().apply { instance = this }
        }

    }

}