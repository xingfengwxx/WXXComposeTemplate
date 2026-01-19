package com.wangxingxing.wxxcomposetemplate.data.remote.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import com.google.gson.annotations.SerializedName

interface LocalApiService {
    @GET("health/ping")
    suspend fun ping(): BaseResponse<PingResponse>

    @GET("users")
    suspend fun getUsers(): BaseResponse<List<User>>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): BaseResponse<User>

    @POST("users")
    suspend fun createUser(@Body request: CreateUserRequest): BaseResponse<User>
}

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("createdAt") val createdAt: String?
)

data class CreateUserRequest(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String
)

data class PingResponse(
    @SerializedName("message") val message: String?
)
