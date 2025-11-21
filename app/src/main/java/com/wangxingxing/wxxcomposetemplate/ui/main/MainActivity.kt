package com.wangxingxing.wxxcomposetemplate.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wangxingxing.wxxcomposetemplate.ui.theme.WXXComposeTemplateTheme
import com.wangxingxing.wxxcomposetemplate.ui.home.HomeScreen
import com.wangxingxing.wxxcomposetemplate.ui.demo.DemoScreen
import com.wangxingxing.wxxcomposetemplate.ui.login.LoginScreen
import dagger.hilt.android.AndroidEntryPoint
import ui.permission.PermissionScreen

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 主 Activity，包含底部导航栏
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WXXComposeTemplateTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen()
            }
            composable("demo") {
                DemoScreen(navController = navController)
            }
            composable("permission") {
                PermissionScreen()
            }
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        // 登录成功后可以执行的操作，比如返回上一页
//                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntryState = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntryState.value?.destination?.route

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = stringResource(com.wangxingxing.wxxcomposetemplate.R.string.nav_home)
                )
            },
            label = { Text(stringResource(com.wangxingxing.wxxcomposetemplate.R.string.nav_home)) }
        )
        NavigationBarItem(
            selected = currentRoute == "demo",
            onClick = { navController.navigate("demo") },
            icon = {
                Icon(
                    imageVector = Icons.Filled.List,
                    contentDescription = stringResource(com.wangxingxing.wxxcomposetemplate.R.string.nav_demo)
                )
            },
            label = { Text(stringResource(com.wangxingxing.wxxcomposetemplate.R.string.nav_demo)) }
        )
    }
}
