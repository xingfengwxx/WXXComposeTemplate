package com.wangxingxing.wxxcomposetemplate.ui.login

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.wangxingxing.wxxcomposetemplate.base.BaseViewModel
import com.wangxingxing.wxxcomposetemplate.base.UiState
import com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult
import com.wangxingxing.wxxcomposetemplate.data.remote.api.UserInfo
import com.wangxingxing.wxxcomposetemplate.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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
    private val repository: LoginRepository
) : BaseViewModel(application) {

    override val uiState = MutableStateFlow<UiState>(UiState.Idle)

    val loginResult = MutableStateFlow<ApiResult<UserInfo>?>(null)

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
}

