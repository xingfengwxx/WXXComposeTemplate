# Android 团队 Copilot 使用规范

## 一、使用原则
- Copilot 是辅助，不是决策者
- 所有生成代码必须 Review

## 二、统一规范
- 默认中文沟通
- Kotlin + Compose + MVVM
- 禁止 LiveData / GlobalScope

## 三、代码评审要求
- 是否符合 core_prompt.md
- 是否存在过度设计
- 是否存在隐藏 Bug

## 四、禁止事项
- 直接合并未 Review 的 AI 代码
- 生成整模块而不拆分

