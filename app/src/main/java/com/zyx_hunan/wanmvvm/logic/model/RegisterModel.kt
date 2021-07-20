package com.zyx_hunan.wanmvvm.logic.model

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/20 0020,下午 3:54
 */
data class RegisterModel(val data:Regdata,val errorCode:Int,val errorMsg:String)

data class Regdata(val admin:Boolean,val chapterTops:MutableList<String>,val coinCount:Int,
                   val collectIds:MutableList<String>,val icon:String,val id:Int,
                   val nickname:String,
                   val password:String,
                   val publicName:String,
                   val token:String,
                   val type:Int,
                   val username:String)