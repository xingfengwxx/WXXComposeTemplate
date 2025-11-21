package com.wangxingxing.wxxcomposetemplate.data.remote.api

import com.google.gson.annotations.SerializedName

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 必应壁纸 API 响应数据模型
 */

/**
 * 必应壁纸 API 响应
 */
data class BingWallpaperResponse(
    @SerializedName("images")
    val images: List<BingImage>
)

/**
 * 必应壁纸图片信息
 */
data class BingImage(
    @SerializedName("url")
    val url: String,
    @SerializedName("urlbase")
    val urlbase: String? = null,
    @SerializedName("copyright")
    val copyright: String? = null,
    @SerializedName("copyrightlink")
    val copyrightlink: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("quiz")
    val quiz: String? = null,
    @SerializedName("wp")
    val wp: Boolean? = null,
    @SerializedName("hsh")
    val hsh: String? = null,
    @SerializedName("drk")
    val drk: Int? = null,
    @SerializedName("top")
    val top: Int? = null,
    @SerializedName("bot")
    val bot: Int? = null,
    @SerializedName("hs")
    val hs: List<Any>? = null
)

