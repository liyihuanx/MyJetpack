package com.liyihuanx.module_base.http.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.liyihuanx.module_base.utils.AppContext

/**
 * @author created by liyihuanx
 * @date 2021/9/8
 * @description: 类的描述
 */
//数据读取、存储时数据转换器,比如将写入时将Date转换成Long存储，读取时把Long转换Date返回
//@TypeConverters(DateConverter.class)
@Database(entities = [Cache::class], version = 1)
abstract class CacheDatabase : RoomDatabase() {

    companion object {
        var database =
            Room.databaseBuilder(AppContext.get(), CacheDatabase::class.java, "http_cache")
                .allowMainThreadQueries() //是否允许在主线程进行查询
                .build()
        /**
         *  //数据库创建和打开后的回调
         *  //.addCallback()
         *  //设置查询的线程池
         *  //.setQueryExecutor()
         *  //.openHelperFactory()
         *  //room的日志模式
         *  //.setJournalMode()
         *  //数据库升级异常之后的回滚
         *  //.fallbackToDestructiveMigration()
         *  //数据库升级异常后根据指定版本进行回滚
         *  //.fallbackToDestructiveMigrationFrom()
         *  //.addMigrations(CacheDatabase.sMigration)
         */
    }


    abstract fun getCacheDao(): CacheDao


}