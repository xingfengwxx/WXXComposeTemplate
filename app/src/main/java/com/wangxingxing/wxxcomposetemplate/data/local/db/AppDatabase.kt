package com.wangxingxing.wxxcomposetemplate.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * author : 王星星
 * date : 2024-12-19
 * email : 1099420259@qq.com
 * description : Room 数据库
 */
@Database(
    entities = [],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // 在这里添加 DAO
}
