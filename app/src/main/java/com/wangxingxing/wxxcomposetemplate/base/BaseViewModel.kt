package com.wangxingxing.wxxcomposetemplate.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : ViewModel 基类，提供通用状态管理
 */
abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * UI 状态
     */
    protected abstract val uiState: MutableStateFlow<UiState>

    /**
     * 获取 UI 状态（只读）
     */
    fun getUiState(): StateFlow<UiState> = uiState.asStateFlow()

    /**
     * 更新 UI 状态
     */
    protected fun updateUiState(state: UiState) {
        viewModelScope.launch {
            uiState.value = state
        }
    }

    /**
     * 执行协程任务
     */
    protected fun launch(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    /**
     * 错误处理
     */
    protected open fun handleError(e: Exception) {
        val errorMessage = e.message ?: getApplication<Application>().getString(
            com.wangxingxing.wxxcomposetemplate.R.string.error_unknown
        )
        updateUiState(UiState.Error(errorMessage))
    }
}

/**
 * UI 状态密封类
 */
sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success<T>(val data: T) : UiState()
    data class Error(val message: String) : UiState()
}
