package com.wangxingxing.wxxcomposetemplate.ui.login

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.wangxingxing.wxxcomposetemplate.base.mvi.BaseViewModel
import com.wangxingxing.wxxcomposetemplate.base.mvi.UiEffect
import com.wangxingxing.wxxcomposetemplate.base.mvi.UiEvent
import com.wangxingxing.wxxcomposetemplate.base.mvi.ResultState
import com.wangxingxing.wxxcomposetemplate.data.local.UserPreferences
import com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult
import com.wangxingxing.wxxcomposetemplate.data.remote.api.model.UserInfo
import com.wangxingxing.wxxcomposetemplate.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 登录页面 ViewModel（MVI）
 */

// MVI Types for Login
sealed interface LoginEvent : UiEvent {
    data class Submit(val username: String, val password: String) : LoginEvent
    data object Logout : LoginEvent
    data object LoadLocalUser : LoginEvent
    data object ClearError : LoginEvent
}

sealed interface LoginEffect : UiEffect {
    data object NavigateAfterLogin : LoginEffect
}

data class LoginState(
    val userInfo: UserInfo? = null,
    val loginResult: ResultState<UserInfo> = ResultState(),
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val repository: LoginRepository,
    private val userPreferences: UserPreferences
) : BaseViewModel<LoginState, LoginEvent, LoginEffect>(initialState = LoginState()) {

    init {
        // 初始化时读取本地用户信息
        dispatch(LoginEvent.LoadLocalUser)
    }

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Submit -> login(event.username, event.password)
            LoginEvent.Logout -> logout()
            LoginEvent.LoadLocalUser -> loadLocalUserInfo()
            LoginEvent.ClearError -> setState { it.copy(error = null) }
        }
    }

    private fun loadLocalUserInfo() {
        viewModelScope.launch {
            val userInfo = userPreferences.getUserInfo()
            setState {
                it.copy(
                    userInfo = userInfo,
                    loginResult = if (userInfo != null) ResultState.success(userInfo) else ResultState(),
                    error = null
                )
            }
        }
    }

    private fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            setState { it.copy(error = "用户名或密码不能为空") }
            return
        }
        viewModelScope.launch {
            setState { it.copy(isLoading = true, error = null) }
            val result = repository.login(username, password)
            when (result) {
                is ApiResult.Success -> {
                    val info = result.data
                    userPreferences.saveUserInfo(info)
                    setState {
                        it.copy(
                            userInfo = info,
                            isLoading = false,
                            loginResult = ResultState.success(info),
                            error = null
                        )
                    }
                    // 登录成功后的导航副作用
                    sendEffect(LoginEffect.NavigateAfterLogin)
                }
                is ApiResult.Error -> {
                    setState {
                        it.copy(
                            isLoading = false,
                            loginResult = ResultState.error(RuntimeException(result.message)),
                            error = result.message
                        )
                    }
                }
                is ApiResult.Loading -> {
                    setState { it.copy(isLoading = true, loginResult = ResultState.loading()) }
                }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            userPreferences.clearUserInfo()
            setState {
                it.copy(
                    userInfo = null,
                    loginResult = ResultState(),
                    username = "",
                    password = "",
                    isLoading = false,
                    error = null
                )
            }
        }
    }
}
