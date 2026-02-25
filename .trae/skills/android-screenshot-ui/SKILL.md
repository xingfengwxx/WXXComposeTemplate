---
name: android-screenshot-ui
description: 通过截图实现Android UI功能，支持基于截图生成Compose代码、分析UI组件、提取颜色方案和布局结构。适用于使用Kotlin、Jetpack Compose、Material 3、Hilt等技术栈的Android项目。
---

# Android Screenshot UI 技能

## 概述

本技能提供了一套完整的工作流程，用于通过截图实现Android UI功能。它能够：

1. 分析截图中的UI组件和布局结构
2. 提取颜色方案和排版样式
3. 生成符合项目技术栈的Jetpack Compose代码
4. 提供组件复用和主题集成建议

## 技术栈兼容性

本技能专为以下技术栈设计：

- Kotlin
- Jetpack Compose
- Material 3
- Hilt
- AndroidX 组件
- PictureSelector (用于图片处理)
- Coil (用于图片加载)

## 工作流程

### 1. 截图分析

当用户提供UI截图时，执行以下步骤：

1. **组件识别**：识别截图中的UI组件类型（按钮、文本框、卡片、列表等）
2. **布局分析**：分析组件的排列方式和空间关系
3. **样式提取**：提取颜色、字体、间距等视觉属性
4. **层次结构**：识别组件的嵌套关系和层级

### 2. 代码生成

基于分析结果，生成以下代码：

1. **Compose 屏幕**：生成完整的屏幕组件代码
2. **可复用组件**：将重复的UI元素提取为可复用组件
3. **主题集成**：使用项目现有的主题系统
4. **状态管理**：根据UI交互需求提供状态管理建议

### 3. 集成建议

1. **项目结构**：建议将生成的代码放入合适的目录结构
2. **依赖注入**：提供Hilt集成示例
3. **导航集成**：与项目的导航系统集成
4. **测试建议**：提供单元测试和UI测试示例

## 使用示例

### 基本用法

用户输入：
```
请基于此截图创建一个登录界面
[截图]
```

处理流程：
1. 分析截图中的登录表单、按钮和输入字段
2. 提取颜色方案和排版样式
3. 生成符合项目技术栈的Compose代码
4. 提供集成建议和使用说明

### 高级用法

用户输入：
```
请基于此截图创建一个商品详情页面，并确保与现有主题集成
[截图]
```

处理流程：
1. 分析截图中的商品信息、图片、价格和操作按钮
2. 提取颜色方案并与项目主题对比
3. 生成Compose代码，包括可复用的商品卡片组件
4. 提供与现有导航和状态管理系统的集成建议

## 代码生成规范

### 文件结构

生成的代码应遵循以下文件结构：

```
ui/
├── [feature]/
│   ├── [Feature]Screen.kt          # 主屏幕组件
│   ├── [Feature]ViewModel.kt       # 状态管理
│   └── components/
│       ├── [Component].kt          # 可复用组件
│       └── ...
```

### 代码风格

1. **命名规范**：使用PascalCase命名组件，camelCase命名属性和方法
2. **注释**：为关键组件和复杂逻辑添加注释
3. **代码组织**：使用合理的代码折叠和分组
4. **空安全**：遵循Kotlin的空安全最佳实践
5. **组合原则**：优先使用组合而非继承

### 主题集成

```kotlin
// 使用项目现有的主题
@Composable
fun ThemedComponent() {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    
    // 使用主题颜色
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.primary
        )
    ) {
        Text(
            text = "Button",
            style = typography.bodyLarge
        )
    }
}
```

### 状态管理

根据项目需求，提供以下状态管理选项：

1. **简单状态**：使用`remember`和`mutableStateOf`
2. **ViewModel**：使用Jetpack ViewModel和StateFlow
3. **MVI模式**：与项目现有的MVI架构集成

## 工具集成

### PictureSelector 集成

项目已集成PictureSelector库，可用于图片选择和处理：

```kotlin
// 使用PictureSelector选择图片
PictureSelector.create(context)
    .openGallery()
    .forResult(object : OnResultCallbackListener<LocalMedia> {
        override fun onResult(result: ArrayList<LocalMedia>) {
            // 处理选择的图片
        }
        
        override fun onCancel() {
            // 处理取消操作
        }
    })
```

### Coil 集成

使用Coil加载和处理图片：

```kotlin
// 使用Coil加载图片
AsyncImage(
    model = imageUrl,
    contentDescription = "Image",
    modifier = Modifier.size(128.dp)
)
```

## 最佳实践

1. **组件复用**：识别并提取可复用的UI组件
2. **响应式设计**：确保生成的UI在不同屏幕尺寸上正常显示
3. **性能优化**：避免不必要的重组和计算
4. **可访问性**：确保生成的UI符合可访问性标准
5. **测试覆盖**：为生成的组件提供测试建议

## 故障排除

### 常见问题

1. **组件识别不准确**：确保截图清晰，组件边界明确
2. **颜色提取偏差**：可能需要手动调整生成的颜色值
3. **布局适配问题**：检查生成的布局是否在不同屏幕尺寸上正常显示
4. **主题集成问题**：确保生成的代码使用项目现有的主题系统

### 解决方案

1. **提供多个截图**：从不同角度和尺寸提供截图以获得更准确的分析
2. **指定设计规范**：如果有设计规范文档，提供给技能以获得更一致的结果
3. **手动调整**：生成代码后，根据实际需求进行手动调整
4. **测试验证**：在不同设备和配置下测试生成的UI

## 输入输出示例

### 输入示例

```
请基于此截图创建一个设置页面，包含主题切换、语言选择和通知设置选项
[设置页面截图]
```

### 输出示例

```kotlin
// SettingsScreen.kt
@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("设置") }
            )
        }
    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // 主题设置
            SettingItem(
                title = "主题",
                subtitle = uiState.themeName,
                onClick = { /* 打开主题选择 */ }
            )
            
            // 语言设置
            SettingItem(
                title = "语言",
                subtitle = uiState.language,
                onClick = { /* 打开语言选择 */ }
            )
            
            // 通知设置
            SettingItem(
                title = "通知",
                subtitle = if (uiState.notificationsEnabled) "已启用" else "已禁用",
                onClick = { /* 打开通知设置 */ }
            )
        }
    }
}

@Composable
fun SettingItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
```

## 总结

本技能提供了一套完整的工作流程，用于通过截图实现Android UI功能。它能够分析截图、提取设计元素并生成符合项目技术栈的Compose代码，大大加速UI开发过程。

使用本技能时，请确保：

1. 提供清晰、高质量的UI截图
2. 说明具体的功能需求和交互逻辑
3. 指出任何特殊的设计要求或约束
4. 确认生成的代码与项目的架构和主题系统集成

通过结合截图分析和代码生成，本技能能够帮助开发者快速实现UI功能，同时保持代码质量和项目一致性。