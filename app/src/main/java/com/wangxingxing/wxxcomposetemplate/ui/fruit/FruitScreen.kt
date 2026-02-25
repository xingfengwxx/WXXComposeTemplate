package com.wangxingxing.wxxcomposetemplate.ui.fruit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.PaddingValues
import com.wangxingxing.wxxcomposetemplate.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FruitScreen() {
    var searchText by remember { mutableStateOf("") }
    var selectedTabIndex by remember { mutableStateOf(0) }
    
    val tabs = listOf("全部", "国产水果", "进口水果", "当季水果", "热带水果", "坚果干货")
    val categories = listOf(
        CategoryItem("新鲜水果", Color(0xFFFF5252)),
        CategoryItem("肉禽蛋", Color(0xFFFF9800)),
        CategoryItem("海鲜水产", Color(0xFF2196F3)),
        CategoryItem("蔬菜豆制品", Color(0xFF4CAF50)),
        CategoryItem("粮油调味", Color(0xFF9C27B0)),
        CategoryItem("牛奶乳品", Color(0xFF607D8B)),
        CategoryItem("休闲零食", Color(0xFFFF4081)),
        CategoryItem("酒水饮料", Color(0xFF00BCD4))
    )
    
    val products = listOf(
        ProductItem("泰国山竹", "5A大果新鲜当季水果", "¥59.9", "9821人付款", Color(0xFF9C27B0)),
        ProductItem("红心火龙果", "新鲜红肉火龙果2个装", "¥16.8", "1254人付款", Color(0xFFFF5252)),
        ProductItem("进口车厘子", "智利车厘子J级2斤装", "¥128.0", "3654人付款", Color(0xFFF44336)),
        ProductItem("海南芒果", "新鲜芒果大青芒5斤装", "¥39.9", "2156人付款", Color(0xFFFF9800)),
        ProductItem("新疆香梨", "新鲜香梨2.5kg装", "¥45.8", "1892人付款", Color(0xFF2196F3)),
        ProductItem("陕西红富士", "新鲜红富士苹果5斤装", "¥29.9", "2345人付款", Color(0xFF4CAF50))
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // 搜索栏
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = {
                    Text(
                        text = "搜索商品",
                        fontSize = 14.sp,
                        color = Color(0xFF999999)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF999999)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(20.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF0F0F0),
                    unfocusedContainerColor = Color(0xFFF0F0F0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )
        }
        
        // 导航标签
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            containerColor = Color.White,
            contentColor = Color.Black,
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .padding(start = tabPositions[selectedTabIndex].left)
                        .width(tabPositions[selectedTabIndex].width)
                        .height(2.dp)
                        .background(Color(0xFFFF4444))
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 14.sp,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTabIndex == index) Color(0xFFFF4444) else Color.Black
                        )
                    }
                )
            }
        }
        
        // 主内容区域
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            // Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(120.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFE3F2FD)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Banner title",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
            
            // 分类图标
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.White)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(categories.size) {
                    val category = categories[it]
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(60.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .background(category.color)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = category.name.first().toString(),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = category.name,
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // 产品网格
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(products.size) {
                    val product = products[it]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clickable { },
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            // 产品图片
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .background(product.color),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = product.name.first().toString(),
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            
                            // 产品信息
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = product.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black,
                                    maxLines = 1
                                )
                                Text(
                                    text = product.description,
                                    fontSize = 12.sp,
                                    color = Color(0xFF666666),
                                    maxLines = 1,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Text(
                                        text = product.price,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFFF4444)
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = product.sales,
                                        fontSize = 11.sp,
                                        color = Color(0xFF999999)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

data class CategoryItem(val name: String, val color: Color)
data class ProductItem(val name: String, val description: String, val price: String, val sales: String, val color: Color)
