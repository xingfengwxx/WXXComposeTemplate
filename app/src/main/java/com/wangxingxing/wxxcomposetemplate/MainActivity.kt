package com.wangxingxing.wxxcomposetemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wangxingxing.wxxcomposetemplate.ui.demo.DemoScreen
import com.wangxingxing.wxxcomposetemplate.ui.local.LocalUserScreen
import com.wangxingxing.wxxcomposetemplate.ui.theme.AppTheme
import com.wangxingxing.wxxcomposetemplate.ui.theme.ThemeMode
import com.wangxingxing.wxxcomposetemplate.ui.theme.ThemeViewModel

/**
 * author : 王星星
 * date : 2025/11/20 20:10
 * email : 1099420259@qq.com
 * description : 
 */
class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState = themeViewModel.state.collectAsStateWithLifecycle()

            AppTheme(
                mode = uiState.value.mode,
                dynamicColor = uiState.value.dynamicColor,
                themeColorIndex = uiState.value.themeColorIndex
            ) {
                val navController = rememberNavController()
                var menuExpanded by remember { mutableStateOf(false) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "WXX Compose Template") },
                            actions = {
                                IconButton(onClick = { menuExpanded = !menuExpanded }) {
                                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Theme Settings")
                                }
                                DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                                    DropdownMenuItem(
                                        text = { Text("跟随系统") },
                                        onClick = {
                                            themeViewModel.dispatch(ThemeViewModel.UiEvent.ChangeMode(ThemeMode.SYSTEM))
                                            menuExpanded = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("浅色主题") },
                                        onClick = {
                                            themeViewModel.dispatch(ThemeViewModel.UiEvent.ChangeMode(ThemeMode.LIGHT))
                                            menuExpanded = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("深色主题") },
                                        onClick = {
                                            themeViewModel.dispatch(ThemeViewModel.UiEvent.ChangeMode(ThemeMode.DARK))
                                            menuExpanded = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text(if (uiState.value.dynamicColor) "关闭动态颜色" else "开启动态颜色") },
                                        onClick = {
                                            themeViewModel.dispatch(ThemeViewModel.UiEvent.ToggleDynamicColor(!uiState.value.dynamicColor))
                                            menuExpanded = false
                                        }
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                titleContentColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "demo",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("demo") {
                            DemoScreen(navController = navController)
                        }
                        composable("local_user") {
                            LocalUserScreen()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    AppTheme() {
        // 预览中仅展示空容器
    }
}