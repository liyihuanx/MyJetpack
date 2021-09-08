package com.liyihuanx.module_base.http.request

import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * @ClassName: IHttpConfig
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/8 21:18
 */
interface IHttpConfig {

    fun getBaseUrl(): String
    fun client(): OkHttpClient
    fun build(builder: Retrofit.Builder)
}