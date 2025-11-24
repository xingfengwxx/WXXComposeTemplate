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
    
    /**
     * 获取项目分类
     * @return 项目分类响应
     */
    @GET("project/tree/json")
    suspend fun getProjectTree(): ProjectCategoryResponse
    
    /**
     * 获取 Banner 列表
     * @return Banner 响应
     */
    @GET("banner/json")
    suspend fun getBannerList(): BannerResponse
    
    /**
     * 获取文章列表
     * @param page 页码，从0开始
     * @param pageSize 每页数量，取值范围[1-40]，不传则使用默认值
     * @return 文章列表响应
     */
    @GET("article/list/{page}/json")
    suspend fun getArticleList(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int? = null
    ): ArticleResponse
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
