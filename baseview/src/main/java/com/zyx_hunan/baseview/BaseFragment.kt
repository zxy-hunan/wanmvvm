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
open class BaseFragment<VB : ViewBinding> : Fragment() {
    private val TAG="BaseFragment"
    lateinit var binding: VB

    //fragment是否显示
    private var mIsVisibleToUser = false

    //fragment是否被加载
    private var mIsViewCreatetd = false

    //是否显示加载数据
    private var mIsDataLoaded = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e(TAG,"onAttach:"+this.targetFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG,"onCreate:"+this.targetFragment)
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
        Log.e(TAG,"onCreateView:"+this.targetFragment)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e(TAG,"onActivityCreated:"+this.targetFragment)
        if (mIsVisibleToUser && !mIsDataLoaded) {
            lazyLoad()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG,"onStart:"+this.targetFragment)
    }


    override fun onResume() {
        super.onResume()
        Log.e(TAG,"onResume:"+this.targetFragment)
    }


    override fun onPause() {
        super.onPause()
        Log.e(TAG,"onPause:"+this.targetFragment)
    }


    override fun onStop() {
        super.onStop()
        Log.e(TAG,"onStop:"+this.targetFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mIsViewCreatetd = false
        mIsVisibleToUser = false
        Log.e(TAG,"onDestroyView:"+this.targetFragment)
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG,"onDestroy:"+this.targetFragment)
    }


    override fun onDetach() {
        super.onDetach()
        Log.e(TAG,"onDetach:"+this.targetFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsViewCreatetd = true
        Log.e(TAG,"onViewCreated:"+this.targetFragment)
    }

    /**
     * isVisibleToUser=true 正在展示当前fragment
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.e(TAG,"setUserVisibleHint:"+isVisibleToUser)
        mIsVisibleToUser = isVisibleToUser
        lazyLoad()
    }

    fun lazyLoad() {
        Log.e(TAG,"startmIsVisibleToUser:"+mIsVisibleToUser+";  mIsViewCreatetd:"+mIsViewCreatetd+";  mIsDataLoaded:"+mIsDataLoaded)
        if (mIsVisibleToUser && mIsViewCreatetd && !mIsDataLoaded) {
            Log.e(TAG,"lazyLoad()")
            requestData()
            mIsDataLoaded = true
        }
        Log.e(TAG,"endmIsVisibleToUser:"+mIsVisibleToUser+";  mIsViewCreatetd:"+mIsViewCreatetd+";  mIsDataLoaded:"+mIsDataLoaded)
    }

    open fun requestData(){}

}