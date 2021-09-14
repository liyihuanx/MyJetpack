package com.liyihuanx.myjetpack

import com.liyihuanx.annotation.AutoApi
import com.liyihuanx.annotation.AutoFlowApi
import com.liyihuanx.annotation.NetStrategy
import com.liyihuanx.module_base.bean.BaseCommonListResponse
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

    @NetStrategy
    @AutoFlowApi(keys = ["page"], defaultValues = ["\"GS\""])
    suspend fun config2(page: String): List<String>


    @NetStrategy(
        strategy = NetStrategy.CACHE_ONLY,
        effectiveTime = 10.0,
        timeUnit = NetStrategy.SECOND,
        isNeedAddParameter = false
    )
    @AutoFlowApi
    @GET("wxarticle/chapters/json")
    suspend fun getData(): List<ChapterBean>
}