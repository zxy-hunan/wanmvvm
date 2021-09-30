package com.zyx_hunan.wanmvvm.ui.view.acy

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ZoomButtonsController
import androidx.appcompat.app.AppCompatActivity
import com.qmuiteam.qmui.widget.webview.QMUIWebView
import com.qmuiteam.qmui.widget.webview.QMUIWebViewClient
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.databinding.ActivityArticleitemBinding
import com.zyx_hunan.wanmvvm.ui.view.QDWebView
import java.lang.reflect.Field

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/27 0027,上午 9:37
 */
class ArticleItemAcy : AppCompatActivity() {
    private var mWebView: QDWebView? = null
    private lateinit var binding: ActivityArticleitemBinding
    private lateinit var url: String
    private lateinit var title: String
    private var collect: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleitemBinding.inflate(layoutInflater)
        url = intent.getStringExtra("url").toString()
        title = intent.getStringExtra("title").toString()
        collect = intent.getBooleanExtra("collect", false)
        setContentView(binding.root)
        initTopbar()
        initWebView()
    }

    private fun initTopbar() {
        binding.topbar.setTitle(title).setTextColor(resources.getColor(R.color.white))
        binding.topbar.addLeftBackImageButton()
            .setOnClickListener {
                this.finish()
            }
/*        if (collect) binding.topbar.addRightImageButton(R.mipmap.heartsel, 101) else
            binding.topbar.addRightImageButton(R.mipmap.heartunsle, 101)*/
    }

    fun needDispatchSafeAreaInset(): Boolean {
        return false
    }

    private fun initWebView() {
        mWebView = QDWebView(this)
        val needDispatchSafeAreaInset = needDispatchSafeAreaInset()
        mWebView?.let { binding.webviewcontainer.addWebView(it, needDispatchSafeAreaInset) }

        binding.webviewcontainer.setCustomOnScrollChangeListener(QMUIWebView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            onScrollWebContent(
                scrollX,
                scrollY,
                oldScrollX,
                oldScrollY
            )
        })

        mWebView?.let {
            //QMUIWebViewClient(needDispatchSafeAreaInset, true)
            it.webChromeClient=object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    //显示进度条
                    binding.progressBar.setProgress(newProgress)
                    if (newProgress == 100) {
                        //加载完毕隐藏进度条
                        binding.progressBar.setVisibility(View.GONE)
                    }
                    super.onProgressChanged(view, newProgress)
                }
            }
//            it.webViewClient = getWebViewClient()
            it.requestFocus(View.FOCUS_DOWN)
            setZoomControlGone(it)
            it.loadUrl(url)
        }
    }


    protected fun onScrollWebContent(
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
    }


    override fun onDestroy() {
        super.onDestroy()
        binding.webviewcontainer.destroy()
        mWebView = null
    }

    private fun setZoomControlGone(webView: WebView) {
        webView.settings.displayZoomControls = false
        val classType: Class<*>
        val field: Field
        try {
            classType = WebView::class.java
            field = classType.getDeclaredField("mZoomButtonsController")
            field.isAccessible = true
            val zoomButtonsController = ZoomButtonsController(
                webView
            )
            zoomButtonsController.zoomControls.visibility = View.GONE
            try {
                field[webView] = zoomButtonsController
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
    }

     fun getWebViewClient(): WebViewClient {
        return ExplorerWebViewClient(needDispatchSafeAreaInset())
    }

     class ExplorerWebViewClient(needDispatchSafeAreaInset: Boolean) :
        QMUIWebViewClient(needDispatchSafeAreaInset, true) {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)

        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)

        }
    }

}