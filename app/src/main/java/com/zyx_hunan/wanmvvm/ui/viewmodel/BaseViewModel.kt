package com.zyx_hunan.wanmvvm.ui.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyx_hunan.baseutil.expand.SingleLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

open class BaseViewModel : ViewModel(), LifecycleObserver {

    private val error by lazy { SingleLiveData<Exception>() }

    private val finally by lazy { SingleLiveData<Int>() }

    //运行在UI线程的协程
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        try {
            withTimeout(20 * 1000) {
                block()
            }
        } catch (e: Exception) {
            //此处接收到BaseRepository里的request抛出的异常，直接赋值给error
            error.value = e
        } finally {
            finally.value = 200
        }
    }

    /**
     * 请求失败，出现异常
     */
    fun getError(): LiveData<Exception> = error

    /**
     * 请求完成，在此处做一些关闭操作
     */
    fun getFinally(): LiveData<Int> = finally
}