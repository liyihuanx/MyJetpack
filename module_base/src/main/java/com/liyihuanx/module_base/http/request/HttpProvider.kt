package com.liyihuanx.module_base.http.request

import android.text.TextUtils
import android.util.Log
import retrofit2.Retrofit
import java.lang.Exception
import java.util.*

/**
 * @ClassName: Api
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/21 21:58
 */
object HttpProvider {

    @JvmStatic
    private val apiProxies = HashMap<String, Any>()
    // 实际想要的效果
    // [configService,configService的实例] --> retrofit.create(tClass)


    @JvmStatic
    private val retrofitMap = HashMap<String, Retrofit>()


    /**
     * 传入要用的interface
     */
    @JvmStatic
    fun <T> defaultCreate(service: Class<T>): T {
        return newRetrofit(HttpConfig::class.java).newCreate(service)
    }

//    @JvmStatic
//    fun <T> customCreate(configClass: Class<out IHttpConfig>): T {
//        return newRetrofit(configClass).newCreate(service)
//    }



    /**
     * 创建Retrofit
     */
    @JvmStatic
    fun newRetrofit(configClass: Class<out IHttpConfig>): Retrofit {
        val retrofit = retrofitMap[configClass.simpleName]
        if (retrofit == null) {
            try {
                val builder = Retrofit.Builder()
                val config = configClass.newInstance()
                config.build(builder)
                val build = builder.build()
                retrofitMap[configClass.simpleName] = build
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return retrofit!!
    }


    /**
     * 创建interface
     */
    private fun <T> Retrofit.newCreate(sClass: Class<T>): T {
        var service = apiProxies[sClass.simpleName]
        if (service == null) {
            service = this.create(sClass)
            apiProxies[sClass.simpleName] = service!!
        }
        return service as T
    }
}