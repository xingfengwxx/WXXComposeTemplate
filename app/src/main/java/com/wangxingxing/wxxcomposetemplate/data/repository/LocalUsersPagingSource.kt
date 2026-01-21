package com.wangxingxing.wxxcomposetemplate.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult
import com.wangxingxing.wxxcomposetemplate.data.remote.api.PageResponse
import com.wangxingxing.wxxcomposetemplate.data.remote.api.User

/**
 * 使用后端分页接口实现 PagingSource
 */
class LocalUsersPagingSource(
    private val repository: LocalUserRepository,
    private val pageSize: Int,
    private val onPageMeta: (PageResponse<User>) -> Unit = {}
) : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        // 尽量靠近当前可见位置进行刷新
        val anchor = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchor)
        return page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 1
        val size = params.loadSize.coerceAtMost(pageSize)
        return when (val result = repository.getUsers(page, size)) {
            is ApiResult.Success -> {
                val pageData = result.data
                // 将分页元数据回传（用于显示总数等）
                onPageMeta(pageData)

                val nextKey = if (page < pageData.pages) page + 1 else null
                val prevKey = if (page > 1) page - 1 else null
                LoadResult.Page(
                    data = pageData.records,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
            is ApiResult.Error -> {
                LoadResult.Error(Exception("${result.code}: ${result.message}"))
            }
            else -> LoadResult.Error(Exception("Unknown state"))
        }
    }
}
