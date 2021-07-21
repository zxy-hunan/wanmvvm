package com.zyx_hunan.wanmvvm.logic

import androidx.lifecycle.liveData
import com.zyx_hunan.wanmvvm.WanApplication
import com.zyx_hunan.wanmvvm.logic.database.AppDataBase
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
    private val userDao=AppDataBase.getDataBase(WanApplication.mContext).userDao

    fun register(map: Map<String,String>) = liveData(Dispatchers.IO) {
        val result = try {
            val registerRep: RegisterModel = WanNet.register(map)
            if (registerRep.errorCode == 0) {
                val data = registerRep.data
                userDao.insertUser(data)
                Result.success(data)
            } else {
                Result.failure(RuntimeException("response errorCode is${registerRep.errorCode}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Regdata>>(e)
        }
        emit(result)
    }

    fun login(map: Map<String,String>) = liveData(Dispatchers.IO) {
        val result = try {
            val registerRep: RegisterModel = WanNet.login(map)
            if (registerRep.errorCode == 0) {
                val data = registerRep.data
                Result.success(data)
            } else {
                Result.failure(RuntimeException("response errorCode is${registerRep.errorCode}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Regdata>>(e)
        }
        emit(result)
    }


    fun findLocal() = liveData(Dispatchers.IO) {
        val result = try {
            var user:Regdata?=null
            val userList=userDao.finAllUser()
            for (reg in userList){
                user=reg
                break
            }
            if (user!=null) {
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