package com.wangxingxing.wxxcomposetemplate.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * author : 王星星
 * date : 2024-12-19
 * email : 1099420259@qq.com
 * description : Compose 页面基类，提供通用功能
 */
@Composable
fun <VM : ViewModel> BaseComposePage(
    viewModel: VM = viewModel(),
    modifier: Modifier = Modifier,
    content: @Composable (VM) -> Unit
) {
    content(viewModel)
}
