package com.wangxingxing.wxxcomposetemplate.data.repository

import com.blankj.utilcode.util.LogUtils
import com.wangxingxing.wxxcomposetemplate.data.remote.api.LocalApiService
import com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult
import com.wangxingxing.wxxcomposetemplate.data.remote.api.User
import com.wangxingxing.wxxcomposetemplate.data.remote.api.CreateUserRequest
import com.wangxingxing.wxxcomposetemplate.data.remote.api.PageResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalUserRepository @Inject constructor(
    private val localApiService: LocalApiService
) {
    suspend fun getUsers(current: Int, size: Int): ApiResult<PageResponse<User>> = safeCall {
        localApiService.getUsers(current, size).toApiResult()
    }

    suspend fun getUser(id: Int): ApiResult<User> = safeCall {
        localApiService.getUser(id).toApiResult()
    }

    suspend fun createUser(username: String, email: String): ApiResult<User> = safeCall {
        localApiService.createUser(CreateUserRequest(username, email)).toApiResult()
    }

    private suspend fun <T> safeCall(block: suspend () -> ApiResult<T>): ApiResult<T> {
        return try {
            block()
        } catch (e: Exception) {
            LogUtils.e("LocalUserRepository error: ${e.message}")
            ApiResult.Error(-1, e.message ?: "Unknown error")
        }
    }
}
