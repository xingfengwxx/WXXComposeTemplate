package com.wangxingxing.wxxcomposetemplate.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wangxingxing.wxxcomposetemplate.R
import com.wangxingxing.wxxcomposetemplate.ui.theme.WXXComposeTemplateTheme

/**
 * author : 王星星
 * date : 2025/01/20
 * email : 1099420259@qq.com
 * description : 主题色选择页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeColorScreen(
    navController: NavController? = null,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val themeColorIndex by viewModel.themeColorIndex.collectAsState()

    // 定义常用颜色列表
    val themeColors = listOf(
        ThemeColorItem(0, Color(0xFF6650a4), "默认紫色"),
        ThemeColorItem(1, Color(0xFF1976D2), "蓝色"),
        ThemeColorItem(2, Color(0xFF388E3C), "绿色"),
        ThemeColorItem(3, Color(0xFFF57C00), "橙色"),
        ThemeColorItem(4, Color(0xFFD32F2F), "红色"),
        ThemeColorItem(5, Color(0xFF7B1FA2), "深紫色"),
        ThemeColorItem(6, Color(0xFF0288D1), "浅蓝色"),
        ThemeColorItem(7, Color(0xFF5D4037), "棕色")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.settings_theme_color))
                },
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.settings_back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 颜色网格
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(themeColors) { colorItem ->
                    val isSelected = themeColorIndex == colorItem.index
                    
                    ColorItem(
                        color = colorItem.color,
                        isSelected = isSelected,
                        onClick = {
                            viewModel.saveThemeColor(colorItem.index)
                        }
                    )
                }
            }
        }
    }
}

/**
 * 颜色项数据类
 */
data class ThemeColorItem(
    val index: Int,
    val color: Color,
    val name: String
)

/**
 * 颜色选择项组件
 */
@Composable
fun ColorItem(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(color)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThemeColorScreenPreview() {
    WXXComposeTemplateTheme {
        // Preview需要NavController，这里只预览UI结构
    }
}

@Preview(showBackground = true)
@Composable
fun ColorItemPreview() {
    WXXComposeTemplateTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ColorItem(
                color = Color(0xFF6650a4),
                isSelected = true,
                onClick = {}
            )
            ColorItem(
                color = Color(0xFF1976D2),
                isSelected = false,
                onClick = {}
            )
        }
    }
}

