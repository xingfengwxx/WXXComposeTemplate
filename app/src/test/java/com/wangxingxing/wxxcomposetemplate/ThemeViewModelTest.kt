package com.wangxingxing.wxxcomposetemplate

import app.cash.turbine.test
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import com.wangxingxing.wxxcomposetemplate.data.repository.SettingsRepository
import com.wangxingxing.wxxcomposetemplate.ui.theme.ThemeMode
import com.wangxingxing.wxxcomposetemplate.ui.theme.ThemeViewModel
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ThemeViewModelTest {

    private fun createDataStore(): DataStore<Preferences> {
        val tmpFile = File.createTempFile("datastore", "preferences").apply { deleteOnExit() }
        return PreferenceDataStoreFactory.create(
            produceFile = { tmpFile }
        )
    }

    @Test
    fun theme_state_updates_on_mode_and_dynamic_color_changes() = runTest {
        val repo = SettingsRepository(createDataStore())
        val vm = ThemeViewModel(repo)

        vm.state.test {
            // 初始值：SYSTEM 模式，dynamicColor = true，colorIndex = 0
            val initial = awaitItem()
            assertEquals(ThemeMode.SYSTEM, initial.mode)
            assertEquals(true, initial.dynamicColor)
            assertEquals(0, initial.themeColorIndex)

            // 切换为 LIGHT
            vm.dispatch(ThemeViewModel.UiEvent.ChangeMode(ThemeMode.LIGHT))
            val lightState = awaitItem()
            assertEquals(ThemeMode.LIGHT, lightState.mode)
            assertEquals(false, lightState.isDarkTheme)

            // 切换为 DARK
            vm.dispatch(ThemeViewModel.UiEvent.ChangeMode(ThemeMode.DARK))
            val darkState = awaitItem()
            assertEquals(ThemeMode.DARK, darkState.mode)
            assertEquals(true, darkState.isDarkTheme)

            // 关闭动态颜色
            vm.dispatch(ThemeViewModel.UiEvent.ToggleDynamicColor(false))
            val noDynamic = awaitItem()
            assertEquals(false, noDynamic.dynamicColor)

            // 切换主题色索引
            vm.dispatch(ThemeViewModel.UiEvent.ChangeThemeColor(3))
            val colorChanged = awaitItem()
            assertEquals(3, colorChanged.themeColorIndex)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
