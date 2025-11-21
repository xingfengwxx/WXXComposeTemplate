# WXX Compose Template

> 企业级 Android Compose 项目架构模板

一个基于 **MVVM 架构**、**Jetpack Compose** 和 **Hilt** 依赖注入的现代化 Android 项目模板，遵循 Google 官方最佳实践，开箱即用，具备良好的扩展性和企业级代码规范。

---

## 📋 目录

- [技术架构](#技术架构)
- [技术栈](#技术栈)
- [项目结构](#项目结构)
- [快速开始](#快速开始)
- [功能特性](#功能特性)
- [架构设计](#架构设计)
- [版本要求](#版本要求)

---

## 🏗 技术架构

### 架构模式

本项目采用 **MVVM（Model-View-ViewModel）** 架构模式，严格遵循分层设计：

```
┌─────────────────────────────────────────┐
│           View Layer (UI)                │
│  ┌──────────────┐  ┌──────────────┐    │
│  │   Compose    │  │  XML+Fragment│    │
│  └──────────────┘  └──────────────┘    │
└─────────────────────────────────────────┘
                    ↕
┌─────────────────────────────────────────┐
│        ViewModel Layer                   │
│  ┌──────────────────────────────────┐   │
│  │   StateFlow / LiveData           │   │
│  │   状态管理与生命周期管理          │   │
│  └──────────────────────────────────┘   │
└─────────────────────────────────────────┘
                    ↕
┌─────────────────────────────────────────┐
│        Repository Layer                  │
│  ┌──────────────────────────────────┐   │
│  │   数据统一入口                     │   │
│  │   Remote + Local 数据源管理       │   │
│  └──────────────────────────────────┘   │
└─────────────────────────────────────────┘
                    ↕
┌─────────────────────────────────────────┐
│        Data Layer                        │
│  ┌──────────────┐  ┌──────────────┐    │
│  │   Remote     │  │    Local     │    │
│  │  (Retrofit)  │  │  (Room/SP)   │    │
│  └──────────────┘  └──────────────┘    │
└─────────────────────────────────────────┘
```

### 架构特点

- ✅ **单 Activity + 多 Fragment 模式**：简化导航和生命周期管理
- ✅ **XML + Compose 混合开发**：支持渐进式迁移，灵活选择 UI 框架
- ✅ **Repository 模式**：统一数据获取入口，支持多数据源
- ✅ **ViewModel 生命周期安全**：自动处理配置变更，数据不丢失
- ✅ **依赖注入（Hilt）**：解耦组件，提升可测试性

---

## 🛠 技术栈

### 核心框架

| 技术 | 版本 | 说明 |
|------|------|------|
| **Kotlin** | 2.2.21 | 主要开发语言 |
| **Jetpack Compose** | 2025.11.01 | 现代 UI 框架 |
| **Hilt** | 2.57.2 | 依赖注入框架 |
| **Navigation** | 2.9.6 | 页面导航管理 |

### 网络请求

| 技术 | 版本 | 说明 |
|------|------|------|
| **Retrofit** | 3.0.0 | 网络请求框架 |
| **OkHttp** | 5.3.1 | HTTP 客户端 |
| **Gson** | 2.13.2 | JSON 解析 |

### 数据存储

| 技术 | 版本 | 说明 |
|------|------|------|
| **Room** | 2.8.4 | 本地数据库 |
| **DataStore** | 1.2.0 | 替代 SharedPreferences |

### 图片处理

| 技术 | 版本 | 说明 |
|------|------|------|
| **Coil** | 2.7.0 | 图片加载库 |
| **PictureSelector** | v3.11.2 | 图片选择器 |

### 媒体播放

| 技术 | 版本 | 说明 |
|------|------|------|
| **ExoPlayer** | 2.19.1 | 视频播放器 |

### UI 组件

| 技术 | 版本 | 说明 |
|------|------|------|
| **DslTabLayout** | 3.0.0 | Tab 布局组件 |
| **BaseRecyclerViewAdapterHelper** | 4.3.2 | RecyclerView 适配器 |
| **MPAndroidChart** | 3.1.0 | 图表库 |

### 工具库

| 技术 | 版本 | 说明 |
|------|------|------|
| **XXPermissions** | 26.5 | 权限管理 |
| **AndroidUtilCode** | 1.31.1 | 常用工具类 |
| **AutoSize** | 1.2.1 | 屏幕适配 |

### Jetpack 组件

| 技术 | 版本 | 说明 |
|------|------|------|
| **WorkManager** | 2.11.0 | 后台任务管理 |
| **Startup** | 1.2.0 | 启动优化 |

---

## 📁 项目结构

```
app/
├── base/                           # 基础类
│   ├── BaseActivity.kt            # Activity 基类
│   ├── BaseFragment.kt            # Fragment 基类
│   ├── BaseViewModel.kt           # ViewModel 基类
│   └── BaseComposePage.kt         # Compose 页面基类
│
├── data/                           # 数据层
│   ├── remote/                    # 远程数据源
│   │   └── api/                   # API 接口
│   │       ├── ApiResult.kt       # 统一响应封装
│   │       ├── ApiService.kt       # API 接口定义
│   │       └── NetworkModule.kt   # 网络模块配置
│   ├── local/                     # 本地数据源
│   │   └── db/                    # 数据库
│   │       ├── AppDatabase.kt     # Room 数据库
│   │       └── DatabaseModule.kt  # 数据库模块配置
│   └── repository/                # Repository 层
│       └── DemoRepository.kt      # 示例 Repository
│
├── di/                            # 依赖注入
│   └── AppModule.kt               # 应用模块配置
│
├── ui/                            # UI 层
│   ├── main/                      # 主页面
│   │   ├── MainActivity.kt        # 主 Activity
│   │   └── MainViewModel.kt      # 主页面 ViewModel
│   ├── home/                      # 首页
│   │   └── HomeScreen.kt         # 首页 Compose 页面
│   ├── demo/                      # 示例列表
│   │   ├── DemoScreen.kt         # 示例列表页面
│   │   └── DemoViewModel.kt      # 示例 ViewModel
│   └── permission/                # 权限示例
│       └── PermissionScreen.kt    # 权限请求页面
│
├── utils/                         # 工具类
│   ├── PermissionHelper.kt        # 权限工具类
│   ├── NetworkUtils.kt            # 网络工具类
│   └── ScreenAdapter.kt          # 屏幕适配工具
│
├── widget/                        # 自定义组件
│   └── CustomView.kt              # 自定义 View 示例
│
└── App.kt                         # 应用入口类
```

---

## 🚀 快速开始

### 环境要求

- **Android Studio**：Hedgehog (2023.1.1) 或更高版本
- **JDK**：11 或更高版本
- **Gradle**：8.13.1
- **Android SDK**：
  - minSdk：24 (Android 7.0)
  - targetSdk：36 (Android 15)
  - compileSdk：36

### 安装步骤

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd WXXComposeTemplate
   ```

2. **同步依赖**
   - 打开 Android Studio
   - 点击 "Sync Now" 同步 Gradle 依赖

3. **运行项目**
   - 连接 Android 设备或启动模拟器
   - 点击 "Run" 按钮运行项目

### 配置说明

#### 网络配置

在 `NetworkModule.kt` 中修改 `BASE_URL`：

```kotlin
private const val BASE_URL = "https://api.example.com/"
```

#### 数据库配置

在 `DatabaseModule.kt` 中可自定义数据库名称和版本。

---

## ✨ 功能特性

### 1. 首页功能

- ✅ 底部导航栏（使用 Compose Navigation）
- ✅ 首页 Compose 页面（状态管理示例）
- ✅ 示例列表页面（数据加载示例）

### 2. 网络请求

- ✅ 统一 API 响应封装（`ApiResult`）
- ✅ Retrofit + OkHttp 配置
- ✅ 日志拦截器
- ✅ 错误处理机制

### 3. 权限管理

- ✅ 封装 XXPermissions 工具类
- ✅ 协程方式请求权限
- ✅ 存储权限示例
- ✅ 相机权限示例

### 4. 数据存储

- ✅ Room 数据库配置
- ✅ DataStore 替代 SharedPreferences
- ✅ Repository 模式数据管理

### 5. 屏幕适配

- ✅ AutoSize 屏幕适配
- ✅ 自动初始化配置

---

## 🎯 架构设计

### 数据流向

```
用户操作
   ↓
View (Compose/XML)
   ↓
ViewModel (状态管理)
   ↓
Repository (数据统一入口)
   ↓
DataSource (Remote/Local)
   ↓
数据返回
```

### 状态管理

使用 `StateFlow` 进行状态管理：

```kotlin
// ViewModel 中定义状态
val uiState = MutableStateFlow<UiState>(UiState.Idle)

// Compose 中观察状态
val state by viewModel.getUiState().collectAsState()
```

### 依赖注入

使用 Hilt 进行依赖注入：

```kotlin
@HiltViewModel
class DemoViewModel @Inject constructor(
    private val repository: DemoRepository
) : BaseViewModel()
```

### 网络请求封装

统一使用 `ApiResult` 封装响应：

```kotlin
sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val code: Int, val message: String) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}
```

---

## 📱 版本要求

- **最低支持**：Android 7.0 (API 24)
- **目标版本**：Android 15 (API 36)
- **编译版本**：Android 15 (API 36)

---

## 📝 代码规范

### KDoc 注释

所有类必须包含以下格式的注释：

```kotlin
/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 类功能说明
 */
```

### 命名规范

- **类名**：大驼峰（PascalCase）
- **方法名**：小驼峰（camelCase）
- **常量**：全大写下划线分隔（UPPER_SNAKE_CASE）
- **包名**：全小写（lowercase）

### 架构规范

- ✅ 所有类单一职责
- ✅ 所有方法命名语义清晰
- ✅ 禁止硬编码 magic number
- ✅ 所有网络请求必须使用 suspend
- ✅ 所有数据模型使用 data class

---

## 🔧 扩展开发

### 添加新页面

1. 在 `ui/` 目录下创建新的功能模块
2. 创建对应的 ViewModel（继承 `BaseViewModel`）
3. 创建 Compose 页面或 Fragment
4. 在 Navigation 中注册路由

### 添加网络接口

1. 在 `ApiService.kt` 中添加接口定义
2. 在对应的 Repository 中调用接口
3. 在 ViewModel 中使用 Repository

### 添加数据库表

1. 创建 Entity 类
2. 创建 DAO 接口
3. 在 `AppDatabase.kt` 中注册
4. 创建对应的 Repository

---

## 📄 许可证

本项目采用企业级代码规范，仅供学习和参考使用。

---

## 👤 作者

**王星星**

- Email: 1099420259@qq.com
- Date: 2025/11/20 19:26

---

## 🙏 致谢

感谢以下开源项目的支持：

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Hilt](https://dagger.dev/hilt/)
- [Retrofit](https://square.github.io/retrofit/)
- 以及其他所有使用的开源库

---

**⭐ 如果这个项目对你有帮助，请给个 Star 支持一下！**
