package com.zyx_hunan.wanmvvm.compose.video

import java.io.Serializable

/**
 * @author: ZXY
 * @date: 2021/9/23
 */
data class VideoModel(
    val id: Long?,
    val title: String?,
    val description: String?, val feed: String?, val playUrl: String?
) : Serializable {
    constructor() : this(
        0, "",
        "", "", ""
    )
}