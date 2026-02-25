# 输出模式指南

## 代码输出模式

### 1. Compose 组件模式

#### 基础组件模式

```kotlin
@Composable
fun ComponentName(
    // 参数列表
    parameter1: Type,
    parameter2: Type = defaultValue,
    onEvent: () -> Unit
) {
    // 组件实现
    Column(modifier = Modifier.fillMaxWidth()) {
        // 组件内容
    }
}
```

#### 屏幕组件模式

```kotlin
@Composable
fun ScreenName(viewModel: ScreenViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Screen Title") }
            )
        }
    ) {
        // 屏幕内容
        when (uiState) {
            is ScreenUiState.Loading -> {
                // 加载状态
            }
            is ScreenUiState.Success -> {
                // 成功状态
            }
            is ScreenUiState.Error -> {
                // 错误状态
            }
        }
    }
}
```

#### ViewModel 模式

```kotlin
@HiltViewModel
class ScreenViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<ScreenUiState>(ScreenUiState.Loading)
    val uiState: StateFlow<ScreenUiState> = _uiState.asStateFlow()
    
    fun loadData() {
        viewModelScope.launch {
            _uiState.value = ScreenUiState.Loading
            try {
                val data = useCase.execute()
                _uiState.value = ScreenUiState.Success(data)
            } catch (e: Exception) {
                _uiState.value = ScreenUiState.Error(e.message)
            }
        }
    }
}

sealed class ScreenUiState {
    object Loading : ScreenUiState()
    data class Success(val data: DataType) : ScreenUiState()
    data class Error(val message: String?) : ScreenUiState()
}
```

### 2. 布局模式

#### 线性布局

```kotlin
Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
) {
    // 垂直排列的组件
}

Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
) {
    // 水平排列的组件
}
```

#### 网格布局

```kotlin
LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp)
) {
    items(items) {
        // 网格项
    }
}
```

#### 列表布局

```kotlin
LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
) {
    items(items) {
        // 列表项
    }
}
```

### 3. 样式模式

#### 颜色方案

```kotlin
// 从主题中获取颜色
val colors = MaterialTheme.colorScheme

// 使用颜色
Text(
    text = "Text",
    color = colors.onSurface
)

Button(
    colors = ButtonDefaults.buttonColors(
        containerColor = colors.primary
    )
) {
    Text("Button")
}
```

#### 排版样式

```kotlin
// 从主题中获取排版
val typography = MaterialTheme.typography

// 使用排版
Text(
    text = "Title",
    style = typography.titleLarge
)

Text(
    text = "Body",
    style = typography.bodyMedium
)
```

#### 间距和尺寸

```kotlin
// 常量定义
private val SpacingSmall = 8.dp
private val SpacingMedium = 16.dp
private val SpacingLarge = 24.dp

// 使用间距
Column(
    verticalArrangement = Arrangement.spacedBy(SpacingMedium)
) {
    // 组件
}

// 使用尺寸
Box(modifier = Modifier.size(48.dp)) {
    // 内容
}
```

## 质量标准

### 1. 代码质量

- **可读性**：使用清晰的命名和结构
- **简洁性**：避免冗余代码和复杂逻辑
- **一致性**：遵循项目的代码风格和规范
- **可维护性**：添加适当的注释和文档

### 2. 视觉质量

- **准确性**：与原始截图视觉一致
- **一致性**：与项目的设计系统一致
- **响应式**：在不同屏幕尺寸上正常显示
- **美观性**：遵循现代设计原则

### 3. 功能质量

- **正确性**：功能按预期工作
- **可靠性**：处理错误和边界情况
- **性能**：避免不必要的计算和渲染
- **可访问性**：符合可访问性标准

## 输入输出示例

### 登录页面示例

#### 输入
```
请基于此截图创建一个登录页面
[登录页面截图]
```

#### 输出

```kotlin
// LoginScreen.kt
@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val context = LocalContext.current
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("登录") }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(24.dp)
            ) {
                Text(
                    text = "App",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // 邮箱输入
            OutlinedTextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                label = { Text("邮箱") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 密码输入
            OutlinedTextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                label = { Text("密码") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // 登录按钮
            Button(
                onClick = {
                    viewModel.login(emailState.value, passwordState.value)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("登录")
                }
            }
            
            if (uiState.error != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = uiState.error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // 注册链接
            Row(horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "还没有账号？",
                    style = MaterialTheme.typography.bodyMedium
                )
                TextButton(onClick = { /* 导航到注册页面 */ }) {
                    Text("立即注册")
                }
            }
        }
    }
}

// LoginViewModel.kt
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    
    data class LoginUiState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val isSuccess: Boolean = false
    )
    
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            
            try {
                val result = loginRepository.login(email, password)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSuccess = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "登录失败"
                )
            }
        }
    }
}
```

### 首页示例

#### 输入
```
请基于此截图创建一个首页，包含顶部导航栏、轮播图、分类列表和推荐内容
[首页截图]
```

#### 输出

```kotlin
// HomeScreen.kt
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("首页") },
                actions = {
                    IconButton(onClick = { /* 搜索 */ }) {
                        Icon(Icons.Default.Search, contentDescription = "搜索")
                    }
                    IconButton(onClick = { /* 消息 */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "消息")
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 轮播图
            if (uiState.banners.isNotEmpty()) {
                BannerCarousel(banners = uiState.banners)
            }
            
            // 分类列表
            CategoryList(categories = uiState.categories)
            
            // 推荐内容
            if (uiState.recommended.isNotEmpty()) {
                SectionHeader(title = "推荐内容")
                RecommendedList(items = uiState.recommended)
            }
        }
    }
}

@Composable
fun BannerCarousel(banners: List<Banner>) {
    // 轮播图实现
}

@Composable
fun CategoryList(categories: List<Category>) {
    // 分类列表实现
}

@Composable
fun SectionHeader(title: String) {
    //  section header实现
}

@Composable
fun RecommendedList(items: List<Item>) {
    // 推荐列表实现
}

// HomeViewModel.kt
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bannerRepository: BannerRepository,
    private val categoryRepository: CategoryRepository,
    private val itemRepository: ItemRepository
) : ViewModel() {
    
    data class HomeUiState(
        val banners: List<Banner> = emptyList(),
        val categories: List<Category> = emptyList(),
        val recommended: List<Item> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadHomeData()
    }
    
    fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            
            try {
                val banners = bannerRepository.getBanners()
                val categories = categoryRepository.getCategories()
                val recommended = itemRepository.getRecommended()
                
                _uiState.value = _uiState.value.copy(
                    banners = banners,
                    categories = categories,
                    recommended = recommended,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}
```

## 总结

本指南提供了一套完整的输出模式和质量标准，用于确保生成的代码符合项目的技术栈和设计要求。通过遵循这些模式和标准，可以生成高质量、一致和可维护的代码，同时确保视觉效果与原始截图一致。

在使用本技能时，请根据具体的需求和项目规范，适当调整输出模式和质量标准，以达到最佳的效果。