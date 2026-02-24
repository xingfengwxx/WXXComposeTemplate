package com.wangxingxing.wxxcomposetemplate.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wangxingxing.wxxcomposetemplate.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 主题状态 ViewModel（MVI：State + Event），负责暴露主题状态并处理用户事件
 */
@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    data class UiState(
        val mode: ThemeMode = ThemeMode.SYSTEM,
        val isDarkTheme: Boolean = false,
        val dynamicColor: Boolean = true,
        val themeColorIndex: Int = 0
    )

    sealed interface UiEvent {
        data class ChangeMode(val mode: ThemeMode) : UiEvent
        data class ToggleDynamicColor(val enabled: Boolean) : UiEvent
        data class ChangeThemeColor(val index: Int) : UiEvent
    }

    private val _internalState = MutableStateFlow(UiState())

    // 从 DataStore 同步实际值
    val state: StateFlow<UiState> = combine(
        settingsRepository.themeMode,
        settingsRepository.dynamicColorEnabled,
        settingsRepository.themeColor
    ) { modeInt, dynamic, colorIndex ->
        val mode = when (modeInt) {
            1 -> ThemeMode.LIGHT
            2 -> ThemeMode.DARK
            else -> ThemeMode.SYSTEM
        }
        val isDark = resolveIsDark(mode)
        UiState(
            mode = mode,
            isDarkTheme = isDark,
            dynamicColor = dynamic,
            themeColorIndex = colorIndex
        )
    }.stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Eagerly, _internalState.value)

    fun dispatch(event: UiEvent) {
        when (event) {
            is UiEvent.ChangeMode -> changeMode(event.mode)
            is UiEvent.ToggleDynamicColor -> toggleDynamic(event.enabled)
            is UiEvent.ChangeThemeColor -> changeColor(event.index)
        }
    }

    private fun changeMode(mode: ThemeMode) {
        viewModelScope.launch {
            val modeInt = when (mode) {
                ThemeMode.SYSTEM -> 0
                ThemeMode.LIGHT -> 1
                ThemeMode.DARK -> 2
            }
            settingsRepository.saveThemeMode(modeInt)
            _internalState.update { it.copy(mode = mode, isDarkTheme = resolveIsDark(mode)) }
        }
    }

    private fun toggleDynamic(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.saveDynamicColorEnabled(enabled)
            _internalState.update { it.copy(dynamicColor = enabled) }
        }
    }

    private fun changeColor(index: Int) {
        viewModelScope.launch {
            settingsRepository.saveThemeColor(index)
            _internalState.update { it.copy(themeColorIndex = index) }
        }
    }
}

