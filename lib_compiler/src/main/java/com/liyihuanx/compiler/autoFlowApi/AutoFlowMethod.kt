package com.liyihuanx.compiler.autoFlowApi

import com.liyihuanx.annotation.AutoFlowApi
import com.liyihuanx.compiler.AptContext
import com.liyihuanx.compiler.ContinuationType
import com.liyihuanx.compiler.FlowType
import com.liyihuanx.compiler.repository.RepositoryMethod
import com.liyihuanx.compiler.types.asKotlinTypeName
import com.liyihuanx.compiler.types.javaToKotlinType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import java.util.ArrayList
import javax.lang.model.element.ExecutableElement

/**
 * @ClassName: AutoFlowMethod
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/9 21:06
 */
class AutoFlowMethod(private val mExecutableElement: ExecutableElement) :
    RepositoryMethod(mExecutableElement) {
    override fun initParameters() {
        val defaultVMap = HashMap<String, String>()
        // 拿到注解的中，key-values 他是一 一对应的
        val annotation: AutoFlowApi = executableElement.getAnnotation(AutoFlowApi::class.java)
        annotation.keys.forEachIndexed { index, s ->
            defaultVMap[s] = annotation.defaultValues[index]
        }
//        AptContext.note(executableElement.modifiers) // [public, abstract]

        // 收集方法的参数
        executableElement.parameters.forEach {
            parameters.add(
                Parameter(
                    it.simpleName.toString(),
                    it.asType().asKotlinTypeName(),
                    defaultVMap[it.simpleName.toString()]
                )
            )
        }

    }

}