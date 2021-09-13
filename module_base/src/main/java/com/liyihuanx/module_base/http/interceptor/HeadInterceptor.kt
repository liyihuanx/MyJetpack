package com.liyihuanx.module_base.http.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.util.*
import kotlin.collections.HashMap

/**
 * @ClassName: HeadInterceptor
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/19 21:43
 */
class HeadInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        //此三行代码是每个自定义拦截器中必须的
//        Request request = chain.request();  Request 包含了请求头（header），请求体（body），访问的的 url。
//        Response response = chain.proceed(request); 包含了服务器或者本地返回响应头，响应体
//        return response;

        // 请求信息
        val request = chain.request().newBuilder()
            .addHeader("APPID", "12345")
            .addHeader("VERSION", "1.0.0")
            .addHeader("token", "1234")
            .build()

        // 中间可以加上自己的配置
        val params = HashMap<String, String>()
        params["APPID"] = "12345"
        params["VERSION"] = "1.0.0"

        val response = chain.proceed(request)
        val body = response.body()
        val bodyString = body!!.string()

        response.newBuilder()
            .body(ResponseBody.create(body.contentType(), bodyString))
            .build()

        // 传递给下一层拦截器获取他的返回结果
        return response

    }
}