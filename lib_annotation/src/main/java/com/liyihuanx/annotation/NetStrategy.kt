package com.liyihuanx.annotation

/**
 * @author created by liyihuanx
 * @date 2021/9/8
 * @description: 类的描述
 */

annotation class NetStrategy(
    val strategy: Int = NET_ONLY,
    val effectiveTime: Double = 0.5,
    val timeUnit: Long = HOUR,
    val isNeedAddParameter: Boolean = false
) {

    companion object {
        const val CACHE_FIRST = 0 // 先取缓存，没有则发起网络请，然后更新缓存 CacheFirst(页面初始化)
        const val NET_ONLY = 1 // 只发起网络请求，不缓存 OnlyHttp(分页)
        const val NET_CACHE = 2 // 先接口，接口成功后更新缓存 NetCache (下拉属性)
        const val CACHE_ONLY = 3 // 取缓存，没有发起Http拿到数据然后保存，之后只读缓存

        const val HOUR = 60 * 60 * 1000L
        const val MIN = 60 * 1000L
        const val SECOND = 1000L
    }
}