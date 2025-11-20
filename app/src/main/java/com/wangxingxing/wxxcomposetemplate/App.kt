package com.wangxingxing.wxxcomposetemplate

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.wangxingxing.wxxcomposetemplate.utils.ScreenAdapter

/**
 * author : 王星星
 * date : 2024-12-19
 * email : 1099420259@qq.com
 * description : 应用入口类
 */
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // 初始化屏幕适配
        ScreenAdapter.init(this)
    }
}
