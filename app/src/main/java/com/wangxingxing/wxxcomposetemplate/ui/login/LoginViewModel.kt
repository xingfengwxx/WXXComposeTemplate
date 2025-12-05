package com.wangxingxing.wxxcomposetemplate.ui.login

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.wangxingxing.wxxcomposetemplate.base.BaseViewModel
import com.wangxingxing.wxxcomposetemplate.base.UiState
import com.wangxingxing.wxxcomposetemplate.data.local.UserPreferences
import com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult
import com.wangxingxing.wxxcomposetemplate.data.remote.api.model.UserInfo
import com.wangxingxing.wxxcomposetemplate.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 登录页面 ViewModel
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val repository: LoginRepository,
    private val userPreferences: UserPreferences
) : BaseViewModel(application) {

    override val uiState = MutableStateFlow<UiState>(UiState.Idle)

    val loginResult = MutableStateFlow<ApiResult<UserInfo>?>(null)

    // 本地用户信息
    private val _localUserInfo = MutableStateFlow<UserInfo?>(null)
    val localUserInfo: StateFlow<UserInfo?> = _localUserInfo.asStateFlow()

    init {
        // 初始化时读取本地用户信息
        loadLocalUserInfo()
    }

    /**
     * 加载本地用户信息
     */
    private fun loadLocalUserInfo() {
        viewModelScope.launch {
            val userInfo = userPreferences.getUserInfo()
            _localUserInfo.value = userInfo
            if (userInfo != null) {
                loginResult.value = ApiResult.Success(userInfo)
            }
        }
    }

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     */
    fun login(username: String, password: String) {
        viewModelScope.launch {
            updateUiState(UiState.Loading)
            val result = repository.login(username, password)
            loginResult.value = result
            when (result) {
                is ApiResult.Success -> {
                    // 登录成功后保存用户信息到本地
                    userPreferences.saveUserInfo(result.data)
                    _localUserInfo.value = result.data
                    updateUiState(UiState.Success(result.data))
                }
                is ApiResult.Error -> {
                    updateUiState(UiState.Error(result.message))
                }
                is ApiResult.Loading -> {
                    updateUiState(UiState.Loading)
                }
            }
        }
    }

    /**
     * 退出登录
     */
    fun logout() {
        viewModelScope.launch {
            // 清除本地用户信息
            userPreferences.clearUserInfo()
            _localUserInfo.value = null
            loginResult.value = null
            updateUiState(UiState.Idle)
        }
    }
}

