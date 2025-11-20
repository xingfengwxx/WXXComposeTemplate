package com.wangxingxing.wxxcomposetemplate.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * author : 王星星
 * date : 2024-12-19
 * email : 1099420259@qq.com
 * description : 示例实体类，用于 Room 数据库
 */
@Entity(tableName = "demo_table")
data class DemoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val createTime: Long = 0L
)
