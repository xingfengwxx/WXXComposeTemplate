package com.wangxingxing.wxxcomposetemplate.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
            text = stringResource(com.wangxingxing.wxxcomposetemplate.R.string.home_welcome),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Button(
            onClick = { clickCount++ },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(stringResource(com.wangxingxing.wxxcomposetemplate.R.string.home_click_count, clickCount))
        }
        
        Text(
            text = stringResource(com.wangxingxing.wxxcomposetemplate.R.string.home_compose_example),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
