package com.wangxingxing.wxxcomposetemplate

import android.app.Application
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import dagger.hilt.android.HiltAndroidApp
import com.wangxingxing.wxxcomposetemplate.BuildConfig
import com.wangxingxing.wxxcomposetemplate.utils.ScreenAdapter

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description :
 */
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        Utils.init(instance)
        initLog()
        // 初始化屏幕适配
        ScreenAdapter.init(instance)
    }

    private fun initLog() {
        Utils.init(instance)
        LogUtils.getConfig()
            .setLogSwitch(isDebug())
            .setGlobalTag(TAG)
            .setBorderSwitch(true)
    }

    companion object {
        const val TAG = "wxx"

        lateinit var instance: App
            private set

        fun isDebug() = BuildConfig.DEBUG
    }
}
