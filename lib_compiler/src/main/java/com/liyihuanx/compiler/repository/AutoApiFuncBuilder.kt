package com.liyihuanx.compiler.repository

import com.liyihuanx.compiler.CoroutineDataFetcherClassType
import com.squareup.kotlinpoet.FunSpec

/**
 * @author created by liyihuanx
 * @date 2021/9/7
 * @description: 添加方法语句
 */
class AutoApiFuncBuilder(private val mRepositoryMethod: RepositoryMethod) :
    AbsFuncBuilder(mRepositoryMethod) {

    override fun addStatement(funcBuilder: FunSpec.Builder) {

        // 收集参数
        val paramsStringBuilder = StringBuilder()
        repositoryMethod.parameters.forEach {
            paramsStringBuilder.append(it.name).append(",")
        }

        // 普通的
//        funcBuilder.addStatement(
//            " return apiService!!.%L(%L)",
//            repositoryMethod.methodName,
//            paramsStringBuilder.toString().dropLast(1)
//        )

        // 用flow的
        funcBuilder.addStatement(
            " return %T {\n  apiService.%L(%L)"
                    + " \n}.startFetchData()",
            CoroutineDataFetcherClassType, // CoroutineDataFetcher
            repositoryMethod.methodName, // 方法名
            paramsStringBuilder.toString().dropLast(1) // 参数，丢弃最后一个","
        )
    }
}