package com.liyihuanx.annotation

/**
 * @author created by liyihuanx
 * @date 2021/9/8
 * @description: 类的描述
 */
enum class NetStrategy(strategyName: String) {
    /**
     * 只请求缓存
     */
    OnlyCache("OnlyCache"),

    /**
     * 只请求网络
     */
    OnlyHttp("OnlyHttp"),

    /**
     * 优先缓存，缓存拿到就展示缓存，缓存没拿到就请求网络
     */
    CacheFirst("CacheFirst"),

    /**
     * 缓存和网络同时请求，数据会返回2次（如果都成功的话）
     */
    Both("Both");

    var strategy = "OnlyRemote"

    init {
        strategy = strategyName
    }
}