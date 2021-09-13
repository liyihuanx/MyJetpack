package com.liyihuanx.module_base.http.datasource

import android.util.Log
import com.liyihuanx.annotation.NetStrategy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

/**
 * @author created by liyihuanx
 * @date 2021/9/8
 * @description: remoteQuest为Http请求
 */
class CoroutineDataFetcher<T>(remoteQuest: suspend () -> T) : AbsDataFetcher<T>(remoteQuest) {

    /**
     * 根据请求策略，发起Http请求
     * // 1.只发起网络请求，不缓存 OnlyHttp(分页)
     * // 2.先取缓存，没有发起网络请，然后更新缓存 CacheFirst(页面初始化)
     * // 3.先接口，接口成功后更新缓存 NetCache (下拉属性)
     * // 每一个请求都新建一个Flow, 能否做复用？
     */

    fun startFetchData(
        cacheStrategy: Int? = NetStrategy.NET_ONLY,
        cacheKey: String? = null
    ): Flow<T?> {
        return when (cacheStrategy) {
            NetStrategy.NET_ONLY -> flow {
                emit(remoteRequest())
            }

            NetStrategy.CACHE_FIRST -> {
                flow {
                    emit(getCache(cacheKey)
                        ?: remoteRequest()
                            .takeIf { it != null }
                            ?.also { saveCache(cacheKey, it) }
                    )
                }
            }

            NetStrategy.NET_CACHE -> {
                flow {
                    emit(
                        remoteRequest()?.also {
                            saveCache(cacheKey, it)
                        }
                    )
                }
            }

            else -> flow { emit(this@CoroutineDataFetcher.remoteRequest()) }
        }.flowOn(Dispatchers.IO)
    }


}