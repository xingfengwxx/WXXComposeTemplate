package com.wangxingxing.wxxcomposetemplate.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wangxingxing.wxxcomposetemplate.ui.theme.WXXComposeTemplateTheme
import androidx.hilt.navigation.compose.hiltViewModel
import com.wangxingxing.wxxcomposetemplate.R
import com.wangxingxing.wxxcomposetemplate.base.UiState
import com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult
import com.wangxingxing.wxxcomposetemplate.data.remote.api.UserInfo

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 登录页面
 */
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit = {}
) {
    val uiState by viewModel.getUiState().collectAsState()
    val loginResult by viewModel.loginResult.collectAsState()
    val localUserInfo by viewModel.localUserInfo.collectAsState()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 预先获取字符串资源
    val loginTitle = stringResource(R.string.login_title)
    val usernameHint = stringResource(R.string.login_username_hint)
    val passwordHint = stringResource(R.string.login_password_hint)
    val loginButton = stringResource(R.string.login_button)
    val loggingIn = stringResource(R.string.login_logging_in)
    val logoutButton = stringResource(R.string.login_logout)

    // 获取用户信息（优先使用本地，其次使用登录结果）
    val userInfo = localUserInfo ?: (when (val result = loginResult) {
        is ApiResult.Success -> result.data
        else -> null
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (userInfo != null) Arrangement.Top else Arrangement.Center
    ) {
        // 如果已登录成功，显示用户信息卡片和退出按钮
        if (userInfo != null) {
            UserInfoCard(
                userInfo = userInfo,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            // 退出按钮
            Button(
                onClick = {
                    viewModel.logout()
                    // 清空输入框
                    username = ""
                    password = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(logoutButton)
            }
        } else {
            // 未登录时显示登录表单
            // 标题
            Text(
                text = loginTitle,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // 用户名输入框
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(usernameHint) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true,
                enabled = uiState !is UiState.Loading
            )

            // 密码输入框
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(passwordHint) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                enabled = uiState !is UiState.Loading
            )

            // 登录按钮
            Button(
                onClick = {
                    if (username.isNotBlank() && password.isNotBlank()) {
                        viewModel.login(username, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = username.isNotBlank() && password.isNotBlank() && uiState !is UiState.Loading
            ) {
                if (uiState is UiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(loggingIn)
                } else {
                    Text(loginButton)
                }
            }

            // 错误提示
            if (uiState is UiState.Error) {
                Text(
                    text = (uiState as UiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

/**
 * 用户信息卡片
 */
@Composable
fun UserInfoCard(
    userInfo: UserInfo,
    modifier: Modifier = Modifier
) {
    val userInfoTitle = stringResource(R.string.login_user_info_title)
    val userIdLabel = stringResource(R.string.login_user_id)
    val usernameLabel = stringResource(R.string.login_username)
    val emailLabel = stringResource(R.string.login_email)
    val nicknameLabel = stringResource(R.string.login_nickname)
    val publicNameLabel = stringResource(R.string.login_public_name)
    val tokenLabel = stringResource(R.string.login_token)
    val unknown = stringResource(R.string.login_unknown)

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 标题
            Text(
                text = userInfoTitle,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Divider()

            // 用户ID
            UserInfoRow(
                label = userIdLabel,
                value = userInfo.id?.toString() ?: unknown
            )

            // 用户名
            UserInfoRow(
                label = usernameLabel,
                value = userInfo.username ?: unknown
            )

            // 邮箱
            UserInfoRow(
                label = emailLabel,
                value = userInfo.email ?: unknown
            )

            // 昵称
            if (!userInfo.nickname.isNullOrBlank()) {
                UserInfoRow(
                    label = nicknameLabel,
                    value = userInfo.nickname
                )
            }

            // 公开名称
            if (!userInfo.publicName.isNullOrBlank()) {
                UserInfoRow(
                    label = publicNameLabel,
                    value = userInfo.publicName
                )
            }

            // Token（如果存在）
            if (!userInfo.token.isNullOrBlank()) {
                Divider(modifier = Modifier.padding(vertical = 4.dp))
                UserInfoRow(
                    label = tokenLabel,
                    value = userInfo.token.take(20) + "...", // 只显示前20个字符
                    isImportant = true
                )
            }
        }
    }
}

/**
 * 用户信息行
 */
@Composable
fun UserInfoRow(
    label: String,
    value: String,
    isImportant: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isImportant) FontWeight.Bold else FontWeight.Normal,
            color = if (isImportant) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * 用户信息卡片预览
 */
@Preview(showBackground = true)
@Composable
fun UserInfoCardPreview() {
    WXXComposeTemplateTheme {
        UserInfoCard(
            userInfo = UserInfo(
                id = 12345,
                username = "testuser",
                email = "test@example.com",
                nickname = "测试用户",
                publicName = "公开名称",
                token = "test_token_1234567890"
            )
        )
    }
}

/**
 * 用户信息行预览
 */
@Preview(showBackground = true)
@Composable
fun UserInfoRowPreview() {
    WXXComposeTemplateTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UserInfoRow(
                label = "用户ID：",
                value = "12345"
            )
            UserInfoRow(
                label = "用户名：",
                value = "testuser"
            )
            UserInfoRow(
                label = "Token：",
                value = "test_token_1234567890",
                isImportant = true
            )
        }
    }
}

