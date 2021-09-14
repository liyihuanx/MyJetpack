package com.liyihuanx.module_base.http.request

import android.text.TextUtils
import android.util.Log
import com.alibaba.fastjson.util.ParameterizedTypeImpl
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.liyihuanx.module_base.bean.BaseCommonListResponse
import com.liyihuanx.module_base.bean.BaseCommonResponse
import com.liyihuanx.module_base.bean.isSuccess
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
                    BaseCommonListResponse::class.java,
                    BaseCommonListResponse::class.java
                )
                val baseBean: BaseCommonListResponse<T> = mGson.fromJson(result, p)
                if (!baseBean.isSuccess()) {
                    throw CustomHttpException(baseBean.errorCode, baseBean.errorMsg)
                }
                return baseBean.data as T
            }


            val p = ParameterizedTypeImpl(
                arrayOf(type),
                BaseCommonResponse::class.java,
                BaseCommonResponse::class.java
            )
            baseBean = mGson.fromJson(response, p)
            Log.d("QWER", "ParameterizedTypeImpl: $p")
            Log.d("QWER", "baseBean: $baseBean")

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

//try {
//    // io.reactivex.Observable<com.qizhou.base.been.common.CommonParseModel<java.lang.Boolean>>
//    if (TextUtils.isEmpty(response)) {
//        throw new JsonIOException("网络异常 101");
//    }
//    if (type.toString().startsWith("io.reactivex.Observable<")) {
//        Type sub = null;
//        if (type instanceof java.lang.reflect.ParameterizedType) {
//            sub = ((java.lang.reflect.ParameterizedType) type).getActualTypeArguments()[0];
//            type = sub;
//        }
//    }
//    if (type.toString().contains("CommonParseModel")) {
//        //  ParameterizedTypeImpl p = new ParameterizedTypeImpl(new Type[]{type}, CommonParseModel.class, CommonParseModel.class);
//
//        CommonParseModel baseBean = gson.fromJson(response, type);
//        if (!baseBean.isSuccess()) {
//            throw new RenovaceException(baseBean.code, baseBean.message);
//        }
//        return (T) baseBean;
//    }
//
//    if (type.toString().contains("CommonListResult")) {
//
//        String result = response;
//        result = result.replace("\"data\":{}", "\"data\":[]");
//
//        //  ParameterizedTypeImpl p = new ParameterizedTypeImpl(new Type[]{type}, CommonListResult.class, CommonListResult.class);
//
//        CommonListResult baseBean = gson.fromJson(result, type);
//        if (!baseBean.isSuccess()) {
//            throw new RenovaceException(baseBean.code, baseBean.message);
//        }
//        return (T) baseBean;
//    }
//
//    if (type.toString().contains("List")) {
//
//
//        Type sub = null;
//        if (type instanceof java.lang.reflect.ParameterizedType) {
//            sub = ((java.lang.reflect.ParameterizedType) type).getActualTypeArguments()[0];
//        }
//        String result = response;
//        result = result.replace("\"data\":{}", "\"data\":[]");
//        ParameterizedTypeImpl p = new ParameterizedTypeImpl(new Type[]{sub}, CommonListResult.class, CommonListResult.class);
//
//        CommonListResult baseBean = gson.fromJson(result, p);
//        if (!baseBean.isSuccess()) {
//            throw new RenovaceException(baseBean.code, baseBean.message);
//        }
//        return (T) baseBean.data;
//    }
//
//
//    ParameterizedTypeImpl p = new ParameterizedTypeImpl(new Type[]{type}, CommonParseModel.class, CommonParseModel.class);
//    CommonParseModel baseBean = gson.fromJson(response, p);
//    if (!baseBean.isSuccess()) {
//        throw new RenovaceException(baseBean.code, baseBean.message);
//    }
//
//    return (T) baseBean.data;
//
//} finally {
//    value.close();
//}