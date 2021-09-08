package com.liyihuanx.module_base.http.datasource

import com.liyihuanx.module_base.http.cache.CacheManager

/**
 * @author created by liyihuanx
 * @date 2021/9/8
 * @description: 类的描述
 */
abstract class AbsDataFetcher<T> {


    /**
     * 从数据库读取缓存
     */
    fun getCache(key: String): T? {
        return CacheManager.getCache(key) as T
    }


    /**
     * 存储缓存
     */
    fun saveCache(key: String, data: T) {
        CacheManager.saveCache(key, data)
    }
}