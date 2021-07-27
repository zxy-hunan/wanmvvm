package com.zyx_hunan.wanmvvm.ui.view

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ZoomButtonsController
import androidx.appcompat.app.AppCompatActivity
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.widget.webview.QMUIWebView
import com.qmuiteam.qmui.widget.webview.QMUIWebViewClient
import com.zyx_hunan.wanmvvm.R
import com.zyx_hunan.wanmvvm.databinding.ActivityArticleitemBinding
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleitemBinding.inflate(layoutInflater)
        url = intent.getStringExtra("url").toString()
        setContentView(binding.root)
        initTopbar()
        initWebView()
    }

    fun needDispatchSafeAreaInset(): Boolean {
        return false
    }

    private fun initTopbar() {
        binding.topbar.addLeftBackImageButton()
            .setOnClickListener {
                this.finish()
            }
    }

    private fun initWebView() {
        mWebView = QDWebView(this)
        val needDispatchSafeAreaInset = needDispatchSafeAreaInset()
        mWebView?.let {binding.webviewcontainer.addWebView(it, needDispatchSafeAreaInset)}

        binding.webviewcontainer.setCustomOnScrollChangeListener(QMUIWebView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            onScrollWebContent(
                scrollX,
                scrollY,
                oldScrollX,
                oldScrollY
            )
        })
        val containerLp = binding.webviewcontainer.layoutParams as FrameLayout.LayoutParams
        binding.webviewcontainer.fitsSystemWindows = !needDispatchSafeAreaInset
        containerLp.topMargin = if (needDispatchSafeAreaInset) 0 else QMUIResHelper.getAttrDimen(
            this,
            R.attr.qmui_topbar_height
        )
        binding.webviewcontainer.layoutParams = containerLp
        mWebView?.let {
            it.webChromeClient = WebChromeClient()
            it.webViewClient = QMUIWebViewClient(needDispatchSafeAreaInset, true)
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

}