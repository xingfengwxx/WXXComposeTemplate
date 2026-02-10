package com.wangxingxing.wxxcomposetemplate.base.mvi

import androidx.compose.runtime.Immutable

interface UiEvent
interface UiEffect

@Immutable
data class ResultState<out T>(
    val data: T? = null,
    val error: Throwable? = null,
    val loading: Boolean = false
) {
    companion object {
        fun <T> loading(): ResultState<T> = ResultState(loading = true)
        fun <T> success(data: T): ResultState<T> = ResultState(data = data)
        fun <T> error(e: Throwable): ResultState<T> = ResultState(error = e)
    }
}

@Immutable
data class PagingState<T>(
    val items: List<T> = emptyList(),
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val endReached: Boolean = false,
    val error: Throwable? = null
)

