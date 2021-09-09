package com.liyihuanx.module_base.http.request

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * @author created by liyihuanx
 * @date 2021/9/9
 * @description: 类的描述
 */
class HttpConverterFactory(gson: Gson) : Converter.Factory() {

    // 模仿GsonConverterFactory写的,可以在responseBodyConverter返回自己想要的格式
    private val mGson = gson

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val adapter = mGson.getAdapter(TypeToken.get(type))
        return GsonResponseBodyConverter(mGson, type, adapter)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        val adapter = mGson.getAdapter(TypeToken.get(type))
        return GsonRequestBodyConverter(mGson, adapter)

    }

}