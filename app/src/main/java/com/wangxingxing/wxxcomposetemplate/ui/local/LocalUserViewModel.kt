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

    // 分页元数据
    private var currentPage = 1
    private val pageSize = 10
    private var totalPages = 0
    private var totalItems = 0

    private val _canLoadMore = MutableStateFlow(false)
    val canLoadMore: StateFlow<Boolean> = _canLoadMore.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    private val _totalCount = MutableStateFlow(0)
    val totalCount: StateFlow<Int> = _totalCount.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _state.value = UiState.Loading
        currentPage = 1
        totalPages = 0
        totalItems = 0
        _canLoadMore.value = false
        _users.value = emptyList()
        loadUsers(page = 1, append = false)
    }

    private fun loadUsers(page: Int, append: Boolean) {
        viewModelScope.launch {
            if (append) _isLoadingMore.value = true
            when (val result = repository.getUsers(page, pageSize)) {
                is ApiResult.Success -> {
                    val pageData = result.data
                    totalItems = pageData.total
                    totalPages = pageData.pages
                    currentPage = pageData.current
                    _totalCount.value = totalItems

                    val newList = if (append) _users.value + pageData.records else pageData.records
                    _users.value = newList

                    _canLoadMore.value = currentPage < totalPages

                    if (!append) _state.value = UiState.Idle
                }
                is ApiResult.Error -> {
                    if (append) {
                        // 追加失败也维持已有数据，仅提示错误状态
                        _isLoadingMore.value = false
                    }
                    _state.value = UiState.Error(result.message)
                }
                else -> {}
            }
            if (append) _isLoadingMore.value = false
        }
    }

    fun loadMore() {
        if (_isLoadingMore.value || !_canLoadMore.value) return
        val next = currentPage + 1
        if (next <= totalPages || totalPages == 0) {
            loadUsers(page = next, append = true)
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
                    // 创建成功后刷新第一页
                    refresh()
                }
                is ApiResult.Error -> {
                    _state.value = UiState.Error(result.message)
                }
                else -> {}
            }
        }
    }

    fun retry() {
        refresh()
    }
}
