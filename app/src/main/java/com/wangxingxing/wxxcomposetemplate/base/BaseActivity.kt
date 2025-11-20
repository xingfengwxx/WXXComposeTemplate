package com.wangxingxing.wxxcomposetemplate.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * author : 王星星
 * date : 2024-12-19
 * email : 1099420259@qq.com
 * description : Activity 基类，提供通用功能
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initViewBinding()
        setContentView(binding.root)
        initView()
        initData()
        initObserver()
    }

    /**
     * 初始化 ViewBinding
     */
    protected abstract fun initViewBinding(): VB

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
