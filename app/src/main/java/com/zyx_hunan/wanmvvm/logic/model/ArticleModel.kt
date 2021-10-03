package com.zyx_hunan.wanmvvm.logic.model

import com.google.gson.annotations.SerializedName
import com.zyx_hunan.baseutil.expand.isNull
import com.zyx_hunan.wanmvvm.compose.video.VideoModel
import com.zyx_hunan.wanmvvm.logic.net.DataType

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/24 0024,下午 1:43
 */

data class AllData(
    val type: DataType?,
    val author: String?,
    val chapterName: String?,
    var collect: Boolean?,
    val fresh: Boolean,
    val id: Long?,
    var link: String?,
    val publishTime: Long?,
    val shareUser: String?,
    val superChapterName: String?,
    val title: String?,
    val description: String?, val playUrl: String?,
    val urls: List<String>?,
    val homepage: String?,
    val videos:List<VideoModel>?
) {
    constructor(
        type: DataType?,
        id: Long?,
        title: String?,
        description: String?, playUrl: String?,
        url: String?,
        urls: List<String>?,homepage: String?
    ) : this(
        type, "", "", false, false, id,
        url, 0, "", "", title, description, playUrl, urls,homepage,null
    ){
        if (url.isNullOrEmpty()){
            link=homepage
        }
    }

    constructor(
        type: DataType?,
        urls: List<String>?,
        videos:List<VideoModel>?
    ) : this(
        type, "", "", false, false, 0L,
        "", 0, "", "", "", "", "", urls,"",videos
    )
}


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
    val id: Long,
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
) {
    constructor(
        desc: String,
        id: Long,
        imagePath: String,
        isVisible: Int,
        order: Int,
        title: String,
        type: Int,
        url: String
    ) : this(
        imagePath, 0, "", false, 0, "", false, 0, desc, "", "", false, "", id, url, "",
        "", "", "", "", 0, 0, 0, 0, "",
        0, "", title, type, 0, isVisible, 0
    )
}


data class OpenFeedTab(
    val itemList: List<OpenFeedTabItem>,
    val count: String,
    val total: String,
    val nextPageUrl: String,
    val adExist: String
)


data class OpenFeedTabItem(
    val type: String,
    val data: OpenFeedTabItemData,
    val trackingData: String,
//    val tag: String,
    val id: Long,
    val adIndex: Int
)

data class OpenFeedTabItemData(
    val dataType: String?,
//    val header: OpenFeedTabItemDataHeader?,
    val content: OpenFeedTabItemDataContent?,
//    val adTrack: String?
    val id: Long,
    val title: String?,
    val description: String?,
    val cover: OpenFeedTabItemDataContentDataCover?,
    val playUrl: String,
    val url: String?,
    val urls: List<String>?
)

data class OpenFeedTabItemDataHeader(
    val id: Long,
    val actionUrl: String?,
//    val labelList: String?,
    val icon: String?,
    val iconType: String?,
    val time: Long,
    val showHateVideo: Boolean,
    val followType: String?,
    val tagId: Int,
    val tagName: String?,
    val issuerName: String?,
    val topShow: Boolean
)

data class OpenFeedTabItemDataContent(
    val type: String?,
    val data: OpenFeedTabItemDataContentData?,
    val tag: String?,
    val id: Int,
    val adIndex: Int
)


data class OpenFeedTabItemDataContentData(
    val dataType: String?,
    val id: Long,
    val title: String?,
    val description: String?,
//    val library: String?,
//    val tags: List<OpenFeedTabItemDataContentDataTags>?,
//    val consumption: OpenFeedTabItemDataContentDataConsumption?,
//    val resourceType: String?,
//    val uid: Long,
//    val createTime: Long,
//    val updateTime: Long,
//    val collected: Boolean,
//    val reallyCollected: Boolean,
//    val owner: OpenFeedTabItemDataContentDataOwner?,
    val cover: OpenFeedTabItemDataContentDataCover?,
//    val selectedTime: Long,
//    val checkStatus: String?,
//    val area: String?,
//    val city: String?,
///*    val longitude: Long,
//    val latitude: Long,*/
//    val ifMock: Boolean,
//    val validateStatus: String?,
//    val validateResult: String?,
//    val width: Long,
//    val height: Long,
//    val addWatermark: Boolean,
//    val recentOnceReply: OpenFeedTabItemDataContentDataRecentOnceReply,
//    val MessageActionUrl: String?,
    val playUrl: String,
    val url: String?,
    val urls: List<String>?,
//    val status: Long,
//    val releaseTime: Long,
//    val urlsWithWatermark: List<String>?
)

data class OpenFeedTabItemDataContentDataTags(
    val id: Long,
    val name: String?,
    val actionUrl: String?,
//    val adTrack: String?,
    val desc: String?,
    val bgPicture: String?,
    val headerImage: String?,
    val tagRecType: String?,
//    val childTagList: String?,
//    val childTagIdList: String?,
    val haveReward: Boolean,
    val ifNewest: Boolean,
//    val newestEndTime: String?,
    val communityIndex: Long
)

data class OpenFeedTabItemDataContentDataConsumption(
    val collectionCount: Long,
    val shareCount: Long,
    val replyCount: Long,
    val realCollectionCount: Long
)


data class OpenFeedTabItemDataContentDataOwner(
    val uid: Long,
    val nickname: String?,
    val avatar: String?,
    val userType: String?,
    val ifPgc: Boolean,
    val description: String?,
//    val area: String?,
    val gender: String?,
    val registDate: Long,
    val releaseDate: Long,
    val cover: String?,
    val actionUrl: String?,
    val followed: Boolean,
    val limitVideoOpen: Boolean,
    val library: String?,
    val birthday: Long,
    val country: String?,
    val city: String?,
    val university: String?,
    val job: String?,
    val expert: Boolean
)

data class OpenFeedTabItemDataContentDataCover(
    val feed: String?,
    /* val detail: String?,
     val blurred: String?,
     val sharing: String?,
     val homepage: String?*/
)

data class OpenFeedTabItemDataContentDataRecentOnceReply(
    val dataType: String?,
    val message: String?,
    val nickname: String?,
    val actionUrl: String?,
    val contentType: String?
)