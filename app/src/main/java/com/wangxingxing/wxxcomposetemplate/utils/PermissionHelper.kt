package com.wangxingxing.wxxcomposetemplate.utils

import android.app.Activity
import android.content.Context
import com.hjq.permissions.XXPermissions
import com.hjq.permissions.permission.base.IPermission
import com.hjq.permissions.permission.PermissionLists
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 权限请求工具类，封装 XXPermissions
 */
object PermissionHelper {

    /**
     * 请求单个权限（协程方式）
     */
    suspend fun requestPermission(
        activity: Activity,
        permission: IPermission
    ): Boolean = suspendCancellableCoroutine { continuation ->
        XXPermissions.with(activity)
            .permission(permission)
            .request { grantedList, deniedList ->
                val allGranted = deniedList.isEmpty()
                continuation.resume(allGranted)
            }
    }

    /**
     * 请求多个权限（协程方式）
     */
    suspend fun requestPermissions(
        activity: Activity,
        vararg permissions: IPermission
    ): Boolean = suspendCancellableCoroutine { continuation ->
        XXPermissions.with(activity).apply {
            permissions.forEach { permission ->
                permission(permission)
            }
        }.request { grantedList, deniedList ->
            val allGranted = deniedList.isEmpty()
            continuation.resume(allGranted)
        }
    }

    /**
     * 检查权限是否已授予
     */
    fun isGranted(context: Context, permission: IPermission): Boolean {
        return XXPermissions.isGrantedPermission(context, permission)
    }

    /**
     * 检查多个权限是否都已授予
     */
    fun isGranted(context: Context, vararg permissions: IPermission): Boolean {
        return XXPermissions.isGrantedPermissions(context, permissions.toList())
    }

    /**
     * 请求存储权限
     * 注意：当 targetSdk >= 30 时，只能申请 MANAGE_EXTERNAL_STORAGE 权限
     * 框架会自动在低版本设备上适配 READ_EXTERNAL_STORAGE 和 WRITE_EXTERNAL_STORAGE
     */
    suspend fun requestStoragePermission(activity: Activity): Boolean {
        // 根据 XXPermissions 文档，targetSdk >= 30 时只申请 MANAGE_EXTERNAL_STORAGE
        // 框架会自动在低版本设备上适配其他存储权限
        return requestPermission(activity, PermissionLists.getManageExternalStoragePermission())
    }

    /**
     * 请求相机权限
     */
    suspend fun requestCameraPermission(activity: Activity): Boolean {
        return requestPermission(activity, PermissionLists.getCameraPermission())
    }

    /**
     * 请求位置权限
     */
    suspend fun requestLocationPermission(activity: Activity): Boolean {
        return requestPermissions(
            activity,
            PermissionLists.getAccessFineLocationPermission(),
            PermissionLists.getAccessCoarseLocationPermission()
        )
    }
}