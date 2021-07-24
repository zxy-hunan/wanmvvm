package com.zyx_hunan.wanmvvm.logic.model

import com.google.gson.annotations.SerializedName

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/24 0024,下午 1:43
 */
data class ArticleModel(val data: Data, val errorCode: Int, val errorMsg: String)


data class Data(
    val curPage: Int,
    @SerializedName("datas") val articleList: List<Articledata>,
    val offset: Int,
    val over: Boolean,
    val valpageCount: Int,
    val size: Int,
    val total: Int
)

data class Articledata(
    val apkLink: String,
    val audit: Int,
    val author: String,
    val canEditv: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val host: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
//    val tags: String,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)