package com.zyx_hunan.wanmvvm.logic.model

import java.io.Serializable

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/8/11 0011,下午 2:14
 */
data class QuestionListModel(
    val data: QuestionDataModel,
    val errorCode: Int,
    val errorMsg: String
)

data class QuestionDataModel(
    val curPage: Int,
    val datas: List<QuestionModel>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class QuestionModel(
    val apkLink: String,
    val audit: Int,
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val host: String,
    val id: Long,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Long,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val tags: List<Tag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int,
    val superChapterName: String
):Serializable

data class Tag(val name: String,val url: String)