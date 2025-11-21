package com.wangxingxing.wxxcomposetemplate.data.repository

import com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult
import com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiService
import com.wangxingxing.wxxcomposetemplate.data.remote.api.DemoItem
import javax.inject.Inject
import javax.inject.Singleton

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 示例 Repository，负责数据获取
 */
@Singleton
class DemoRepository @Inject constructor(
    private val apiService: ApiService
) {
    /**
     * 获取列表数据
     */
    suspend fun getListData(page: Int = 1, size: Int = 20): ApiResult<List<DemoItem>> {
        return try {
            val response = apiService.getListData(page, size)
            response.toApiResult()
        } catch (e: Exception) {
            ApiResult.Error(-1, e.message ?: "网络请求失败")
        }
    }

    /**
     * 模拟获取本地数据（用于演示）
     */
    suspend fun getMockListData(): List<DemoItem> {
        return (1..20).map { index ->
            DemoItem(
                id = index,
                title = "标题 $index",
                content = "这是第 $index 条内容",
                imageUrl = "https://picsum.photos/200/200?random=$index"
            )
        }
    }
}
