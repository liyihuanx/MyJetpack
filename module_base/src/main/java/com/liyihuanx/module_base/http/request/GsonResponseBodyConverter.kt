package com.liyihuanx.module_base.http.request

import android.text.TextUtils
import android.util.Log
import com.alibaba.fastjson.util.ParameterizedTypeImpl
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.liyihuanx.module_base.http.CommonListResponse
import com.liyihuanx.module_base.http.CommonResponse
import com.liyihuanx.module_base.http.CustomHttpException
import com.liyihuanx.module_base.http.isSuccess
import okhttp3.ResponseBody
import retrofit2.Converter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author created by liyihuanx
 * @date 2021/9/9
 * @description: 把数据转换成自己想要的格式
 */
class GsonResponseBodyConverter<T>(gson: Gson, type: Type, adapter: TypeAdapter<T>) :
    Converter<ResponseBody, T> {

    private val mGson = gson
    private var type = type
    private val adapter = adapter

    override fun convert(value: ResponseBody): T {
        val response = value.string()
        var baseBean: CommonResponse<T>? = null
        try {
            if (TextUtils.isEmpty(response)) {
                throw JsonIOException("网络异常 101")
            }
            if (type.toString().startsWith("io.reactivex.Observable<")) {
                var sub: Type? = null
                if (type is ParameterizedType) {
                    sub = (type as ParameterizedType).actualTypeArguments[0]
                    type = sub
                }
            }


            if (type.toString().contains("List")) {
                var sub: Type? = null
                if (type is ParameterizedType) {
                    sub = (type as ParameterizedType).actualTypeArguments[0]
                }
                var result = response
                result = result.replace("\"data\":{}", "\"data\":[]")
                val p = ParameterizedTypeImpl(
                    arrayOf(sub),
                    CommonListResponse::class.java,
                    CommonListResponse::class.java
                )
                val baseBean: CommonListResponse<T> = mGson.fromJson(result, p)
                if (!baseBean.isSuccess()) {
                    throw CustomHttpException(baseBean.code, baseBean.message)
                }
                return baseBean.data as T
            }


            val p = ParameterizedTypeImpl(
                arrayOf(type),
                CommonResponse::class.java,
                CommonResponse::class.java
            )
            baseBean = mGson.fromJson(response, p)
            Log.d("QWER", "ParameterizedTypeImpl: $p")
            Log.d("QWER", "baseBean: $baseBean")

            if (!baseBean?.isSuccess()!!) {
                throw CustomHttpException(baseBean?.code, baseBean?.message)
            }
        } catch (e: Exception) {
            Log.d("QWER", "Exception: ${e.message}")
        } finally {
            value.close()
        }

        return baseBean!!.data
    }

}