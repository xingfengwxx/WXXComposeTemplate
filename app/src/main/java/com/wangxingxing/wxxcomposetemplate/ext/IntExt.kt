package com.wangxingxing.wxxcomposetemplate.ext

import com.wangxingxing.wxxcomposetemplate.App

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 字符串资源扩展方法，支持 R.string.xxx.getStringRes() 调用方式
 */


/**
 * Int 扩展方法：通过字符串资源 ID 获取字符串（使用全局 Application Context）
 * 适用于非 Composable 环境，使用 App.instance 获取字符串资源
 * 
 * 注意：此方法使用 App.instance，确保 App 已初始化
 * 在非 Composable 环境中，可以直接调用此方法，无需传入 Context
 *
 * 使用示例：
 * ```
 * val text = R.string.app_name.getString()
 * ```
 *
 * @return 字符串资源对应的文本
 */
fun Int.getStringRes(): String {
    return App.instance.getString(this)
}

/**
 * Int 扩展方法：通过字符串资源 ID 获取字符串（带格式化参数，使用全局 Application Context）
 * 适用于非 Composable 环境，使用 App.instance 获取字符串资源
 * 
 * 注意：此方法使用 App.instance，确保 App 已初始化
 *
 * 使用示例：
 * ```
 * val text = R.string.demo_error.getString("网络错误")
 * ```
 *
 * @param formatArgs 格式化参数
 * @return 格式化后的字符串
 */
fun Int.getStringRes(vararg formatArgs: Any): String {
    return App.instance.getString(this, *formatArgs)
}


