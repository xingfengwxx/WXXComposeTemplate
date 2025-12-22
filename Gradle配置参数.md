# Gradle配置参数

```groovy
org.gradle.jvmargs=-Xmx12g -Xms2g -XX:MaxMetaspaceSize=4g -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:+UseStringDeduplication
```

## 一、完整参数原文

```
org.gradle.jvmargs=
-Xmx12g
-Xms2g
-XX:MaxMetaspaceSize=2g
-XX:+HeapDumpOnOutOfMemoryError
-Dfile.encoding=UTF-8
-XX:+UseG1GC
-XX:MaxGCPauseMillis=200
-XX:+UseStringDeduplication
```

------

## 二、参数逐条解释（重点）

------

### 1️⃣ `-Xmx12g`

**最大堆内存：12GB**

- Gradle JVM 运行时最多可使用 **12GB 内存**
- 用于：
  - Kotlin 编译
  - KAPT / KSP
  - Dex / R8
  - Compose 编译

✅ **适合场景**

- 大型 Android 项目
- 多 module / Compose / KSP / AGP 8+

⚠️ **注意**

- 不要超过 **物理内存的 50–60%**
- 32GB 内存机器：12g 非常合理
- 16GB 内存机器：偏大，建议 6–8g

------

### 2️⃣ `-Xms2g`

**初始堆内存：2GB**

- JVM 启动时直接申请 2GB
- 减少频繁扩容 → **构建更稳定**

✅ 优点

- 减少 GC 抖动
- Gradle Daemon 启动更快

📌 建议

- 通常设为 `Xmx` 的 **1/4 ~ 1/3**
- 你这个配置是合理的

------

### 3️⃣ `-XX:MaxMetaspaceSize=2g`

**限制 Metaspace 最大为 2GB**

Metaspace 用来存放：

- 类信息
- Kotlin / Java 编译生成的大量 class
- 动态代理类

✅ 适合 Android 场景

- 多 module + Compose 项目
- 防止无限制膨胀拖垮系统

⚠️ 风险

- 如果模块特别多（100+），2g 可能偏小
- 报错：`OutOfMemoryError: Metaspace`

📌 建议

```
常规项目：1g–2g
超大项目：2g–4g
```

------

### 4️⃣ `-XX:+HeapDumpOnOutOfMemoryError`

**OOM 时生成堆内存快照（.hprof）**

- 当 Gradle OOM：
  - 自动生成 heap dump
  - 用于分析内存泄漏

✅ 非常推荐

- 排查：
  - 编译期 OOM
  - KAPT / KSP 内存问题

⚠️ 注意

- dump 文件可能 **几 GB**
- 确保磁盘空间充足

------

### 5️⃣ `-Dfile.encoding=UTF-8`

**强制使用 UTF-8 编码**

解决：

- 中文路径
- 资源文件乱码
- Windows / macOS 编码不一致问题

✅ 必加参数
 几乎 **所有 Gradle 项目都应该加**

------

### 6️⃣ `-XX:+UseG1GC`

**使用 G1 垃圾回收器**

G1 特点：

- 面向 **大堆内存（>4G）**
- 低停顿
- 可预测性强

✅ 看齐官方推荐

- Android Gradle Plugin 默认推荐
- 大项目必选

❌ 不建议再用 CMS / Parallel GC

------

### 7️⃣ `-XX:MaxGCPauseMillis=200`

**期望最大 GC 停顿：200ms**

- 告诉 G1：

  > 尽量把 GC 停顿控制在 200ms 内

⚠️ 注意

- **不是强制值**
- JVM 会“尽力而为”

📌 建议范围

```
100–300ms（Gradle 构建很合适）
```

------

### 8️⃣ `-XX:+UseStringDeduplication`

**开启字符串去重（G1 专属）**

- 自动识别内容相同的 `String`
- 多个副本 → 共享一个 char[] / byte[]

✅ 对 Android 项目很有用

- Kotlin
- 注解处理器
- R8 / ProGuard

📈 效果

- 降低内存占用 5–15%
- 减少 OOM 风险

⚠️ 代价

- 极小的 CPU 开销（可忽略）

------

## 三、整体评价（你的配置如何）

### ✅ 优点

- 非常 **成熟 & 健壮**
- 明显针对：
  - Compose
  - Kotlin
  - 多 Module
- 适合 **中大型 Android 项目**

### ⚠️ 潜在风险

| 风险点       | 说明                            |
| ------------ | ------------------------------- |
| Xmx 12g      | 如果你是 16G 内存电脑，可能吃紧 |
| Metaspace 2g | 超多模块项目可能不够            |