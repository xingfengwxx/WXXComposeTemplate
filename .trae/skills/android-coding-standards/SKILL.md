---
name: android-coding-standards
description: 符合WXXComposeTemplate项目架构规范的Android编码规范。用于指导开发人员遵循项目的架构设计、编码风格、命名规范和最佳实践。
---

# WXXComposeTemplate 编码规范

## 项目架构

### 包结构

```
com.wangxingxing.wxxcomposetemplate/
├── base/           # 基础组件
│   ├── coroutines/ # 协程相关
│   ├── mvi/        # MVI架构相关
│   ├── BaseActivity.kt
│   ├── BaseComposePage.kt
│   ├── BaseFragment.kt
│   └── BaseViewModel.kt
├── data/           # 数据层
│   ├── local/      # 本地数据
│   ├── remote/     # 远程数据
│   └── repository/ # 仓库层
├── di/             # 依赖注入
├── domain/         # 领域层
│   ├── model/      # 领域模型
│   ├── repository/ # 领域仓库接口
│   └── usecase/    # 用例
├── ext/            # 扩展函数
├── ui/             # UI层
│   ├── screen/     # 页面
│   └── theme/      # 主题
├── utils/          # 工具类
├── widget/         # 自定义组件
└── App.kt          # 应用入口
```

### 架构模式

- **MVVM**架构：使用ViewModel + Compose
- **依赖注入**：使用Hilt
- **数据分层**：data → domain → ui
- **Repository模式**：统一数据访问接口

## 编码风格

### 代码格式

- 使用4个空格进行缩进
- 每行不超过100个字符
- 类和函数之间空2行
- 函数内部逻辑块之间空1行

### 命名规范

#### 包名
- 小写字母，单词间用点分隔
- 示例：`com.wangxingxing.wxxcomposetemplate.ui.home`

#### 类名
- 大驼峰命名法
- 示例：`HomeViewModel`、`BaseComposePage`

#### 函数名
- 小驼峰命名法
- 动词开头，描述函数功能
- 示例：`loadData()`、`initLog()`

#### 变量名
- 小驼峰命名法
- 描述性命名
- 示例：`userName`、`isLoading`

#### 常量名
- 全大写，单词间用下划线分隔
- 示例：`const val TAG = "wxx"`

#### 伴生对象
- 使用`companion object`
- 常量和静态方法放在伴生对象中

### 注释规范

#### 类注释

```kotlin
/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description :
 */
class App : Application() {
    // ...
}
```

#### 函数注释

```kotlin
/**
 * 加载数据
 */
fun loadData() {
    // ...
}
```

## 代码规范

### 类和对象

- 优先使用`class`，避免过度使用`object`
- 避免使用静态变量，优先使用依赖注入
- 伴生对象中的属性使用`const`或`lateinit`

### 函数

- 函数体尽量简洁，单个函数不超过50行
- 避免过长的参数列表，超过4个参数考虑使用数据类
- 优先使用Kotlin的扩展函数

### 异常处理

- 明确处理异常，避免静默忽略
- 使用try-catch包裹可能抛出异常的代码

### 协程使用

- 使用`viewModelScope`进行ViewModel中的协程管理
- 避免在UI线程进行耗时操作
- 使用`withContext`切换到合适的线程

## 最佳实践

### Hilt依赖注入

- 使用`@HiltAndroidApp`标记Application类
- 使用`@HiltViewModel`标记ViewModel
- 使用`@Inject`进行依赖注入

```kotlin
@HiltViewModel
class DemoViewModel @Inject constructor(
    application: Application,
    private val repository: DemoRepository
) : BaseViewModel(application) {
    // ...
}
```

### Compose UI

- 使用单一职责原则，每个Composable函数只负责一个UI部分
- 避免在Composable函数中进行耗时操作
- 使用`remember`和`mutableStateOf`管理状态

### 工具类

- 使用`Utils`类进行通用工具方法管理
- 使用`LogUtils`进行日志管理
- 工具类方法应该是静态的或扩展函数

### 日志规范

- 使用统一的日志标签：`TAG = "wxx"`
- 在`initLog()`方法中配置日志
- 只在Debug模式下输出详细日志

```kotlin
private fun initLog() {
    Utils.init(instance)
    LogUtils.getConfig()
        .setLogSwitch(isDebug())
        .setGlobalTag(TAG)
        .setBorderSwitch(true)
}
```

## 命名规范

### 文件命名

- 类文件：与类名相同，使用大驼峰命名法
- 示例：`DemoViewModel.kt`、`HomeScreen.kt`

### 资源命名

- 布局文件：`activity_`或`fragment_`前缀
- 字符串资源：使用小写字母和下划线
- 示例：`activity_main.xml`、`string_app_name`

### 组件命名

- Activity：`MainActivity`
- Fragment：`HomeFragment`
- ViewModel：`HomeViewModel`
- Composable：`HomeScreen`
- Repository：`DemoRepository`

## 代码结构

### ViewModel结构

1. 继承`BaseViewModel`
2. 使用`@HiltViewModel`注解
3. 定义UI状态和数据状态
4. 使用`viewModelScope`处理协程
5. 提供公共方法供UI调用

### Compose页面结构

1. 定义页面Composable函数
2. 使用`ViewModel`获取数据
3. 处理UI状态（Loading、Success、Error）
4. 使用Material 3组件
5. 实现响应式布局

### Repository结构

1. 实现数据获取逻辑
2. 封装本地和远程数据源
3. 提供简洁的API接口
4. 处理数据转换

## 测试规范

### 单元测试

- 测试类命名：`[被测试类]Test`
- 测试函数命名：`test[功能描述]`
- 使用MockK进行模拟
- 测试覆盖率目标：80%以上

### 集成测试

- 使用`androidTest`目录
- 测试UI交互流程
- 测试数据层和UI层的集成

## 性能优化

### 内存优化

- 避免内存泄漏
- 使用`lateinit`和`lazy`初始化
- 及时取消协程和监听器

### UI优化

- 使用`remember`缓存计算结果
- 避免在Composable中创建新对象
- 使用`LazyColumn`和`LazyRow`处理长列表

### 网络优化

- 使用Retrofit进行网络请求
- 实现缓存策略
- 避免重复请求

## 安全性

### 数据安全

- 避免硬编码敏感信息
- 使用`BuildConfig`管理配置
- 加密存储敏感数据

### 网络安全

- 使用HTTPS
- 验证服务器证书
- 避免明文传输敏感信息

## 版本控制

### Git规范

- 分支命名：`feature/[功能名]`、`bugfix/[问题描述]`
- 提交信息：简洁明了，描述变更内容
- 代码审查：所有变更都需要通过代码审查

### 版本号规范

- 使用语义化版本：`major.minor.patch`
- 示例：`1.0.0`、`1.1.0`、`1.0.1`

## 文档规范

### 代码文档

- 使用KDoc注释
- 注释类、函数、参数的作用
- 提供使用示例

### 项目文档

- 维护README.md
- 记录架构设计决策
- 文档与代码保持同步

## 工具和插件

### 推荐工具

- Android Studio
- Kotlin Plugin
- Hilt Plugin
- Compose Plugin

### 代码检查

- 使用Lint进行代码检查
- 配置.gitignore文件
- 使用代码格式化工具

## 总结

遵循以上编码规范可以：

1. 提高代码质量和可维护性
2. 减少bug和错误
3. 提高团队协作效率
4. 保证项目的一致性和可读性

所有开发人员都应该严格遵循这些规范，确保项目的代码质量和架构一致性。