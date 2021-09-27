package com.zyx_hunan.baseview

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    open  var mHandler: Handler?=null
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


}


