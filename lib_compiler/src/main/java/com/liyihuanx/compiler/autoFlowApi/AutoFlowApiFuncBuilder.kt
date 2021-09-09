package com.liyihuanx.compiler.autoFlowApi

import com.liyihuanx.compiler.CoroutineDataFetcherClassType
import com.liyihuanx.compiler.CoroutineLambdaType
import com.liyihuanx.compiler.FlowCollectLambdaType
import com.liyihuanx.compiler.UnitType
import com.liyihuanx.compiler.autoApi.AbsFuncBuilder
import com.liyihuanx.compiler.repository.RepositoryMethod
import com.liyihuanx.compiler.types.javaToKotlinType
import com.squareup.kotlinpoet.*
import java.lang.Exception

/**
 * @author created by liyihuanx
 * @date 2021/9/7
 * @description: 添加方法语句 return CoroutineDataFetcher { apiService.config2(page) }.startFetchData()
 */
class AutoFlowApiFuncBuilder(private val mRepositoryMethod: RepositoryMethod) :
    AbsFuncBuilder(mRepositoryMethod) {

    override fun addStatement(funcBuilder: FunSpec.Builder) {
        // 收集参数 这是调用接口时传入的那一串
        val paramsStringBuilder = StringBuilder()
        repositoryMethod.parameters.forEach {
            paramsStringBuilder.append(it.name).append(",")
        }

        funcBuilder.addStatement(
            "var result : %T = null", repositoryMethod.returnType.javaToKotlinType().asNullable()
        )
        funcBuilder.addStatement(
            "%T{ \n" +
                "doWork{ %T { apiService.%L(%L) }.startFetchData().%T{ result = it } } \n" +
                "catchError{ onError?.invoke(it) } \n" +
                "onFinally{  onComplete?.invoke() } \n" +
            "}",
            CoroutineLambdaType,
            CoroutineDataFetcherClassType, // CoroutineDataFetcher
            repositoryMethod.methodName, // 方法名
            paramsStringBuilder.toString().dropLast(1), // 参数，丢弃最后一个","
            FlowCollectLambdaType
        )
        funcBuilder.addStatement(
            "return result"
        )
//        "return %T {\n  apiService.%L(%L) \n}.startFetchData()", " } \n" +

        // 用flow的
//        funcBuilder.addStatement(
//            "return %T {\n  apiService.%L(%L)"
//                    + " \n}.startFetchData()",
//            CoroutineDataFetcherClassType, // CoroutineDataFetcher
//            repositoryMethod.methodName, // 方法名
//            paramsStringBuilder.toString().dropLast(1) // 参数，丢弃最后一个","
//        )
    }

    // 参数1：(e: Exception) -> Unit)? = null,
    // 参数2：(() -> Unit)? = null

    /**
     * 添加lambda表达式
     */
    override fun addLambdaParameter(funcBuilder: FunSpec.Builder) {
        /**
         * ((e: Exception)-> Unit)
         * 写法1：
         * val asTypeName = Exception::class.java.asTypeName()
         * val onError1 = LambdaTypeName.get(
         *      parameters = *arrayOf(asClassName), // vararg 前面要加* 表示多个参数！！！！
         *      returnType = UnitType
         *      ).asNullable()
         */

        // 写法2：
        val arrayOf =
            listOf(ParameterSpec.builder("e", Exception::class.java.asClassName()).build())
        val onError = LambdaTypeName.get(
            parameters = arrayOf,
            returnType = UnitType
        ).asNullable()
        val onErrorParameter = ParameterSpec.builder("onError", onError)
            .defaultValue("%L", null)


        // (() -> Unit)? = null
        val onComplete = LambdaTypeName.get(
            returnType = UnitType
        ).asNullable()

        val onCompleteParameter = ParameterSpec.builder("onComplete", onComplete)
            .defaultValue("%L", null)

        funcBuilder.addParameter(onErrorParameter.build())
            .addParameter(onCompleteParameter.build())

    }


//    /**
//     * 在Repository中最后想实现的效果
//     */
//    fun http(error: ((e: Exception) -> Unit)? = null,onComplete: (() -> Unit)? = null): ChapterBean? {
//        var result: ChapterBean? = null
//        coroutine {
//            doWork {
//                CoroutineDataFetcher {
//                    apiService.getData()
//                }.startFetchData().collect {
//                    result = it
//                }
//            }
//            catchError {
//                error?.invoke(it)
//            }
//
//            onFinally {
//
//            }
//        }
//        return result
//    }

}