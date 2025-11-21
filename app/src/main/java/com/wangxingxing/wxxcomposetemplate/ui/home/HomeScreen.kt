package com.wangxingxing.wxxcomposetemplate.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 首页 Compose 页面
 */
@Composable
fun HomeScreen() {
    var clickCount by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "欢迎使用 Android 架构模板",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Button(
            onClick = { clickCount++ },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("点击次数: $clickCount")
        }
        
        Text(
            text = "这是 Compose 示例页面",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
