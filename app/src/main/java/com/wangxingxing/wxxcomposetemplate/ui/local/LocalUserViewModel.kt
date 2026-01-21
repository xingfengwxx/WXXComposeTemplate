package com.wangxingxing.wxxcomposetemplate.ui.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult
import com.wangxingxing.wxxcomposetemplate.data.remote.api.User
import com.wangxingxing.wxxcomposetemplate.data.repository.LocalUserRepository
import com.wangxingxing.wxxcomposetemplate.data.repository.LocalUsersPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
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

    private val _state = MutableStateFlow<UiState>(UiState.Idle)
    val state: StateFlow<UiState> = _state.asStateFlow()

    // 详情弹窗
    val selectedUser = MutableStateFlow<User?>(null)
    val dialogState = MutableStateFlow(false)

    private val pageSize = 10

    // 总数展示
    private val _totalCount = MutableStateFlow(0)
    val totalCount: StateFlow<Int> = _totalCount.asStateFlow()

    // 列表刷新触发器（递增即可触发 flatMapLatest 重建 Pager）
    private val refreshCounter = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingDataFlow = refreshCounter.flatMapLatest {
        Pager(
            config = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize,
                prefetchDistance = maxOf(1, pageSize / 2),
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                LocalUsersPagingSource(
                    repository = repository,
                    pageSize = pageSize,
                    onPageMeta = { meta ->
                        _totalCount.value = meta.total
                    }
                )
            }
        ).flow
    }.cachedIn(viewModelScope)

    fun refresh() {
        // 触发重建 Pager，驱动整表刷新
        refreshCounter.value = refreshCounter.value + 1
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
                    // 创建成功后刷新分页
                    _state.value = UiState.Idle
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
