package ui.permission

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.wangxingxing.wxxcomposetemplate.utils.permission.PermissionHelper
import com.wangxingxing.wxxcomposetemplate.R

/**
 * author : 王星星
 * date : 2025/11/20 19:26
 * email : 1099420259@qq.com
 * description : 权限请求示例页面
 */
@Composable
fun PermissionScreen() {
    val activity = LocalActivity.current
    val scope = rememberCoroutineScope()
    val initialStatus = stringResource(R.string.permission_not_requested)
    var permissionStatus by remember { 
        mutableStateOf(initialStatus)
    }
    
    // 预先获取所有需要的字符串资源
    val permissionTitle = stringResource(R.string.permission_title)
    val permissionGranted = stringResource(R.string.permission_granted)
    val permissionDenied = stringResource(R.string.permission_denied)
    val permissionNeedActivity = stringResource(R.string.permission_need_activity)
    val permissionRequestStorage = stringResource(R.string.permission_request_storage)
    val permissionRequestCamera = stringResource(R.string.permission_request_camera)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = permissionTitle,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = stringResource(R.string.permission_status, permissionStatus),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                scope.launch {
                    if (activity != null) {
                        val granted = PermissionHelper.requestStoragePermission(activity)
                        permissionStatus = if (granted) {
                            permissionGranted
                        } else {
                            permissionDenied
                        }
                    } else {
                        permissionStatus = permissionNeedActivity
                    }
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(permissionRequestStorage)
        }

        Button(
            onClick = {
                scope.launch {
                    if (activity != null) {
                        val granted = PermissionHelper.requestCameraPermission(activity)
                        permissionStatus = if (granted) {
                            permissionGranted
                        } else {
                            permissionDenied
                        }
                    } else {
                        permissionStatus = permissionNeedActivity
                    }
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(permissionRequestCamera)
        }
    }
}
