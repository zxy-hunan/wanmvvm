package com.zyx_hunan.baseview

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 *
 *
 * fragment懒加载实现
 *@author Administrator
 *
 *@time 2021,2021/8/7 0007,下午 5:05
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    var TAG="BaseFragment"
    lateinit var binding: VB

    //fragment是否显示
    private var fragmentViewVisibility = false

    //fragment是否被加载
    private var fragmentViewCreated = false

    //是否显示加载数据
    private var fragmentDataLoded = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e(TAG,"onAttach"+this.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG,"onCreate"+this.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        binding = method.invoke(null, layoutInflater, container, false) as VB
        Log.e(TAG,"onCreateView"+this.toString())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e(TAG,"onActivityCreated"+this.toString())
        if (fragmentViewVisibility && !fragmentDataLoded) {
            lazyLoad()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG,"onStart"+this.toString())
    }


    override fun onResume() {
        super.onResume()
        Log.e(TAG,"onResume"+this.toString())
    }


    override fun onPause() {
        super.onPause()
        Log.e(TAG,"onPause"+this.toString())
    }


    override fun onStop() {
        super.onStop()
        Log.e(TAG,"onStop"+this.toString())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(TAG,"onDestroyView"+this.toString())
    }


    override fun onDestroy() {
        super.onDestroy()
        fragmentViewCreated = false
        fragmentDataLoded = false
        Log.e(TAG,"onDestroy"+this.toString())
    }


    override fun onDetach() {
        super.onDetach()
        Log.e(TAG,"onDetach"+this.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentViewCreated = true
        Log.e(TAG,"onViewCreated"+this.toString())
    }

    /**
     * isVisibleToUser=true 正在展示当前fragment
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.e(TAG,"setUserVisibleHint"+this.toString())
        fragmentViewVisibility = isVisibleToUser
        lazyLoad()
    }

    fun lazyLoad() {
        if (fragmentViewVisibility && fragmentViewCreated && !fragmentDataLoded) {
            requestData()
            fragmentDataLoded = true
        }
    }

    abstract fun requestData()

}