package com.liyihuanx.module_base.http.interceptor

import android.util.Log
import com.liyihuanx.module_logutil.MLog
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * 打印出请求的Url和结果
 */
class CustomLogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val body = response.body()
        val bodyString = body!!.string()
        Log.v(
            "Http_Request", "intercept: " + String.format(
                "result\nUrl=%s\nbody=%s",
                request.url(),
                bodyString
            )
        )
        // 解决java.lang.IllegalStateException: closed，因为Response.body().string()方法只能读取一次
        response.newBuilder()
            .body(ResponseBody.create(body.contentType(), bodyString))
            .build()
        return response
    }


    private fun printParams(url: String, params: Map<String, String>?) {
        if (params != null && params.isNotEmpty()) {
            val paramSb = StringBuilder()
            for ((key, value) in params) {
                paramSb.append("\n")
                paramSb.append("$key=$value")
            }
            paramSb.append("\n")
            MLog.v(String.format("Url=%s\nParams: {%s}", url, paramSb.toString()))
        }
    }

}