package com.liyihuanx.compiler.autoApi

import com.liyihuanx.compiler.UnitType
import com.liyihuanx.compiler.repository.RepositoryMethod
import com.liyihuanx.compiler.types.javaToKotlinType
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec

/**
 * @author created by liyihuanx
 * @date 2021/9/7
 * @description: 添加方法 suspend fun config2(page: String = "GS"): Flow<List<String>> { }
 */
abstract class AbsFuncBuilder(val repositoryMethod: RepositoryMethod) {
    fun build(typeBuilder: TypeSpec.Builder) {
        repositoryMethod.build()
        val funcBuilder = FunSpec.builder(repositoryMethod.methodName)
            .addModifiers(KModifier.PUBLIC)

        // 是否需要返回值
        if (repositoryMethod.isNeedReturnType()) {
            // 返回值是否为可空类型
            val realReturnType = if (repositoryMethod.isNullable()) {
                //把java类型的转成kotlin的，比如String 不加会是 java.lang.String
                repositoryMethod.returnType.javaToKotlinType().asNullable()
            } else {
                repositoryMethod.returnType.javaToKotlinType()
            }
            funcBuilder.returns(realReturnType)

            if (repositoryMethod.isNeedSuspend()) {
                funcBuilder.addModifiers(KModifier.SUSPEND)
            }
        }

        // 添加方法上的参数
        repositoryMethod.parameters.forEach {
            val paramSpecBuilder = ParameterSpec.builder(it.name, it.type)
//                .defaultValue("%L", it.defaultValue) 这样会 参数 = null, 但是在service时写的不是非空类型会出错

            //默认参数
            it.defaultValue?.let { defaultValue ->
                paramSpecBuilder.defaultValue("%L", defaultValue)
            }
            funcBuilder.addParameter(paramSpecBuilder.build())
        }

        // 在入参添加viewModelScope
        addViewModelScope(funcBuilder)
        // 在入参添加lambda表达式
        addLambdaParameter(funcBuilder)
        // 添加方法语句
        addStatement(funcBuilder)
        typeBuilder.addFunction(funcBuilder.build())
    }

    abstract fun addStatement(funcBuilder: FunSpec.Builder)
    open fun addLambdaParameter(funcBuilder: FunSpec.Builder) {}
    open fun addViewModelScope(funcBuilder: FunSpec.Builder) {}

}