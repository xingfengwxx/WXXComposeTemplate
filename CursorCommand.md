# Cursor 命令文档

## 项目背景

我使用AndroidStudio创建了一个空的AndroidCompose项目，将Kotlin作为首选语言，项目基础架构使用Jetpack搭建，并且按照我上传附件的技术架构图对当前项目改造，生成Cursor命令

## 技术架构要求

- **语言：** Kotlin
- **UI框架：** Jetpack Compose
- **架构模式：** MVVM
- **依赖注入：** Hilt
- **网络请求：** Retrofit + OkHttp
- **本地数据库：** Room
- **状态管理：** StateFlow / Flow
- **协程：** Kotlin Coroutines

## 代码规范

- 所有类必须使用 KDoc 注释
- 遵循单一职责原则
- 使用 sealed class 表示 UI State
- 所有网络请求必须使用 suspend 函数
- 所有数据模型使用 data class
- 禁止硬编码，所有文字使用 strings.xml 管理

## 功能需求

### 1. 登录页面

创建一个登录页面，从标题2的位置点击进入，点击登录调用接口完成网络请求，登录接口如下：

**接口地址：** https://www.wanandroid.com/user/login

**方法：** POST

**参数：**
- username
- password

**API文档地址：** https://wanandroid.com/blog/show/2

**返回数据结构定义：**

```json
{
    "data": ...,
    "errorCode": 0,
    "errorMsg": ""
}
```

**接口返回的基础数据格式：**

```json
{
    "data": ...,
    "errorCode": 0,
    "errorMsg": ""
}
```

**错误码说明：**
- `errorCode = 0` 代表执行成功，不建议依赖任何非0的 errorCode
- `errorCode = -1001` 代表登录失效，需要重新登录

**要求：** 对当前项目做统一的返回数据格式处理

---

### 2. Room 数据库使用示例页面

创建一个room数据库的使用示例页面，添加到标题3的位置，页面中的功能如下：

#### 功能需求：

1. **如果本地没有项目分类数据则调用接口：**
   - **接口地址：** https://www.wanandroid.com/project/tree/json
   - **方法：** GET
   - **参数：** 无

   **接口返回数据示例：**

   ```json
   {
     "data": [
       {
         "articleList": [],
         "author": "",
         "children": [],
         "courseId": 13,
         "cover": "",
         "desc": "",
         "id": 294,
         "lisense": "",
         "lisenseLink": "",
         "name": "完整项目",
         "order": 145000,
         "parentChapterId": 293,
         "type": 0,
         "userControlSetTop": false,
         "visible": 0
       }
     ],
     "errorCode": 0,
     "errorMsg": ""
   }
   ```

2. **调用接口成功后把获取到的数据使用room存储到本地**

3. **下次进入这个页面时获取本地数据，如果有则将数据显示在屏幕上**

4. **页面中所有用到的文字使用统一使用strings.xml管理起来，并要有中英文版本的**

5. **下拉刷新使用Compose的下拉刷新动画，在下拉刷新时，不管本地是否有数据都请求网络，下拉刷新动画风格使用使用 PullToRefreshBox composable，它默认就集成了官方的 Material 3 风格动画。给下拉刷新动画添加一个最小显示时间，以防接口返回太快导致加载动画一闪而过**

---


