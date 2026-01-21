package com.wangxingxing.wxxcomposetemplate.ui.local

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import androidx.compose.material3.pulltorefresh.PullToRefreshBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalUserScreen(
    viewModel: LocalUserViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()
    val selectedUser by viewModel.selectedUser.collectAsState()
    val totalCount by viewModel.totalCount.collectAsState()

    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val isRefreshing by remember {
        derivedStateOf { pagingItems.loadState.refresh is LoadState.Loading }
    }

    var showCreateDialog by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { pagingItems.refresh() }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "本地网络请求示例（共 $totalCount 条）", style = MaterialTheme.typography.headlineMedium)
                Button(onClick = { pagingItems.refresh() }, enabled = !isRefreshing) {
                    Text("刷新")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            when (val refreshState = pagingItems.loadState.refresh) {
                is LoadState.Loading -> {
                    // 下拉刷新或首次加载时，展示占位
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is LoadState.Error -> {
                    val msg = refreshState.error.message ?: "加载失败"
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "错误: $msg", color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { pagingItems.retry() }) { Text("重试") }
                    }
                }
                is LoadState.NotLoading -> {
                    if (pagingItems.itemCount == 0) {
                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "暂无用户数据", style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { pagingItems.refresh() }) { Text("刷新") }
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f).fillMaxWidth()
                        ) {
                            items(
                                count = pagingItems.itemCount,
                                key = pagingItems.itemKey { it.id }
                            ) { index ->
                                val user = pagingItems[index]
                                if (user != null) {
                                    ElevatedCard(
                                        onClick = { viewModel.onUserClick(user) },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(16.dp)) {
                                            Text(text = user.username, style = MaterialTheme.typography.titleMedium)
                                            Text(text = user.email, style = MaterialTheme.typography.bodyMedium)
                                        }
                                    }
                                } else {
                                    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                                        Box(Modifier.fillMaxWidth().height(64.dp)) {}
                                    }
                                }
                            }
                            // 底部加载更多 / 错误 / 结束提示
                            item {
                                Spacer(modifier = Modifier.height(8.dp))
                                when (val appendState = pagingItems.loadState.append) {
                                    is LoadState.Loading -> {
                                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                    is LoadState.Error -> {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text("加载更多失败")
                                            Spacer(Modifier.height(4.dp))
                                            Button(onClick = { pagingItems.retry() }) { Text("重试") }
                                        }
                                    }
                                    is LoadState.NotLoading -> {
                                        if (pagingItems.itemCount > 0 && appendState.endOfPaginationReached) {
                                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                                Text("没有更多了")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { showCreateDialog = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = state !is UiState.Loading
            ) { Text("创建新用户") }
        }
    }

    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("创建用户") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("用户名") })
                    OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("邮箱") })
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.createNewUser(username.trim(), email.trim())
                    showCreateDialog = false
                }, enabled = username.isNotBlank() && email.isNotBlank()) { Text("确定") }
            },
            dismissButton = { TextButton(onClick = { showCreateDialog = false }) { Text("取消") } }
        )
    }

    if (dialogState && selectedUser != null) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDialog() },
            title = { Text("用户详情") },
            text = {
                val u = selectedUser!!
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("ID: ${u.id}")
                    Text("用户名: ${u.username}")
                    Text("邮箱: ${u.email}")
                    Text("创建时间: ${u.createdAt ?: "未知"}")
                }
            },
            confirmButton = { TextButton(onClick = { viewModel.dismissDialog() }) { Text("关闭") } },
            dismissButton = {}
        )
    }
}
