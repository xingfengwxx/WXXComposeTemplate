package com.wangxingxing.wxxcomposetemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wangxingxing.wxxcomposetemplate.ui.theme.WXXComposeTemplateTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wangxingxing.wxxcomposetemplate.ui.demo.DemoScreen
import com.wangxingxing.wxxcomposetemplate.ui.local.LocalUserScreen

/**
 * author : 王星星
 * date : 2025/11/20 20:10
 * email : 1099420259@qq.com
 * description : 
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WXXComposeTemplateTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
    WXXComposeTemplateTheme {
        // 预览中仅展示空容器
    }
}