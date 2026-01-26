---

```md
# GitHub Copilot Instructions  
## Database（资料库）模块开发规范（最终版）

> 本文档用于约束 GitHub Copilot 在 **资料库（Database）模块** 中生成代码的行为，  
> 所有生成内容必须严格遵守以下规则。

---

## 一、架构与实现规范

1. 严格遵循当前项目已有架构  
   - 所有功能必须基于项目既有分层实现  
   - 开发前必须参考项目根目录 `README.md`  
   - ❌ 禁止引入新的架构模式或破坏现有结构  

2. 功能实现参考旧版代码  
   - `PersonDataFootballFragment`  
   - `PersonDataBasketballFragment`  

---

## 二、MVI 架构规范（强制）

```

Action → ViewModel → reduce → UiState → UI

```

- ViewModel 必须继承 `BaseViewModel`
- UiState 必须继承 `BaseUiState`
- Action 必须继承 `BaseViewModelAction`
- 新增代码 **100% 符合 MVI 架构**

---

## 三、资料库模块目录规范

### 主目录（强制）

```

composeApp/src/commonMain/kotlin/com/meishi/sports/database

```

用于存放：
- Screen
- Page
- Route
- ViewModel
- UiState / Action
- 业务逻辑

### 实体类目录（强制）

```

composeApp/src/commonMain/kotlin/com/meishi/sports/database/bean

````

---

## 四、实体类序列化规范（强制）

1. 所有实体类必须添加 `@Serializable`
2. 必须使用 `kotlinx.serialization.Serializable`
3. ❌ 禁止使用 `java.io.Serializable`
4. ❌ 禁止使用 `@Parcelize` 替代

### 正确示例

```kotlin
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseItemBean(
    val id: String,
    val title: String,
    val coverUrl: String
)
````

---

## 五、主题与颜色规范（核心强制）

### 5.1 Color.kt 统一入口

* 所有颜色值 **只能定义在 `Color.kt`**
* ❌ 禁止在 UI / Theme / ViewModel 中硬编码颜色

### 5.2 颜色值定义语法（唯一允许）

```kotlin
val searchBarBorderLight = Color(0x4D0B0701)
val searchBarBorderDark  = Color(0x66FFFFFF)
```

❌ 禁止：

* `Color(255, 255, 255)`
* `Color.parseColor("#FFFFFF")`
* `Color(0xFF000000.toInt())`

---

### 5.3 Theme 映射规范

```kotlin
// 接口
val searchBarBorder: Color

// 浅色主题
override val searchBarBorder: Color
    get() = searchBarBorderLight

// 深色主题
override val searchBarBorder: Color
    get() = searchBarBorderDark
```

---

### 5.4 颜色使用规范

```kotlin
Modifier.border(
    width = 1.dp,
    color = AppColors.current.searchBarBorder
)
```

❌ 禁止使用 `MaterialTheme.colorScheme`
❌ 禁止使用 `Color.White / Color.Black`

---

## 六、页面背景颜色规范（强制）

页面默认背景颜色 **统一使用**：

```kotlin
AppColors.current.scaffoldBg
```

适用于：

* Screen / Page 根容器
* Scaffold.containerColor
* Box / Column / LazyColumn 外层

---

## 七、UI 与 Compose 规范

1. UI 样式需参考设计稿 / 截图
2. 公共 UI 组件统一放置在：

```
shared-ui/src/commonMain/kotlin/com/tw/shared_ui/component/database
```

3. 所有 Compose 组件 **必须提供 Preview**

---

## 八、Compose 预览规范（最终唯一标准）

### 必须同时提供 Light / Dark Preview

### Preview 外层必须使用 `Box + wrapContentSize()`

```kotlin
@Preview(name = "Light", showBackground = true)
@Composable
fun DatabasePreview_Light() {
    AppTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(AppColors.current.scaffoldBg)
        ) {
            DatabasePage()
        }
    }
}

@Preview(name = "Dark", showBackground = true)
@Composable
fun DatabasePreview_Dark() {
    AppTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(AppColors.current.scaffoldBg)
        ) {
            DatabasePage()
        }
    }
}
```

❌ 禁止：

* `fillMaxSize()`
* 单 Preview
* `Color.White / Color.Black`
* `MaterialTheme` 背景色

---

## 九、资源与图片规范

### 图片目录

```
shared-ui/src/commonMain/composeResources/drawable
```

### 命名规范

* 图标：`database_ic_xxx`
* 背景：`database_bg_xxx`
* 浅色资源：`*_light`

### 格式优先级

```
svg > webp > png
```

---

## 十、主题态本地图片使用规范

```kotlin
Image(
    painter = painterResource(
        if (LocalDarkTheme.current)
            Res.drawable.xxx
        else
            Res.drawable.xxx_light
    ),
    contentDescription = null
)
```

---

## 十一、UI 字符串与多语言规范

```kotlin
Text(text = stringResource(Res.string.home_title))
```

必须同步维护：

```
values/strings.xml
values-en/strings.xml
values-zh-rTW/strings.xml
```

---

## 十二、日志规范

```kotlin
"进入了${title}页面".wxxLog()
```

❌ 禁止使用 `Log.d / println`

---

## 十三、图标使用规范

* ❌ 禁止使用 `SBIcons`
* ❌ 禁止使用 `Icons.Default`
* ✅ 仅允许使用：

```
shared-ui/src/commonMain/kotlin/com/tw/shared_ui/design/icons
```

---

## 十四、图片加载规范

* 图片加载库统一使用 **Coil**
* 网络图片必须设置：

    * placeholder
    * error

---

## 十五、文件头注释规范（强制）

```kotlin
/**
 * author : 王星星
 * date : yyyy/M/d HH:mm
 * email : 1099420259@qq.com
 * description :
 */
```

---

## 十六、平台实现说明

* ✅ Android / Compose Multiplatform
* ❌ 忽略 iOS 实现

---

## 十七、Copilot MCP 自动调度规则（强制）

Copilot 必须根据任务自动选择 MCP：

* 读取项目代码 → `filesystem`
* 复杂 MVI / 状态拆解 → `sequential-thinking`
* 官方 API / Compose / Kotlin → `context7`
* Figma 设计稿 → `figma`
* GitHub 仓库 / Issue / PR → `github`

多 MCP 调用顺序：

```
filesystem → sequential-thinking → context7 → figma → github
```

❌ 禁止在可使用 MCP 时凭空生成代码

---

## 十八、Copilot 最终强制行为总结

Copilot **必须**：

* 使用 MVI 架构
* 实体类全部添加 `@Serializable`
* 颜色统一定义在 Color.kt
* 页面与 Preview 背景统一 `AppColors.current.scaffoldBg`
* 提供 Light / Dark Preview
* 使用 strings.xml 多语言
* 使用 Coil 加载图片
* 禁止 SBIcons / 系统 Icons
* 禁止颜色硬编码
* 禁止绕过 MCP

---

## 📄 作者与版权声明

作者：王星星
邮箱：[1099420259@qq.com](mailto:1099420259@qq.com)
版权所有 © 王星星

```

