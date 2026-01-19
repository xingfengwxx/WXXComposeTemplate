package com.wangxingxing.wxxcomposetemplate.ui.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult
import com.wangxingxing.wxxcomposetemplate.data.remote.api.User
import com.wangxingxing.wxxcomposetemplate.data.repository.LocalUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiState {
    data object Idle : UiState()
    data object Loading : UiState()
    data class Error(val message: String) : UiState()
}

@HiltViewModel
class LocalUserViewModel @Inject constructor(
    private val repository: LocalUserRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    val selectedUser = MutableStateFlow<User?>(null)
    val dialogState = MutableStateFlow(false)

    init {
        loadUsers()
    }

    fun loadUsers() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            when (val result = repository.getUsers()) {
                is ApiResult.Success -> {
                    _users.value = result.data
                    _state.value = UiState.Idle
                }
                is ApiResult.Error -> {
                    _state.value = UiState.Error(result.message)
                }
                else -> {}
            }
        }
    }

    fun onUserClick(user: User) {
        _state.value = UiState.Loading
        viewModelScope.launch {
            when (val result = repository.getUser(user.id)) {
                is ApiResult.Success -> {
                    selectedUser.value = result.data
                    dialogState.value = true
                    _state.value = UiState.Idle
                }
                is ApiResult.Error -> {
                    _state.value = UiState.Error(result.message)
                }
                else -> {}
            }
        }
    }

    fun dismissDialog() {
        dialogState.value = false
        selectedUser.value = null
    }

    fun createNewUser(username: String, email: String) {
        if (username.isBlank() || email.isBlank()) {
            _state.value = UiState.Error("用户名或邮箱不能为空")
            return
        }
        _state.value = UiState.Loading
        viewModelScope.launch {
            when (val result = repository.createUser(username, email)) {
                is ApiResult.Success -> {
                    loadUsers()
                }
                is ApiResult.Error -> {
                    _state.value = UiState.Error(result.message)
                }
                else -> {}
            }
        }
    }

    fun retry() {
        loadUsers()
    }
}
