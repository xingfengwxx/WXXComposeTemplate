package com.wangxingxing.wxxcomposetemplate.ext

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.wangxingxing.wxxcomposetemplate.App

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 字符串资源扩展方法，支持 R.string.xxx.getString() 调用方式
 */


/**
 * Int 扩展方法：通过字符串资源 ID 获取字符串（使用指定的 Context）
 * 适用于需要指定 Context 的场景
 *
 * 使用示例：
 * ```
 * val text = R.string.app_name.getString(context)
 * ```
 *
 * @param context Context 对象
 * @return 字符串资源对应的文本
 */
fun Int.getString(context: Context): String {
    return context.getString(this)
}

/**
 * Int 扩展方法：通过字符串资源 ID 获取字符串（使用指定的 Context，带格式化参数）
 * 适用于需要指定 Context 的场景
 *
 * 使用示例：
 * ```
 * val text = R.string.demo_error.getString(context, "网络错误")
 * ```
 *
 * @param context Context 对象
 * @param formatArgs 格式化参数
 * @return 格式化后的字符串
 */
fun Int.getString(context: Context, vararg formatArgs: Any): String {
    return context.getString(this, *formatArgs)
}

/**
 * Int 扩展方法：通过字符串资源 ID 获取字符串（Composable 环境）
 * 适用于 Compose 环境，使用 stringResource 获取字符串资源
 * 
 * 注意：此方法只能在 @Composable 函数中调用
 *
 * 使用示例：
 * ```
 * @Composable
 * fun MyScreen() {
 *     val text = R.string.app_name.getString()
 *     Text(text = text)
 * }
 * ```
 *
 * @return 字符串资源对应的文本
 */
@Composable
fun Int.getString(): String {
    return stringResource(this)
}

/**
 * Int 扩展方法：通过字符串资源 ID 获取字符串（Composable 环境，带格式化参数）
 * 适用于 Compose 环境，使用 stringResource 获取字符串资源
 *
 * 使用示例：
 * ```
 * @Composable
 * fun MyScreen() {
 *     val text = R.string.demo_error.getString("网络错误")
 *     Text(text = text)
 * }
 * ```
 *
 * @param formatArgs 格式化参数
 * @return 格式化后的字符串
 */
@Composable
fun Int.getString(vararg formatArgs: Any): String {
    return stringResource(this, *formatArgs)
}

