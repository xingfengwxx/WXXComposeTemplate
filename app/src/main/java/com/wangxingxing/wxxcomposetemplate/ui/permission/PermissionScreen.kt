package com.wangxingxing.wxxcomposetemplate.ui.permission

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wangxingxing.wxxcomposetemplate.utils.PermissionHelper
import kotlinx.coroutines.launch

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 权限请求示例页面
 */
@Composable
fun PermissionScreen() {
    val activity = LocalActivity.current
    val scope = rememberCoroutineScope()
    var permissionStatus by remember { mutableStateOf("未请求") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "权限请求示例",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "状态: $permissionStatus",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                scope.launch {
                    if (activity != null) {
                        val granted = PermissionHelper.requestStoragePermission(activity)
                        permissionStatus = if (granted) "已授予" else "已拒绝"
                    } else {
                        permissionStatus = "需要 Activity 上下文"
                    }
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("请求存储权限")
        }

        Button(
            onClick = {
                scope.launch {
                    if (activity != null) {
                        val granted = PermissionHelper.requestCameraPermission(activity)
                        permissionStatus = if (granted) "已授予" else "已拒绝"
                    } else {
                        permissionStatus = "需要 Activity 上下文"
                    }
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("请求相机权限")
        }
    }
}
