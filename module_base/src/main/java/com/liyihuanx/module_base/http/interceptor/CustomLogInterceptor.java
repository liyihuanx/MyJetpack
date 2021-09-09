package com.liyihuanx.module_base.http.interceptor;


import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 打印出请求的Url和结果
 */
public class CustomLogInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        ResponseBody body = response.body();
        String bodyString = body.string();
        Log.v("Http_Request", "intercept: " + String.format("result\nUrl=%s\nbody=%s", request.url(), bodyString));
        // 解决java.lang.IllegalStateException: closed，因为Response.body().string()方法只能读取一次
        response.newBuilder()
                .body(ResponseBody.create(body.contentType(), bodyString))
                .build();
        return response;
    }
}