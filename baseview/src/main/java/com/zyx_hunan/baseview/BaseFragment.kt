package com.zyx_hunan.baseview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 *
 *
 * fragment懒加载实现
 *@author Administrator
 *
 *@time 2021,2021/8/7 0007,下午 5:05
 */
abstract class BaseFragment<T:ViewBinding> : Fragment() {

    //fragment是否显示
    private var fragmentViewVisibility = false

    //fragment是否被加载
    private var fragmentViewCreated = false

    //是否显示加载数据
    private var fragmentDataLoded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentViewCreated = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=getViewBinding()
        FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * isVisibleToUser=true 正在展示当前fragment
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        fragmentViewVisibility = isVisibleToUser
        lazyLoad()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (fragmentViewVisibility && !fragmentDataLoded) {
            lazyLoad()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentViewCreated = false
        fragmentDataLoded = false
    }


    fun lazyLoad() {
        if (fragmentViewVisibility && fragmentViewCreated && !fragmentDataLoded) {
            requestData()
            fragmentDataLoded=true
        }
    }


    abstract fun requestData()

    abstract fun getViewBinding():ViewBinding

}