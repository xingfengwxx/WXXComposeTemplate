package com.wangxingxing.wxxcomposetemplate.data.remote.api

import com.google.gson.annotations.SerializedName

/**
 * author : 王星星
 * date : 2025/01/20
 * email : 1099420259@qq.com
 * description : Banner 响应数据模型（wanandroid API 格式）
 */
data class BannerResponse(
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("errorMsg")
    val errorMsg: String,
    @SerializedName("data")
    val data: List<Banner>?
) {
    fun isSuccess(): Boolean = errorCode == 0

    fun toApiResult(): ApiResult<List<Banner>> {
        return if (isSuccess() && data != null) {
            ApiResult.Success(data)
        } else {
            ApiResult.Error(errorCode, errorMsg)
        }
    }
}

/**
 * Banner 数据模型
 */
data class Banner(
    @SerializedName("desc")
    val desc: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imagePath")
    val imagePath: String,
    @SerializedName("isVisible")
    val isVisible: Int,
    @SerializedName("order")
    val order: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("url")
    val url: String
)

