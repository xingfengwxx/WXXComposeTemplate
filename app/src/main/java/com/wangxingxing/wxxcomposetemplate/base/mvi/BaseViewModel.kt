package com.wangxingxing.wxxcomposetemplate.base.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Event: UiEvent, Effect: UiEffect>(
    initialState: State
) : ViewModel() {

    protected val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<Event>(extraBufferCapacity = 64)
    val uiEvent: SharedFlow<Event> = _uiEvent.asSharedFlow()

    private val _uiEffect = MutableSharedFlow<Effect>(replay = 0, extraBufferCapacity = 64)
    val uiEffect: SharedFlow<Effect> = _uiEffect.asSharedFlow()

    fun dispatch(event: Event) {
        viewModelScope.launch { _uiEvent.emit(event) }
        onEvent(event)
    }

    protected abstract fun onEvent(event: Event)

    protected fun setState(reducer: (State) -> State) {
        _uiState.value = reducer(_uiState.value)
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch { _uiEffect.emit(effect) }
    }
}

