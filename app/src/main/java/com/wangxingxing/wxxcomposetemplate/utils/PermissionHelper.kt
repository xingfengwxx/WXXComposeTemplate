package com.wangxingxing.wxxcomposetemplate.utils

import android.app.Activity
import android.content.Context
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * author : 王星星
 * date : 2024-12-19
 * email : 1099420259@qq.com
 * description : 权限请求工具类，封装 XXPermissions
 */
object PermissionHelper {

    /**
     * 请求单个权限（协程方式）
     */
    suspend fun requestPermission(
        activity: Activity,
        permission: String
    ): Boolean = suspendCancellableCoroutine { continuation ->
        XXPermissions.with(activity)
            .permission(permission)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    continuation.resume(allGranted)
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    continuation.resume(false)
                }
            })
    }

    /**
     * 请求多个权限（协程方式）
     */
    suspend fun requestPermissions(
        activity: Activity,
        vararg permissions: String
    ): Boolean = suspendCancellableCoroutine { continuation ->
        XXPermissions.with(activity)
            .permission(*permissions)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    continuation.resume(allGranted)
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    continuation.resume(false)
                }
            })
    }

    /**
     * 检查权限是否已授予
     */
    fun isGranted(context: Context, permission: String): Boolean {
        return XXPermissions.isGranted(context, permission)
    }

    /**
     * 检查多个权限是否都已授予
     */
    fun isGranted(context: Context, vararg permissions: String): Boolean {
        return XXPermissions.isGranted(context, *permissions)
    }

    /**
     * 请求存储权限
     */
    suspend fun requestStoragePermission(activity: Activity): Boolean {
        return requestPermissions(
            activity,
            Permission.MANAGE_EXTERNAL_STORAGE,
            Permission.READ_EXTERNAL_STORAGE,
            Permission.WRITE_EXTERNAL_STORAGE
        )
    }

    /**
     * 请求相机权限
     */
    suspend fun requestCameraPermission(activity: Activity): Boolean {
        return requestPermission(activity, Permission.CAMERA)
    }

    /**
     * 请求位置权限
     */
    suspend fun requestLocationPermission(activity: Activity): Boolean {
        return requestPermissions(
            activity,
            Permission.ACCESS_FINE_LOCATION,
            Permission.ACCESS_COARSE_LOCATION
        )
    }
}
