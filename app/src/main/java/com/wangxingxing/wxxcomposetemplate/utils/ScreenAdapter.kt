package com.wangxingxing.wxxcomposetemplate.utils

import android.app.Application
import android.util.Log
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.onAdaptListener

/**
 * author : 王星星
 * date : 2024-12-19
 * email : 1099420259@qq.com
 * description : 屏幕适配工具类，封装 AutoSize
 */
object ScreenAdapter {

    private const val TAG = "ScreenAdapter"

    /**
     * 初始化屏幕适配
     */
    fun init(application: Application) {
        AutoSizeConfig.getInstance()
            .setCustomFragment(true)
            .setOnAdaptListener(object : onAdaptListener {
                override fun onAdaptBefore(target: Any?, activity: android.app.Activity?) {
                    Log.d(TAG, "${target?.javaClass?.simpleName} onAdaptBefore!")
                }

                override fun onAdaptAfter(target: Any?, activity: android.app.Activity?) {
                    Log.d(TAG, "${target?.javaClass?.simpleName} onAdaptAfter!")
                }
            })
            .setLog(true)
            .setUseDeviceSize(false)
    }
}
