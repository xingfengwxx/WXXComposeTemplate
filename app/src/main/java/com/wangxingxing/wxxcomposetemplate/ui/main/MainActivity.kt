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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
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
import com.google.gson.Gson
import com.wangxingxing.wxxcomposetemplate.data.remote.api.model.Article
import com.wangxingxing.wxxcomposetemplate.ui.articledetail.ArticleDetailScreen
import com.wangxingxing.wxxcomposetemplate.ui.theme.WXXComposeTemplateTheme
import com.wangxingxing.wxxcomposetemplate.ui.home.HomeScreen
import com.wangxingxing.wxxcomposetemplate.ui.demo.DemoScreen
import com.wangxingxing.wxxcomposetemplate.ui.login.LoginScreen
import com.wangxingxing.wxxcomposetemplate.ui.projectcategory.ProjectCategoryScreen
import com.wangxingxing.wxxcomposetemplate.ui.settings.SettingsScreen
import com.wangxingxing.wxxcomposetemplate.ui.settings.SettingsViewModel
import com.wangxingxing.wxxcomposetemplate.ui.settings.ThemeColorScreen
import com.wangxingxing.wxxcomposetemplate.ui.fruit.FruitScreen
import dagger.hilt.android.AndroidEntryPoint
import ui.permission.PermissionScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.wangxingxing.wxxcomposetemplate.ui.local.LocalUserScreen

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
            MainScreenWithTheme()
        }
    }
}

@Composable
fun MainScreenWithTheme() {
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val themeColorIndex by settingsViewModel.themeColorIndex.collectAsState()
    
    WXXComposeTemplateTheme(themeColorIndex = themeColorIndex) {
        MainScreen()
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
                HomeScreen(navController = navController)
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
            composable("project_category") {
                ProjectCategoryScreen()
            }
            composable("local_user") {
                LocalUserScreen()
            }
            composable("fruit") {
                FruitScreen()
            }
            composable("article_detail/{articleJson}") { backStackEntry ->
                val articleJson = backStackEntry.arguments?.getString("articleJson") ?: ""
                val article = try {
                    Gson().fromJson(articleJson, Article::class.java)
                } catch (e: Exception) {
                    null
                }
                if (article != null) {
                    ArticleDetailScreen(
                        article = article,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }
            composable("settings") {
                SettingsScreen(navController = navController)
            }
            composable("theme_color") {
                ThemeColorScreen(navController = navController)
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
        NavigationBarItem(
            selected = currentRoute == "settings",
            onClick = { navController.navigate("settings") },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = stringResource(com.wangxingxing.wxxcomposetemplate.R.string.nav_settings)
                )
            },
            label = { Text(stringResource(com.wangxingxing.wxxcomposetemplate.R.string.nav_settings)) }
        )
        NavigationBarItem(
            selected = currentRoute == "fruit",
            onClick = { navController.navigate("fruit") },
            icon = {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "水果"
                )
            },
            label = { Text("水果") }
        )
    }
}
