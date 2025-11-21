package com.wangxingxing.wxxcomposetemplate.ui.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.blankj.utilcode.util.LogUtils
import androidx.compose.ui.res.stringResource

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 示例列表 Compose 页面
 */
@Composable
fun DemoScreen(
    navController: NavController,
    viewModel: DemoViewModel = hiltViewModel()
) {
    val items by viewModel.items.collectAsState()
    val uiState by viewModel.getUiState().collectAsState()
    // 获取字符串资源
    val permissionTitle = stringResource(com.wangxingxing.wxxcomposetemplate.R.string.demo_permission_title)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 标题和刷新按钮
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(com.wangxingxing.wxxcomposetemplate.R.string.demo_list_title),
                style = MaterialTheme.typography.headlineMedium
            )
            Button(onClick = { viewModel.refresh() }) {
                Text(stringResource(com.wangxingxing.wxxcomposetemplate.R.string.demo_refresh))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 加载状态
        when (val state = uiState) {
            is com.wangxingxing.wxxcomposetemplate.base.UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is com.wangxingxing.wxxcomposetemplate.base.UiState.Error -> {
                Text(
                    text = stringResource(com.wangxingxing.wxxcomposetemplate.R.string.demo_error, state.message),
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {
                // 列表内容
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { item ->
                        DemoItemCard(
                            item = item,
                            permissionTitle = permissionTitle,
                            onClick = {
                                // 点击权限请求示例时跳转到权限示例页面
                                if (item.title == permissionTitle) {
                                    navController.navigate("permission")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DemoItemCard(
    item: com.wangxingxing.wxxcomposetemplate.data.remote.api.DemoItem,
    permissionTitle: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (item.title == permissionTitle) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 图片区域
            if (!item.imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = item.title,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    placeholder = androidx.compose.ui.graphics.painter.ColorPainter(
                        MaterialTheme.colorScheme.surfaceVariant
                    ),
                    error = androidx.compose.ui.graphics.painter.ColorPainter(
                        MaterialTheme.colorScheme.errorContainer
                    ),
                    onError = {
                        // 可以在这里添加错误日志
                        LogUtils.e("DemoItemCard: 图片加载失败: ${item.imageUrl}")
                    }
                )
            } else {
                // 占位符
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(com.wangxingxing.wxxcomposetemplate.R.string.demo_no_image),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // 文本内容区域
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}
