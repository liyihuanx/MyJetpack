package com.liyihuanx.module_base.http.datasource

import com.liyihuanx.annotation.NetStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * @author created by liyihuanx
 * @date 2021/9/8
 * @description: 类的描述
 */
class CoroutineDataFetcher<T>(
    private var netStrategy: NetStrategy = NetStrategy.OnlyHttp, // 请求策略
    private var remoteQuest: suspend () -> T // http 请求
) : AbsDataFetcher<T>() {

    /**
     * 根据请求策略，发起Http请求
     */
    fun startFetchData(): Flow<T> {
        return when (netStrategy) {
            NetStrategy.OnlyHttp -> flow { emit(this@CoroutineDataFetcher.remoteRequest()) }

            NetStrategy.OnlyCache -> {
                flow {
                    val localData = getCache("")
                    if (localData != null) {
                        emit(localData)
                    }
                }
            }

            NetStrategy.CacheFirst -> {
                flow {
                    emit(getCache("") ?: this@CoroutineDataFetcher.remoteRequest())
                }
            }

            NetStrategy.Both -> {
                val localData = getCache("")
                flow {
                    if (localData != null) {
                        emit(localData)
                    }
                    emit(this@CoroutineDataFetcher.remoteRequest())
                }
            }

            else -> flow { emit(this@CoroutineDataFetcher.remoteRequest()) }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun remoteRequest(): T {
        return remoteQuest.invoke()
    }

}