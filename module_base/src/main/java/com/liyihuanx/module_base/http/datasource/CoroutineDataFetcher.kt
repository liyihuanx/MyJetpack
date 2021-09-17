package com.liyihuanx.module_base.http.datasource

import android.util.Log
import com.liyihuanx.annotation.NetStrategy
import com.liyihuanx.module_base.utils.AppContext
import com.liyihuanx.module_base.utils.NetUtil
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
     * // 1.OnlyHttp(分页)：只发起网络请求，不缓存
     * // 0.CacheFirst(页面初始化): 先取缓存，没有则发起网络请，然后更新缓存,会发送两次数据 --> 可以改进为在无网络或者网络差时取缓存，正常网络可以不读缓存
     * // 2.NetCache(下拉属性): 先接口，接口成功后更新缓存
     * // 3.CACHE_ONLY: 取缓存，没有发起Http拿到数据然后保存，之后在没有超出有效期限的情况下只读缓存，
     * // 每一个请求都新建一个Flow, 能否做复用？
     */

    fun startFetchData(
        cacheStrategy: Int? = NetStrategy.NET_ONLY,
        cacheKey: String? = null,
        effectiveTime: Long? = null
    ): Flow<T?> {
        return when (cacheStrategy) {
            NetStrategy.NET_ONLY -> flow {
                emit(remoteRequest())
            }

            NetStrategy.CACHE_FIRST -> {
                flow {
                    emit(getCache(cacheKey))
                    emit(getRequest(cacheKey, effectiveTime))
                }
            }

            NetStrategy.NET_CACHE -> {
                flow {
                    emit(getRequest(cacheKey, effectiveTime))
                }
            }
            NetStrategy.CACHE_ONLY -> {
                flow {
                    emit(getCache(cacheKey) ?: getRequest(cacheKey, effectiveTime))
                }
            }


            else -> flow { emit(remoteRequest()) }
        }.flowOn(Dispatchers.IO)
    }


    /**
     * 请求接口且做缓存
     */
    private suspend fun getRequest(cacheKey: String? = null, effectiveTime: Long? = null): T? {
        return remoteRequest().takeIf { it != null }
            ?.also { saveCache(cacheKey, it, effectiveTime) }
    }

}