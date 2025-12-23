# 🧭 Copilot Prompt Index  
> Android · Kotlin · Jetpack Compose

本文件是 **Copilot Prompt 的统一入口与使用指南**。  
在使用 GitHub Copilot Chat 或代码生成前，请先参考本索引。

---

## 🧠 全局规则（必须遵守）

- 项目总规则：`ai/core_prompt.md`
- 语言规则：`ai/language_prompt.md`（默认中文）
- 架构：MVVM
- UI：Jetpack Compose + Material 3
- 并发：Coroutines + StateFlow

在任何代码生成任务中，请**优先遵守以上规则**。

---

## 📦 Prompt 分类索引

### 🎨 UI / Compose

| 场景           | Prompt 文件                    |
| -------------- | ------------------------------ |
| 页面（Screen） | `prompts/compose_screen.md`    |
| 可复用组件     | `prompts/compose_component.md` |

---

### 🧠 状态与逻辑

| 场景         | Prompt 文件            |
| ------------ | ---------------------- |
| UIState 定义 | `prompts/ui_state.md`  |
| ViewModel    | `prompts/viewmodel.md` |
| UseCase      | `prompts/usecase.md`   |

---

### 🗄 数据层

| 场景          | Prompt 文件               |
| ------------- | ------------------------- |
| Repository    | `prompts/repository.md`   |
| Retrofit API  | `prompts/retrofit_api.md` |
| Mapper / 转换 | `prompts/mapper.md`       |

---

### 🧪 测试

| 场景            | Prompt 文件                 |
| --------------- | --------------------------- |
| ViewModel 单测  | `prompts/unit_test_vm.md`   |
| Repository 单测 | `prompts/unit_test_repo.md` |

---

### 🔧 重构

| 场景     | Prompt 文件                 |
| -------- | --------------------------- |
| 代码重构 | `prompts/refactor_rules.md` |

---

## 🚀 推荐使用方式

### 方式一：Copilot Chat（推荐）

在 Copilot Chat 中使用如下格式：

```text
参考 ai/core_prompt.md，
使用 prompts/viewmodel.md 生成 LoginViewModel
```

### 方式二：代码内触发

在文件或类顶部添加注释：

```text
// 参考 ai/prompt_index.md
// 使用 prompts/compose_screen.md
```