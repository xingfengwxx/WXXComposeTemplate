# Android Compose · Core Prompt

本项目使用 Kotlin + Jetpack Compose，架构为 MVVM。

- UI → ViewModel → UseCase → Repository
- UI 不包含业务逻辑
- 使用 Coroutines + StateFlow
- Material 3
- 禁止 LiveData / GlobalScope
