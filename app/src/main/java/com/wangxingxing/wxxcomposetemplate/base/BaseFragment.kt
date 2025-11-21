package com.wangxingxing.wxxcomposetemplate.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : Fragment 基类，提供通用功能
 */
abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    protected lateinit var binding: VB
        private set

    protected lateinit var viewModel: VM
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = initViewModel()
        initView()
        initData()
        initObserver()
    }

    /**
     * 初始化 ViewBinding
     */
    protected abstract fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VB

    /**
     * 初始化 ViewModel
     */
    protected abstract fun initViewModel(): VM

    /**
     * 初始化视图
     */
    protected open fun initView() {}

    /**
     * 初始化数据
     */
    protected open fun initData() {}

    /**
     * 初始化观察者
     */
    protected open fun initObserver() {}
}
