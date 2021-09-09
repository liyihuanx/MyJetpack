package com.liyihuanx.compiler.autoApi

import com.liyihuanx.compiler.CoroutineDataFetcherClassType
import com.liyihuanx.compiler.repository.RepositoryMethod
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
        funcBuilder.addStatement(
            "return apiService.%L(%L)",
            repositoryMethod.methodName,
            paramsStringBuilder.toString().dropLast(1)
        )
    }
}