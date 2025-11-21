package com.wangxingxing.wxxcomposetemplate.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.blankj.utilcode.util.NetworkUtils

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 网络工具类，封装 AndroidUtilCode
 */
object NetworkHelper {

    /**
     * 检查网络是否可用
     */
    fun isNetworkAvailable(context: Context): Boolean {
        return NetworkUtils.isAvailable()
    }

    /**
     * 检查是否是 WiFi 连接
     */
    fun isWifiConnected(context: Context): Boolean {
        return NetworkUtils.isWifiConnected()
    }

    /**
     * 检查是否是移动数据连接
     */
    fun isMobileDataConnected(context: Context): Boolean {
        return NetworkUtils.isMobileData()
    }

    /**
     * 获取网络类型
     */
    fun getNetworkType(context: Context): String {
        return when {
            isWifiConnected(context) -> "WiFi"
            isMobileDataConnected(context) -> "Mobile"
            else -> "Unknown"
        }
    }
}
