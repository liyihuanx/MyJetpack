package com.liyihuanx.compiler

import com.liyihuanx.annotation.NetStrategy

/**
 * @author created by liyihuanx
 * @date 2021/9/14
 * @description: 类的描述
 */
data class CacheInfo(
    var netStrategy: Int = NetStrategy.NET_ONLY, // 缓存策略
    var isUserStrategyParameter: Boolean = false, // 缓存策略这个变量参数中
    var isUserStrategyFunction: Boolean = false, // 缓存策略用在方法上
    var effectiveTime: Double = 1.0, // 缓存时间
    var timeUnit: Long = NetStrategy.HOUR // 时间单位
)