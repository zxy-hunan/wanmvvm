package com.zyx_hunan.wanmvvm.logic.net

import android.webkit.CookieManager
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * @author: ZXY
 * @date: 2021/10/3
 */
class CookieJar : CookieJar {
    //请求结束
    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        for (cookie in cookies) {
            cookieManager.setCookie(url.toString(), cookie.toString())
        }
        cookieManager.flush()
    }

    //请求前
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookieList: MutableList<Cookie> = ArrayList()
        CookieManager.getInstance().getCookie(url.toString())?.let {
            if (it.isNotEmpty()) {
                val cookies = it.split(";".toRegex())
                for (cookie in cookies) {
                    Cookie.parse(url, cookie)?.apply {
                        cookieList.add(this)
                    }
                }
            }
        }
        return cookieList
    }
}