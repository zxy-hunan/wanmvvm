package com.zyx_hunan.wanmvvm.logic.model

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/24 0024,下午 1:43
 */
data class KnowledgeModel(val data: List<Children>, val errorCode: Int, val errorMsg: String)


data class Children(
    val children:List<Children>,
    val courseId:Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)
