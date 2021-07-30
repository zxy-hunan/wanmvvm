package com.zyx_hunan.wanmvvm.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zyx_hunan.wanmvvm.databinding.FragmentMineBinding
import com.zyx_hunan.wanmvvm.ui.view.LoginAcy
import com.zyx_hunan.wanmvvm.ui.viewmodel.KnowledgeViewModel

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/30 0030,上午 11:05
 */
class MineFragment : Fragment() {
    private lateinit var binding: FragmentMineBinding
    private val viewModel by lazy { ViewModelProvider(this).get(KnowledgeViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMineBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        binding.textlogin.setOnClickListener {
            startActivity(Intent(activity,LoginAcy::class.java))
        }
    }


}