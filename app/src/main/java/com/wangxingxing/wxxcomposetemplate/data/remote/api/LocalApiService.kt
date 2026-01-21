package com.wangxingxing.wxxcomposetemplate.data.remote.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import com.google.gson.annotations.SerializedName
import retrofit2.http.Query

interface LocalApiService {
    @GET("health/ping")
    suspend fun ping(): BaseResponse<PingResponse>

    // 支持分页参数 current 与 size，返回分页数据结构
    @GET("users")
    suspend fun getUsers(
        @Query("current") current: Int,
        @Query("size") size: Int
    ): BaseResponse<PageResponse<User>>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): BaseResponse<User>

    @POST("users")
    suspend fun createUser(@Body request: CreateUserRequest): BaseResponse<User>
}

// 通用用户实体
data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("createdAt") val createdAt: String?
)

// 创建用户请求体
data class CreateUserRequest(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String
)

// Ping 响应
data class PingResponse(
    @SerializedName("message") val message: String?
)

// 通用分页响应体，与后端字段对齐
data class PageResponse<T>(
    @SerializedName("records") val records: List<T> = emptyList(),
    @SerializedName("total") val total: Int = 0,
    @SerializedName("size") val size: Int = 0,
    @SerializedName("current") val current: Int = 1,
    @SerializedName("pages") val pages: Int = 0
)
