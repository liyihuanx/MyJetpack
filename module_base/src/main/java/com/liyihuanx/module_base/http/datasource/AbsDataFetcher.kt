package com.liyihuanx.module_base.http.datasource

import com.liyihuanx.module_base.http.cache.CacheManager
import com.liyihuanx.module_base.utils.AppContext
import com.liyihuanx.module_base.utils.NetUtil
import java.lang.Exception

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
        return CacheManager.getCache(key) as T?
    }

    /**
     * 存储缓存
     */
    fun saveCache(key: String?, data: T, effectiveTime: Long? = null) {
        if (key.isNullOrEmpty()) return
        CacheManager.saveCache(key, data, effectiveTime)
    }

    /**
     * 返回T,还是返回T?
     */
    suspend fun remoteRequest(): T? {
        return try {
            remoteQuest.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}