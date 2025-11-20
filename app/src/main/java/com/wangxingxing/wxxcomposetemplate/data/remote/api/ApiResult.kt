package com.wangxingxing.wxxcomposetemplate.data.remote.api

/**
 * author : 王星星
 * date : 2024-12-19
 * email : 1099420259@qq.com
 * description : 统一 API 响应结果封装
 */
sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val code: Int, val message: String) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}

/**
 * API 响应基础数据类
 */
data class BaseResponse<T>(
    val code: Int,
    val message: String,
    val data: T?
) {
    fun isSuccess(): Boolean = code == 200

    fun toApiResult(): ApiResult<T> {
        return if (isSuccess() && data != null) {
            ApiResult.Success(data)
        } else {
            ApiResult.Error(code, message)
        }
    }
}
