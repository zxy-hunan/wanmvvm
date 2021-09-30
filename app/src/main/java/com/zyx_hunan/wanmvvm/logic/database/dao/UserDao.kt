package com.zyx_hunan.wanmvvm.logic.database.dao

import androidx.room.*
import com.zyx_hunan.wanmvvm.logic.model.Regdata

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/21 0021,下午 5:11
 */
@Dao
interface UserDao {

    @Insert
    fun insertUser(user:Regdata):Long

    @Update
    fun updateUser(user: Regdata)

    @Delete
    fun deleteUser(user: Regdata)

    @Query("SELECT * FROM Regdata")
    fun finAllUser():List<Regdata>
}