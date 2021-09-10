package com.liyihuanx.compiler.autoFlowApi

import com.liyihuanx.compiler.*
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
class AutoFlowApiFuncBuilder(mRepositoryMethod: RepositoryMethod) :
    AbsFuncBuilder(mRepositoryMethod) {

    override fun addStatement(funcBuilder: FunSpec.Builder) {
        // 收集参数 这是调用接口时传入的那一串
        val paramsStringBuilder = StringBuilder()
        repositoryMethod.parameters.forEach {
            if (it.name == CACHE_STRATEGY_PARAMETER_NAME){
                return@forEach
            }
            paramsStringBuilder.append(it.name).append(",")
        }

        funcBuilder.addStatement(
            "%T {\n" +
                "doWork { \n" +
                    "\t%T { apiService.%L(%L) }.$startFetchData \n" +
                    "\t\t.%T {\n" +
                        "\t\t\tonResult.invoke(it) \n" +
                    "\t\t} \n" +
                 "} \n" +
                "catchError { onError?.invoke(it) } \n" +
                "onFinally { onComplete?.invoke() } \n" +
            "}",
            CoroutineLambdaType, // coroutine
            CoroutineDataFetcherClassType, // CoroutineDataFetcher
            repositoryMethod.methodName, // 方法名
            paramsStringBuilder.toString().dropLast(1), // 参数，丢弃最后一个","
            repositoryMethod.getCacheKey(),
            FlowCollectLambdaType // collect
        )

    }

    private val startFetchData by lazy {
        // 是不是添加在参数上
        if (repositoryMethod.isUserStrategyParameter) {
            "startFetchData($CACHE_STRATEGY_PARAMETER_NAME,\n %S)"
        } else if (repositoryMethod.isUserStrategyFunction) {
            "startFetchData(${repositoryMethod.netStrategy},\n %S)"
        } else {

        }
    }


    /**
     * 添加lambda表达式
     *  参数1：(e: Exception) -> Unit)? = null,
     *  参数2：(() -> Unit)? = null
     *  参数3: result: ((e: ChapterBean) -> Unit)
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


        /**
         * (() -> Unit)? = null
         */
        val onComplete = LambdaTypeName.get(
            returnType = UnitType
        ).asNullable()

        val onCompleteParameter = ParameterSpec.builder("onComplete", onComplete)
            .defaultValue("%L", null)

        /**
         * result: ((e: ChapterBean) -> Unit)
         */
        val asTypeName = repositoryMethod.returnType.javaToKotlinType()
        val onResult = LambdaTypeName.get(
            parameters = arrayOf(asTypeName),
            returnType = UnitType
        )
        val onResultParameter = ParameterSpec.builder("onResult", onResult)


        funcBuilder.addParameter(onErrorParameter.build())
            .addParameter(onCompleteParameter.build())
            .addParameter(onResultParameter.build())

    }


//    /**
//     * 在Repository中最后想实现的效果
//     */
//    fun getData(
//        onError: ((e: Exception) -> Unit)? = null,
//        onComplete: (() -> Unit)? = null,
//        onResult: ((e: ChapterBean) -> Unit),
//    ) {
//        coroutine {
//            doWork {
//                CoroutineDataFetcher { apiService.getData() }.startFetchData()
//                    .collect {
//                        result.invoke(it)
//                    }
//            }
//            catchError { onError?.invoke(it) }
//            onFinally { onComplete?.invoke() }
//        }
//    }

}