package com.wangxingxing.wxxcomposetemplate.data.repository

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult
import com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiService
import com.wangxingxing.wxxcomposetemplate.data.remote.api.BingWallpaperService
import com.wangxingxing.wxxcomposetemplate.data.remote.api.DemoItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 示例 Repository，负责数据获取
 */
@Singleton
class DemoRepository @Inject constructor(
    private val apiService: ApiService,
    private val bingWallpaperService: BingWallpaperService,
    @ApplicationContext private val context: Context
) {
    /**
     * 获取列表数据
     */
    suspend fun getListData(page: Int = 1, size: Int = 20): ApiResult<List<DemoItem>> {
        return try {
            val response = apiService.getListData(page, size)
            response.toApiResult()
        } catch (e: Exception) {
            ApiResult.Error(-1, e.message ?: context.getString(com.wangxingxing.wxxcomposetemplate.R.string.error_network_request_failed))
        }
    }

    /**
     * 获取必应壁纸图片 URL
     * 使用必应官方 API 获取壁纸链接
     * @param index 日期索引，0 表示今天，1 表示昨天，以此类推（最大支持 7 天）
     * @return 必应壁纸图片 URL，如果获取失败则返回空字符串
     */
    private suspend fun getBingWallpaperImageUrl(index: Int): String {
        return try {
            // 使用必应官方 API
            // idx 参数：0=今天，1=昨天，2=前天，以此类推（最大 7）
            val idx = index.coerceIn(0, 7)
            // 调用必应壁纸 API
            val response = bingWallpaperService.getWallpaper(
                format = "js",
                idx = idx,
                n = 1,
                mkt = "zh-CN"
            )
            // 从响应中提取图片 URL
            if (response.images.isNotEmpty()) {
                val imageUrl = response.images[0].url
                // 拼接完整 URL
                if (imageUrl.startsWith("http")) {
                    imageUrl
                } else {
                    "https://cn.bing.com$imageUrl"
                }
            } else {
                // 如果获取失败，使用备用方案
                getRandomImageUrl(index)
            }
        } catch (e: Exception) {
            // 如果 API 调用失败，使用备用方案
            LogUtils.e("获取必应壁纸失败: ${e.message}")
            getRandomImageUrl(index)
        }
    }
    
    /**
     * 获取随机图片 URL（用于确保每个 item 显示不同的图片）
     * @param seed 随机种子，用于生成不同的图片
     * @return 图片 URL
     */
    private fun getRandomImageUrl(seed: Int): String {
        // 使用一个支持随机参数的图片服务
        // 通过不同的 seed 值确保每个 item 显示不同的图片
        // Picsum Photos 的 seed 功能可以确保相同的 seed 返回相同的图片
        return "https://picsum.photos/seed/$seed/200/200"
    }
    
    /**
     * 获取图片 URL（统一入口，确保每个 item 显示不同的图片）
     * @param itemId item 的唯一标识
     * @return 图片 URL
     */
    private suspend fun getImageUrl(itemId: Int): String {
        // 对于前 8 个 item，使用必应壁纸（索引 0-7）
        // 对于超过 8 个的 item，使用随机图片服务，通过 itemId 作为 seed 确保每个都不同
        return if (itemId <= 8) {
            getBingWallpaperImageUrl(itemId - 1)
        } else {
            getRandomImageUrl(itemId)
        }
    }

    /**
     * 模拟获取本地数据（用于演示）
     */
    suspend fun getMockListData(): List<DemoItem> {
        val list = mutableListOf<DemoItem>()
        // 第一个 item 是权限示例
        list.add(
            DemoItem(
                id = 1,
                title = context.getString(com.wangxingxing.wxxcomposetemplate.R.string.demo_permission_title),
                content = context.getString(com.wangxingxing.wxxcomposetemplate.R.string.demo_permission_content),
                imageUrl = getImageUrl(1)
            )
        )
        // 其他示例数据
        // 确保每个 item 显示不同的图片
        // 对于前 8 个 item，使用必应壁纸 API 获取真实壁纸
        // 对于超过 8 个的 item，使用随机图片服务
        (2..20).forEach { index ->
            list.add(
                DemoItem(
                    id = index,
                    title = context.getString(com.wangxingxing.wxxcomposetemplate.R.string.demo_item_title, index),
                    content = context.getString(com.wangxingxing.wxxcomposetemplate.R.string.demo_item_content, index),
                    imageUrl = getImageUrl(index)
                )
            )
        }
        return list
    }
}
