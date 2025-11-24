package com.wangxingxing.wxxcomposetemplate.ui.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.wangxingxing.wxxcomposetemplate.base.BaseViewModel
import com.wangxingxing.wxxcomposetemplate.base.UiState
import com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult
import com.wangxingxing.wxxcomposetemplate.data.remote.api.Banner
import com.wangxingxing.wxxcomposetemplate.data.repository.ArticlePagingSourceFactory
import com.wangxingxing.wxxcomposetemplate.data.repository.BannerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/01/20
 * email : 1099420259@qq.com
 * description : 首页 ViewModel
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val bannerRepository: BannerRepository,
    private val articlePagingSourceFactory: ArticlePagingSourceFactory
) : BaseViewModel(application) {

    override val uiState = MutableStateFlow<UiState>(UiState.Idle)

    /**
     * Banner 列表
     */
    val banners = MutableStateFlow<List<Banner>>(emptyList())

    /**
     * 文章列表 Paging Flow
     */
    val articles = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        pagingSourceFactory = { articlePagingSourceFactory.create() }
    ).flow.cachedIn(viewModelScope)

    init {
        loadBannerData()
    }

    /**
     * 加载 Banner 数据
     */
    fun loadBannerData() {
        viewModelScope.launch {
            updateUiState(UiState.Loading)
            try {
                val result = bannerRepository.getBannerList()
                when (result) {
                    is ApiResult.Success -> {
                        banners.value = result.data
                        updateUiState(UiState.Success(result.data))
                    }
                    is ApiResult.Error -> {
                        updateUiState(UiState.Error(result.message))
                    }
                    else -> {
                        updateUiState(UiState.Error(getApplication<Application>().getString(
                            com.wangxingxing.wxxcomposetemplate.R.string.error_unknown
                        )))
                    }
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    /**
     * 刷新 Banner 数据
     */
    fun refreshBanner() {
        loadBannerData()
    }
}

