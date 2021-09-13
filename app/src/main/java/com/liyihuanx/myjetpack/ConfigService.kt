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
    @GET("wxarticle/chapters/json")
    suspend fun config(): ChapterBean

    @NetStrategy(isNeedAddParameter = true)
    @AutoFlowApi(keys = ["page"], defaultValues = ["\"GS\""])
    suspend fun config2(page: String): List<String>


    @NetStrategy(NetStrategy.CACHE_FIRST)
    @AutoFlowApi
    @GET("wxarticle/chapters/json")
    suspend fun getData(): ChapterBean
}