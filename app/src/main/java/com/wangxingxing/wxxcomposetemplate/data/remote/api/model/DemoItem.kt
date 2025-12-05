package com.wangxingxing.wxxcomposetemplate.data.remote.api.model

/**
 * author : 王星星
 * date : 2025/12/5 11:19
 * email : 1099420259@qq.com
 * description : 列表示例数据项
 *  * @param id 唯一标识符
 *  * @param title 标题
 *  * @param content 内容描述
 *  * @param imageUrl 图片URL
 */
data class DemoItem(
    val id: Int,
    val title: String,
    val content: String,
    val imageUrl: String
)