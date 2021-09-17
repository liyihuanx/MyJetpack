package com.liyihuan.module_common.service

import com.liyihuan.module_common.bean.ChapterBean
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

    @NetStrategy
    @AutoFlowApi(keys = ["page"], defaultValues = ["\"GS\""])
    suspend fun config2(page: String): List<String>



    @AutoFlowApi
    @NetStrategy(isNeedAddParameter = true)
    @GET("wxarticle/chapters/json")
    suspend fun getData(): List<ChapterBean>
}