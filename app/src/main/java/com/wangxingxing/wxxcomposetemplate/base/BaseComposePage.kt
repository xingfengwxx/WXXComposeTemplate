package com.wangxingxing.wxxcomposetemplate.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : Compose 页面基类，提供通用功能
 * 
 * 注意：由于 @Composable 函数不能使用 reified 类型参数，因此需要调用者显式传入 ViewModel
 * 使用示例：
 * ```
 * @Composable
 * fun MyScreen(viewModel: MyViewModel = hiltViewModel()) {
 *     BaseComposePage(viewModel = viewModel) { vm ->
 *         // 使用 vm 构建 UI
 *     }
 * }
 * ```
 */
@Composable
fun <VM : ViewModel> BaseComposePage(
    viewModel: VM,
    modifier: Modifier = Modifier,
    content: @Composable (VM) -> Unit
) {
    content(viewModel)
}
