package com.liyihuanx.compiler.repository

import com.liyihuanx.compiler.AptContext
import com.liyihuanx.compiler.types.javaToKotlinType
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import kotlin.reflect.jvm.internal.impl.builtins.SuspendFunctionTypesKt

/**
 * @author created by liyihuanx
 * @date 2021/9/7
 * @description: 添加方法
 */
abstract class AbsFuncBuilder(val repositoryMethod: RepositoryMethod) {
    fun build(typeBuilder: TypeSpec.Builder) {
        repositoryMethod.build()
        val funcBuilder = FunSpec.builder(repositoryMethod.methodName)
            .addModifiers(KModifier.PUBLIC)
            .addModifiers(KModifier.SUSPEND)
            .returns(repositoryMethod.returnType.javaToKotlinType()) //把java类型的转成kotlin的，比如String 不加会是 java.lang.String

        // 添加方法上的参数
        repositoryMethod.parameters.forEach {
            val paramSpecBuilder = ParameterSpec.builder(it.name, it.type)
                .defaultValue("%L", it.defaultValue)
                .build()
            //默认参数
//            it.defaultValue?.let { defaultValue ->
//                paramSpecBuilder.defaultValue("%L", defaultValue)
//            }
            funcBuilder.addParameter(paramSpecBuilder)
        }

        addStatement(funcBuilder)
        typeBuilder.addFunction(funcBuilder.build())
    }

    abstract fun addStatement(funcBuilder: FunSpec.Builder)

}