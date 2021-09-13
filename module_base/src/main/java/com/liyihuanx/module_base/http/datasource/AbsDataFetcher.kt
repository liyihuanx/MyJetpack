package com.liyihuanx.module_base.http.datasource

import android.util.Log
import com.liyihuanx.annotation.NetStrategy
import com.liyihuanx.module_base.http.cache.CacheManager
import kotlinx.coroutines.flow.Flow
import okhttp3.internal.cache.CacheStrategy

/**
 * @author created by liyihuanx
 * @date 2021/9/8
 * @description: 类的描述
 */
abstract class AbsDataFetcher<T>(private val remoteQuest: suspend () -> T) {


    /**
     * 从数据库读取缓存
     */
    fun getCache(key: String?): T? {
        if (key.isNullOrEmpty()) return null
        Log.d("QWER", "getCache: ")
        return CacheManager.getCache(key) as T?
    }


    /**
     * 存储缓存
     */
    fun saveCache(key: String?, data: T) {
        if (key.isNullOrEmpty()) return
        CacheManager.saveCache(key, data)
    }



    /**
     * 返回T,还是返回T?
     */
    suspend fun remoteRequest(): T? {
        Log.d("QWER", "remoteRequest: ")
        return remoteQuest.invoke()
    }
}