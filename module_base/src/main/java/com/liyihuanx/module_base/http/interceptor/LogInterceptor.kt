package com.liyihuanx.module_base.http.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

import java.io.IOException


/**
 * @author liyihuan
 * @date 2022/01/06
 * @Description
 */
class LogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        //1.请求前--打印请求信息


        //2.网络请求
        val response: Response = chain.proceed(request)

        //3.网络响应后--打印响应信息

        return response
    }

}