package com.wangxingxing.wxxcomposetemplate.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * author : 王星星
 * date : 2024-12-19
 * email : 1099420259@qq.com
 * description : API 接口定义
 */
interface ApiService {

    /**
     * 示例：获取列表数据
     */
    @GET("api/list")
    suspend fun getListData(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): BaseResponse<List<DemoItem>>
}

/**
 * 示例数据模型
 */
data class DemoItem(
    val id: Int,
    val title: String,
    val content: String,
    val imageUrl: String? = null
)
