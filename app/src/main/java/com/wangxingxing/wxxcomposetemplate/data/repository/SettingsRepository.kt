package com.wangxingxing.wxxcomposetemplate.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * author : 王星星
 * date : 2025/01/20
 * email : 1099420259@qq.com
 * description : 设置 Repository，负责设置数据的存储和读取
 */
@Singleton
class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val THEME_COLOR_KEY = intPreferencesKey("theme_color")
    }

    /**
     * 获取主题色
     */
    val themeColor: Flow<Int> = dataStore.data.map { preferences ->
        preferences[THEME_COLOR_KEY] ?: 0 // 0 表示默认主题色
    }

    /**
     * 保存主题色
     */
    suspend fun saveThemeColor(colorIndex: Int) {
        dataStore.edit { preferences ->
            preferences[THEME_COLOR_KEY] = colorIndex
        }
    }
}

