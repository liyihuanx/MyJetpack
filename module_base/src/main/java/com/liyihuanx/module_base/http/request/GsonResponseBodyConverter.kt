package com.liyihuanx.module_base.http.request

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.liyihuanx.module_base.http.bean.BaseCommonListResponse
import com.liyihuanx.module_base.http.bean.BaseCommonResponse
import com.liyihuanx.module_base.http.bean.isSuccess
import com.liyihuanx.module_base.http.CustomHttpException
import okhttp3.ResponseBody
import retrofit2.Converter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author created by liyihuanx
 * @date 2021/9/9
 * @description: 把数据转换成自己想要的格式
 */
class GsonResponseBodyConverter<T>(
    private val mGson: Gson,
    private var type: Type,
    private val adapter: TypeAdapter<T>
) : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody): T? {
        val response = value.string()
        var baseBean: BaseCommonResponse<T>? = null
        try {
            if (TextUtils.isEmpty(response)) {
                throw JsonIOException("网络异常 101")
            }

            // 返回的List<T>列表
            if (type.toString().contains("List")) {
                var sub: Type? = null
                if (type is ParameterizedType) {
                    sub = (type as ParameterizedType).actualTypeArguments[0]
                }
                val result = response.replace("\"data\":{}", "\"data\":[]")
                val p = ParameterizedTypeImpl(
                    arrayOf(sub),
                    BaseCommonListResponse::class.java,
                    BaseCommonListResponse::class.java
                )
                val listBean: BaseCommonListResponse<T> = mGson.fromJson(result, p)

//                // 这样会在recyclerview的adapter时候报错(应该是遍历的时候)
//                // com.google.gson.internal.LinkedTreeMap cannot be cast to xxx
//                val typeToken = object : TypeToken<BaseCommonListResponse<T>>(){}.type
//                val baseBean: BaseCommonListResponse<T> = mGson.fromJson(result, typeToken)

                if (!listBean.isSuccess()) {
                    throw CustomHttpException(listBean.errorCode, listBean.errorMsg)
                }
                return listBean.data as T
            }

            // 普通的object对象
            val p = ParameterizedTypeImpl(
                arrayOf(type),
                BaseCommonResponse::class.java,
                BaseCommonResponse::class.java
            )

            baseBean = mGson.fromJson(response, p)
            if (!baseBean?.isSuccess()!!) {
                throw CustomHttpException(baseBean.errorCode, baseBean.errorMsg)
            }
        } catch (e: Exception) {
            Log.d("QWER", "Exception: ${e.message}")
        } finally {
            value.close()
        }

        return baseBean!!.data
    }

}
