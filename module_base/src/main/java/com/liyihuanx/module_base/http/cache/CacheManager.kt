package com.liyihuanx.module_base.http.cache

import android.util.Log
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
     * 是用一个user就可以还是每个Closeable对象都要配一个？？
     */
    @JvmStatic
    fun toObject(data: ByteArray): Any? {
//        var bais: ByteArrayInputStream? = null
//        var ois: ObjectInputStream? = null
//        try {
//            bais = ByteArrayInputStream(data)
//            ois = ObjectInputStream(bais)
//            return ois.readObject()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            try {
//                bais?.close()
//                ois?.close()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//        return null
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
//        var baos: ByteArrayOutputStream? = null
//        var oos: ObjectOutputStream? = null
//        try {
//            baos = ByteArrayOutputStream()
//            oos = ObjectOutputStream(baos)
//            oos.writeObject(body)
//            oos.flush()
//            return baos.toByteArray()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            try {
//                baos?.close()
//                oos?.close()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//        return ByteArray(0)

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
    fun <T> getCache(key: String): T? {
        val cache = CacheDatabase.database.getCacheDao().getCache(key)
        // 判断缓存是否过期
        cache?.also {
            it.effectiveTime?.plus(it.createTime)?.takeIf { time ->
                time < System.currentTimeMillis()
            }?.also {
                deleteCache(key)
                return null
            }
        }

        return (if (cache?.data != null) {
            toObject(cache.data!!)
        } else null) as? T?

    }

    /**
     * 删除缓存
     */
    @JvmStatic
    fun deleteCache(key: String) {
        Cache(key).also {
            CacheDatabase.database.getCacheDao().delete(it)
        }

    }

    /**
     * 保存缓存
     * 缓存时间默认半小时 30 * 60 * 1000
     */
    @JvmStatic
    fun <T> saveCache(key: String, body: T, effectiveTime: Long? = null) {
        val cache = Cache(key, toByteArray(body), System.currentTimeMillis(), effectiveTime)
        CacheDatabase.database.getCacheDao().saveCache(cache)
    }

}