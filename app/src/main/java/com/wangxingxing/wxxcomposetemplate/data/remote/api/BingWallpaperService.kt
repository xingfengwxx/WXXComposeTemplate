package com.wangxingxing.wxxcomposetemplate.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 必应壁纸 API 接口
 */
interface BingWallpaperService {
    
    /**
     * 获取必应壁纸信息
     * @param format 返回数据格式，js 为 JSON，xml 为 XML 格式
     * @param idx 请求图片的截止天数。0 表示今天，-1 为明天（预准备的图片），1 为昨天，以此类推，目前最多能获取到最近7天的图片
     * @param n 返回的图片数量，取值范围为 1 到 8
     * @param mkt 地区码，例如 zh-CN 为中国地区
     * @return 必应壁纸响应
     */
    @GET("HPImageArchive.aspx")
    suspend fun getWallpaper(
        @Query("format") format: String = "js",
        @Query("idx") idx: Int = 0,
        @Query("n") n: Int = 1,
        @Query("mkt") mkt: String = "zh-CN"
    ): BingWallpaperResponse
}

