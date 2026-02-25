# 工作流程指南

## 多步骤流程

### 1. 截图分析流程

#### 步骤 1: 接收和预处理截图
- 确保截图清晰可见
- 检查截图是否包含完整的UI元素
- 调整截图大小和方向（如果需要）

#### 步骤 2: 组件识别
- 识别基本UI组件（按钮、文本框、卡片等）
- 识别复杂组件（列表、网格、表单等）
- 标记组件边界和类型

#### 步骤 3: 布局分析
- 分析组件的排列方式（线性、网格、层叠等）
- 测量组件间的间距和对齐方式
- 识别布局容器和分割线

#### 步骤 4: 样式提取
- 提取主要和次要颜色
- 识别字体类型、大小和粗细
- 分析阴影、圆角和边框样式

### 2. 代码生成流程

#### 步骤 1: 确定组件层次结构
- 设计根组件和子组件的关系
- 识别可复用的组件
- 规划组件的职责和接口

#### 步骤 2: 生成基础代码结构
- 创建屏幕文件和ViewModel文件
- 设计可复用组件目录
- 实现基本的布局结构

#### 步骤 3: 实现组件样式
- 应用提取的颜色方案
- 设置字体和排版样式
- 实现组件的视觉效果

#### 步骤 4: 添加交互逻辑
- 实现点击事件和导航
- 添加状态管理逻辑
- 处理用户输入和反馈

#### 步骤 5: 集成和优化
- 与项目主题和导航集成
- 优化性能和可访问性
- 添加适当的注释和文档

### 3. 验证和测试流程

#### 步骤 1: 代码审查
- 检查代码是否符合项目规范
- 验证组件命名和结构
- 确保代码可读性和可维护性

#### 步骤 2: 视觉验证
- 对比生成的UI与原始截图
- 检查颜色、间距和对齐
- 验证在不同屏幕尺寸上的显示效果

#### 步骤 3: 功能测试
- 测试交互功能是否正常
- 验证状态管理和数据流
- 检查错误处理和边界情况

#### 步骤 4: 性能测试
- 检查组件重组频率
- 测试滚动和动画性能
- 验证内存使用情况

## 条件逻辑

### 1. 基于截图复杂度的处理

#### 简单截图（单个组件）
- 直接生成对应组件的代码
- 提供组件使用示例
- 建议集成到现有屏幕

#### 中等复杂度（完整屏幕）
- 生成完整的屏幕代码
- 提取可复用组件
- 提供ViewModel和状态管理

#### 复杂截图（多屏幕或复杂交互）
- 分析屏幕间的导航关系
- 设计状态管理架构
- 提供完整的功能实现方案

### 2. 基于项目架构的适配

#### 现有项目集成
- 分析项目的目录结构
- 遵循现有的命名规范
- 集成到现有的导航和依赖注入系统

#### 新项目初始化
- 建议标准的项目结构
- 提供完整的依赖配置
- 实现基本的应用架构

### 3. 基于技术栈的代码生成

#### Jetpack Compose (Material 3)
- 使用最新的Compose API
- 遵循Material 3设计规范
- 实现响应式布局

#### View System (Legacy)
- 生成XML布局文件
- 实现对应的Activity/Fragment
- 提供 findViewById 或 ViewBinding 实现

#### Hybrid Approach
- 建议逐步迁移到Compose
- 提供Compose和View的互操作方案
- 设计兼容两种系统的架构

## 最佳实践

### 1. 组件设计

- **单一职责**：每个组件只负责一个功能
- **可配置性**：通过参数实现组件的灵活性
- **可测试性**：设计易于测试的组件结构
- **可访问性**：确保组件符合可访问性标准

### 2. 代码生成

- **一致性**：生成的代码风格与项目保持一致
- **简洁性**：避免生成冗余代码
- **可维护性**：提供清晰的代码结构和注释
- **可扩展性**：设计易于扩展的组件架构

### 3. 性能优化

- **避免过度重组**：使用 remember 和 derivedStateOf
- **延迟加载**：对于复杂组件使用懒加载
- **图片优化**：合理使用图片缓存和压缩
- **动画性能**：使用 Compose 的动画API实现流畅动画

### 4. 用户体验

- **响应式设计**：适配不同屏幕尺寸
- **触觉反馈**：为交互添加适当的反馈
- **加载状态**：处理数据加载和错误状态
- **导航体验**：提供流畅的页面过渡效果

## 故障排除

### 1. 常见问题

#### 截图分析问题
- **组件识别错误**：调整识别算法或手动修正
- **颜色提取不准确**：使用颜色选择工具手动调整
- **布局分析偏差**：参考设计规范或手动调整布局

#### 代码生成问题
- **编译错误**：检查依赖和API版本
- **运行时错误**：检查状态管理和空安全
- **性能问题**：优化组件结构和渲染逻辑

#### 集成问题
- **导航错误**：检查导航图和参数传递
- **依赖注入错误**：确保正确的Hilt配置
- **主题集成错误**：检查主题属性和资源引用

### 2. 解决方案

#### 分析增强
- 使用多个截图从不同角度分析
- 参考设计规范文档
- 与用户确认关键设计决策

#### 代码修正
- 自动生成代码后进行手动调整
- 使用代码审查工具检查问题
- 参考项目中的现有代码模式

#### 性能优化
- 使用 Compose 性能分析工具
- 优化组件结构和状态管理
- 减少不必要的计算和渲染

## 输入输出示例

### 复杂流程示例

#### 输入
```
请基于此截图创建一个包含商品列表和详情的完整功能模块
[商品列表截图]
[商品详情截图]
```

#### 处理流程
1. **分析截图**
   - 识别列表中的商品卡片组件
   - 分析详情页的布局结构
   - 提取颜色方案和样式

2. **设计架构**
   - 创建商品列表和详情屏幕
   - 设计商品数据模型
   - 实现导航逻辑

3. **生成代码**
   - 实现商品卡片可复用组件
   - 生成列表和详情屏幕代码
   - 添加ViewModel和状态管理

4. **集成和优化**
   - 与项目导航系统集成
   - 优化列表滚动性能
   - 添加错误处理和加载状态

5. **测试和验证**
   - 测试列表加载和滚动
   - 验证详情页导航和数据显示
   - 检查在不同设备上的显示效果

#### 输出
```kotlin
// 商品卡片组件
@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "¥${product.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// 商品列表屏幕
@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("商品列表") })
        }
    ) {
        when (uiState) {
            is ProductListUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ProductListUiState.Success -> {
                val products = (uiState as ProductListUiState.Success).products
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(products) {
                        ProductCard(
                            product = it,
                            onClick = {
                                navController.navigate(
                                    "product_detail/${it.id}"
                                )
                            }
                        )
                    }
                }
            }
            is ProductListUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "加载失败，请重试",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

// 商品详情屏幕
@Composable
fun ProductDetailScreen(
    productId: String,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }
    
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("商品详情") })
        }
    ) {
        when (uiState) {
            is ProductDetailUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ProductDetailUiState.Success -> {
                val product = (uiState as ProductDetailUiState.Success).product
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    AsyncImage(
                        model = product.imageUrl,
                        contentDescription = product.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "¥${product.price}",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = product.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { /* 添加到购物车 */ }
                        ) {
                            Text("添加到购物车")
                        }
                    }
                }
            }
            is ProductDetailUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "加载失败，请重试",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
```