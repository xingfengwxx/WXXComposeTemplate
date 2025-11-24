package com.wangxingxing.wxxcomposetemplate.ui.projectcategory

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.wangxingxing.wxxcomposetemplate.base.BaseViewModel
import com.wangxingxing.wxxcomposetemplate.base.UiState
import com.wangxingxing.wxxcomposetemplate.data.local.db.entity.ProjectCategoryEntity
import com.wangxingxing.wxxcomposetemplate.data.repository.ProjectCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/01/20
 * email : 1099420259@qq.com
 * description : 项目分类页面 ViewModel
 */
@HiltViewModel
class ProjectCategoryViewModel @Inject constructor(
    application: Application,
    private val repository: ProjectCategoryRepository
) : BaseViewModel(application) {

    override val uiState = MutableStateFlow<UiState>(UiState.Idle)

    /**
     * 项目分类列表
     */
    val categories = MutableStateFlow<List<ProjectCategoryEntity>>(emptyList())

    init {
        loadData()
    }

    /**
     * 加载数据
     */
    fun loadData() {
        viewModelScope.launch {
            updateUiState(UiState.Loading)
            try {
                // 检查本地是否有数据
                val hasLocalData = repository.hasLocalData()
                if (!hasLocalData) {
                    // 本地没有数据，从网络获取
                    val result = repository.fetchAndSaveProjectCategories()
                    when (result) {
                        is com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult.Success -> {
                            categories.value = result.data
                            updateUiState(UiState.Success(result.data))
                        }
                        is com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult.Error -> {
                            updateUiState(UiState.Error(result.message))
                        }
                        else -> {
                            updateUiState(UiState.Error(getApplication<Application>().getString(
                                com.wangxingxing.wxxcomposetemplate.R.string.error_unknown
                            )))
                        }
                    }
                }
                
                // 监听本地数据变化（无论是否有本地数据，都开始监听）
                repository.getProjectCategories()
                    .catch { e ->
                        if (uiState.value !is UiState.Error) {
                            updateUiState(UiState.Error(e.message ?: getApplication<Application>().getString(
                                com.wangxingxing.wxxcomposetemplate.R.string.error_unknown
                            )))
                        }
                    }
                    .collect { data ->
                        categories.value = data
                        // 只有在成功获取到数据时才更新状态
                        if (data.isNotEmpty() && uiState.value !is UiState.Success<*>) {
                            updateUiState(UiState.Success(data))
                        } else if (data.isNotEmpty()) {
                            // 数据已更新，但状态已经是 Success，不需要再次更新
                        }
                    }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    /**
     * 刷新数据（强制从网络获取）
     * 
     * 下拉刷新时，不管本地是否有数据都请求网络
     * 此方法会先清空本地数据，然后从网络获取最新数据并保存到本地
     * 
     * 添加了最小显示时间（500ms），防止接口返回太快导致加载动画一闪而过
     */
    fun refresh() {
        viewModelScope.launch {
            updateUiState(UiState.Loading)
            
            // 最小显示时间：500毫秒，防止动画一闪而过
            val minDisplayTime = 500L
            
            try {
                // 并行执行网络请求和最小时间延迟
                val networkRequest = async {
                    // 不管本地是否有数据，都从网络获取最新数据
                    repository.refreshProjectCategories()
                }
                
                val minTimeDelay = async {
                    delay(minDisplayTime)
                }
                
                // 等待网络请求和最小时间都完成
                val result = networkRequest.await()
                minTimeDelay.await()
                
                when (result) {
                    is com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult.Success -> {
                        categories.value = result.data
                        updateUiState(UiState.Success(result.data))
                    }
                    is com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult.Error -> {
                        updateUiState(UiState.Error(result.message))
                    }
                    else -> {
                        updateUiState(UiState.Error(getApplication<Application>().getString(
                            com.wangxingxing.wxxcomposetemplate.R.string.error_unknown
                        )))
                    }
                }
            } catch (e: Exception) {
                // 即使出错，也确保最小显示时间
                delay(minDisplayTime)
                handleError(e)
            }
        }
    }
}

