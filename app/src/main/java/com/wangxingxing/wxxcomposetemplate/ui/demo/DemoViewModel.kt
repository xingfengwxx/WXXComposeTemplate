package com.wangxingxing.wxxcomposetemplate.ui.demo

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.wangxingxing.wxxcomposetemplate.base.BaseViewModel
import com.wangxingxing.wxxcomposetemplate.base.UiState
import com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult
import com.wangxingxing.wxxcomposetemplate.data.remote.api.model.DemoItem
import com.wangxingxing.wxxcomposetemplate.data.repository.DemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 示例页面 ViewModel
 */
@HiltViewModel
class DemoViewModel @Inject constructor(
    application: Application,
    private val repository: DemoRepository
) : BaseViewModel(application) {

    override val uiState = MutableStateFlow<UiState>(UiState.Idle)

    val items = MutableStateFlow<List<DemoItem>>(emptyList())

    init {
        loadData()
    }

    /**
     * 加载数据
     */
    fun loadData() {
        viewModelScope.launch {
            updateUiState(UiState.Loading)
            // 使用模拟数据
            val mockData = repository.getMockListData()
            items.value = mockData
            updateUiState(UiState.Success(mockData))
        }
    }

    /**
     * 刷新数据
     */
    fun refresh() {
        loadData()
    }
}
