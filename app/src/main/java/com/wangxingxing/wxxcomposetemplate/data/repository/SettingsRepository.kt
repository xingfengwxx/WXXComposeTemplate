package com.wangxingxing.wxxcomposetemplate.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * author : 王星星
 * date : 2025/01/20
 * email : 1099420259@qq.com
 * description : 设置 Repository，负责主题相关数据的存储和读取
 */
@Singleton
class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val THEME_COLOR_KEY = intPreferencesKey("theme_color")
        private val THEME_MODE_KEY = intPreferencesKey("theme_mode") // 0:system, 1:light, 2:dark
        private val DYNAMIC_COLOR_KEY = booleanPreferencesKey("dynamic_color")
    }

    // 主题色索引
    val themeColor: Flow<Int> = dataStore.data.map { preferences ->
        preferences[THEME_COLOR_KEY] ?: 0
    }

    // 主题模式：0 跟随系统 / 1 浅色 / 2 深色
    val themeMode: Flow<Int> = dataStore.data.map { preferences ->
        preferences[THEME_MODE_KEY] ?: 0
    }

    // 动态颜色开关
    val dynamicColorEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DYNAMIC_COLOR_KEY] ?: true
    }

    /**
     * 保存主题色
     */
    suspend fun saveThemeColor(colorIndex: Int) {
        dataStore.edit { preferences ->
            preferences[THEME_COLOR_KEY] = colorIndex
        }
    }

    /**
     * 保存主题模式
     */
    suspend fun saveThemeMode(mode: Int) {
        require(mode in 0..2) { "Invalid theme mode: $mode" }
        dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = mode
        }
    }

    /**
     * 保存动态颜色开关状态
     */
    suspend fun saveDynamicColorEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DYNAMIC_COLOR_KEY] = enabled
        }
    }
}
