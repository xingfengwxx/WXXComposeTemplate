package com.wangxingxing.wxxcomposetemplate.ui.demo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * author : 王星星
 * date : 2024-12-19
 * email : 1099420259@qq.com
 * description : 示例列表 Compose 页面
 */
@Composable
fun DemoScreen(
    viewModel: DemoViewModel = hiltViewModel()
) {
    val items by viewModel.items.collectAsState()
    val uiState by viewModel.getUiState().collectAsState()

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
                text = "示例列表",
                style = MaterialTheme.typography.headlineMedium
            )
            Button(onClick = { viewModel.refresh() }) {
                Text("刷新")
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
                    text = "错误: ${state.message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {
                // 列表内容
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { item ->
                        DemoItemCard(item = item)
                    }
                }
            }
        }
    }
}

@Composable
fun DemoItemCard(item: com.wangxingxing.wxxcomposetemplate.data.remote.api.DemoItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.content,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
