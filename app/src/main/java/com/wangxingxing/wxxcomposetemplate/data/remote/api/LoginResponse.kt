package com.wangxingxing.wxxcomposetemplate.data.remote.api

import com.google.gson.annotations.SerializedName

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 登录响应数据模型（wanandroid API 格式）
 */
data class LoginResponse<T>(
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("errorMsg")
    val errorMsg: String,
    @SerializedName("data")
    val data: T?
) {
    fun isSuccess(): Boolean = errorCode == 0

    fun toApiResult(): ApiResult<T> {
        return if (isSuccess() && data != null) {
            ApiResult.Success(data)
        } else {
            ApiResult.Error(errorCode, errorMsg)
        }
    }
}

/**
 * 登录用户信息
 */
data class UserInfo(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("nickname")
    val nickname: String? = null,
    @SerializedName("publicName")
    val publicName: String? = null,
    @SerializedName("token")
    val token: String? = null
)

