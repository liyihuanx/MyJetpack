package com.liyihuanx.module_base.http.cache

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * @author created by liyihuanx
 * @date 2021/9/8
 * @description: 类的描述
 */
object CacheManager {

    /**
     * 反序列,把二进制数据转换成java object对象
     */
    @JvmStatic
    fun toObject(data: ByteArray): Any? {
        return ByteArrayInputStream(data).use {
            ObjectInputStream(it).use {
                it.readObject()
            }
        }
    }

    /**
     * 序列化存储数据需要转换成二进制
     */
    @JvmStatic
    fun <T> toByteArray(body: T): ByteArray {
        return ByteArrayOutputStream().use {
            ObjectOutputStream(it).use { ObjectOutputStream ->
                ObjectOutputStream.writeObject(body)
                ObjectOutputStream.flush()
            }
            it.toByteArray()
        }
    }


    /**
     * 根据key获取缓存
     */
    @JvmStatic
    fun getCache(key: String): Any? {
        val cache = CacheDatabase.database.getCacheDao().getCache(key)
        if (cache?.data != null) {
            return toObject(cache.data)!!
        }

        return null
    }

    /**
     * 删除缓存
     */
    @JvmStatic
    fun <T> deleteCache(key: String, body: T) {
        val cache = Cache(key, toByteArray(body))
        CacheDatabase.database.getCacheDao().delete(cache)
    }

    /**
     * 保存缓存
     */
    @JvmStatic
    fun <T> saveCache(key: String, body: T) {
        val cache = Cache(key, toByteArray(body))
        CacheDatabase.database.getCacheDao().saveCache(cache)
    }

}