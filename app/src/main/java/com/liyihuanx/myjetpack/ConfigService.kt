package com.liyihuanx.myjetpack

import com.liyihuanx.annotation.AutoApi
import com.liyihuanx.annotation.AutoFlowApi
import com.liyihuanx.annotation.NetStrategy
import retrofit2.http.GET

/**
 * @author created by liyihuanx
 * @date 2021/9/7
 * @description: 类的描述
 */
interface ConfigService {

    @AutoApi
    @NetStrategy(isNeedAddParameter = true)
    suspend fun config(page: String): String

    @NetStrategy(NetStrategy.NET_CACHE, isNeedAddParameter = true)
    @AutoFlowApi(keys = ["page"], defaultValues = ["\"GS\""])
    suspend fun config2(page: String): List<String>


    @NetStrategy(NetStrategy.NET_CACHE)
    @AutoFlowApi
    @GET("wxarticle/chapters/json")
    suspend fun getData(): ChapterBean
}