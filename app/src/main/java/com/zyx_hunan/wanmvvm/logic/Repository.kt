package com.zyx_hunan.wanmvvm.logic

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.liveData
import com.zyx_hunan.baseutil.expand.showToast
import com.zyx_hunan.wanmvvm.WanApplication
import com.zyx_hunan.wanmvvm.logic.database.AppDataBase
import com.zyx_hunan.wanmvvm.logic.database.dao.UserDao
import com.zyx_hunan.wanmvvm.logic.model.Regdata
import com.zyx_hunan.wanmvvm.logic.model.RegisterModel
import com.zyx_hunan.wanmvvm.logic.net.WanNet
import com.zyx_hunan.wanmvvm.logic.net.req.RegisterReq
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/20 0020,下午 4:19
 */
object Repository {
    private var userDao: UserDao = AppDataBase.getDataBase(WanApplication.mContext).userDao

    fun register(map: Map<String, String>) = liveData(Dispatchers.IO) {

        val result = try {
            val registerRep: RegisterModel = WanNet.register(map)
            if (registerRep.errorCode == 0) {
                val data = registerRep.data
                Log.i("test", data.toString())
                val rId = userDao?.insertUser(data)
                Log.i("test", "rId:$rId")
                Result.success(data)
            } else {
                Result.failure(RuntimeException("response errorCode is${registerRep.errorCode}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Regdata>>(e)
        }
        emit(result)
    }

    fun login(map: Map<String, String>) = liveData(Dispatchers.IO) {
        val result = try {
            val registerRep: RegisterModel = WanNet.login(map)
            if (registerRep.errorCode == 0) {
                val data = registerRep.data
                userDao.updateUser(data)
                Result.success(data)
            } else {
                Result.failure(RuntimeException("response errorCode is${registerRep.errorCode}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Regdata>>(e)
        }
        emit(result)
    }


    fun findLocal(type: Int = 0) = liveData(Dispatchers.IO) {
        val result = try {
            var user: Any? = null
            val userList = userDao.finAllUser()
            if (type != 1) {
                for (reg in userList) {
                    user = reg
                    break
                }
            } else {
                user = userList
            }
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(RuntimeException("user is null"))
            }
        } catch (e: Exception) {
            Result.failure<List<Regdata>>(e)
        }
        emit(result)
    }

}