package com.wangxingxing.wxxcomposetemplate.data.remote.api

import retrofit2.http.*

/**
 * author : 王星星
 * date : 2025/11/20 19:26
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
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : WanAndroid API 接口定义
 */
interface WanAndroidApiService {

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录响应
     */
    @POST("user/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse<UserInfo>
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
