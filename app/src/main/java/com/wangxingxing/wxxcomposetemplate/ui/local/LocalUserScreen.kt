package com.wangxingxing.wxxcomposetemplate.ui.local

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wangxingxing.wxxcomposetemplate.data.remote.api.User

@Composable
fun LocalUserScreen(
    viewModel: LocalUserViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val users by viewModel.users.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()
    val selectedUser by viewModel.selectedUser.collectAsState()

    var showCreateDialog by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "本地网络请求示例", style = MaterialTheme.typography.headlineMedium)
            Button(onClick = { viewModel.retry() }, enabled = state !is UiState.Loading) {
                Text("重试")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        when (state) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Error -> {
                val msg = (state as UiState.Error).message
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "错误: $msg", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.retry() }) { Text("重试") }
                }
            }
            else -> {
                if (users.isEmpty()) {
                    Text(text = "暂无用户数据", style = MaterialTheme.typography.bodyMedium)
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
                        items(users) { user ->
                            ElevatedCard(onClick = { viewModel.onUserClick(user) }) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(text = "${user.username}", style = MaterialTheme.typography.titleMedium)
                                    Text(text = user.email, style = MaterialTheme.typography.bodyMedium)
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
