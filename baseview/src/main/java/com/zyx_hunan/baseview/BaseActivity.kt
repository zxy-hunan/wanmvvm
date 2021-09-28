package com.zyx_hunan.baseview

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType


/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/21 0021,下午 2:50
 */

open class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    private val UPDATEBROADCAST="com.zxy_hunan.wan"
    open var mHandler: Handler? = null
    lateinit var binding: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val clazz = type.actualTypeArguments[0] as Class<VB>
            val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            binding = method.invoke(null, layoutInflater) as VB
            setContentView(binding.root)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler?.let { it.removeCallbacks(task) }
    }

    var ouTime = 3
    val task: Runnable = object : Runnable {
        override fun run() {
            ouTime--

            if (ouTime == 0) {
                mHandler?.sendEmptyMessage(-1)
                mHandler?.removeCallbacks(this)
            } else {
                mHandler?.postDelayed(this, 1000)
            }

        }
    }


     fun registerBc(bcReeiver:BroadcastReceiver){
        val filter = IntentFilter(UPDATEBROADCAST)
        LocalBroadcastManager.getInstance(this).registerReceiver(bcReeiver,filter)
    }


     fun unRegisterBc(bcReeiver:BroadcastReceiver){
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bcReeiver)
    }


    fun sendBdMessage(){
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(UPDATEBROADCAST))
    }


}


