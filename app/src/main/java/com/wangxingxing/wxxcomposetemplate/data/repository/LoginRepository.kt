package com.wangxingxing.wxxcomposetemplate.data.repository

import com.wangxingxing.wxxcomposetemplate.data.remote.api.ApiResult
import com.wangxingxing.wxxcomposetemplate.data.remote.api.UserInfo
import com.wangxingxing.wxxcomposetemplate.data.remote.api.WanAndroidApiService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 登录 Repository，负责登录相关数据获取
 */
@Singleton
class LoginRepository @Inject constructor(
    private val wanAndroidApiService: WanAndroidApiService
) {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    suspend fun login(username: String, password: String): ApiResult<UserInfo> {
        return try {
            val response = wanAndroidApiService.login(username, password)
            response.toApiResult()
        } catch (e: Exception) {
            ApiResult.Error(-1, e.message ?: "登录失败")
        }
    }
}

