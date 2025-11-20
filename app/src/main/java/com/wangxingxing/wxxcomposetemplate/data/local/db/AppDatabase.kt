package com.wangxingxing.wxxcomposetemplate.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wangxingxing.wxxcomposetemplate.data.local.db.dao.DemoDao
import com.wangxingxing.wxxcomposetemplate.data.local.db.entity.DemoEntity

/**
 * author : 王星星
 * date : 2024-12-19
 * email : 1099420259@qq.com
 * description : Room 数据库
 */
@Database(
    entities = [DemoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    /**
     * 获取 DemoDao
     */
    abstract fun demoDao(): DemoDao
}
