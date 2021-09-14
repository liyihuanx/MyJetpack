package com.liyihuanx.compiler.repository

import com.liyihuanx.annotation.NetStrategy
import com.liyihuanx.compiler.CACHE_STRATEGY_PARAMETER_NAME
import com.liyihuanx.compiler.CacheInfo
import com.liyihuanx.compiler.ContinuationType
import com.liyihuanx.compiler.types.javaToKotlinType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.util.*
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement

/**
 * @author created by liyihuanx
 * @date 2021/9/6
 * @description: 类的描述
 */
abstract class RepositoryMethod(val executableElement: ExecutableElement) {
    val methodName = executableElement.simpleName.toString()
    val parameters = LinkedList<Parameter>()
    var returnType = executableElement.returnType.asTypeName()

    /**
     * 缓存策略 netStrategy 的信息
     */
    var cacheInfo = CacheInfo()

    /**
     * 是不是Observable
     */
    var isObservable = false

    /**
     * 是不是挂起函数
     */
    var isSuspend = false


    /**
     *  收集参数的方法
     */
    abstract fun initParameters()

    /**
     * 可以添加你想包装的类型到返回值中去
     */
    open fun addTypeInReturnType(returnType : TypeName) : TypeName{
        return returnType
    }

    /**
     * 返回值 是否为可空类型
     */
    open fun isNullable(): Boolean {
        return false
    }

    /**
     * 是否 为挂起函数
     */
    open fun isNeedSuspend(): Boolean {
        return isSuspend
    }

    /**
     * 是否 需要返回值
     */
    open fun isNeedReturnType(): Boolean {
        return true
    }



    open fun build() {
        // 使用了缓存策略的注解，并且要求要加到参数中
        if (cacheInfo.isUserStrategyParameter) {
            parameters.addFirst(
                Parameter(
                    CACHE_STRATEGY_PARAMETER_NAME,
                    Int::class.java.asTypeName(),
                )
            )
        }
        initParameters()
        /**
         *  // 挂起函数 会生成一个参数 Continuation<in xxxx >
         *  // 返回值1: kotlin.coroutines.Continuation<in java.lang.String>
         *  // 返回值2:kotlin.coroutines.Continuation<in java.util.List<java.lang.String>>
         */
        // 1.先判断是不是挂起函数，取出最后一个参数做判断
        val last = parameters.last.type
        if (last.toString().contains(ContinuationType.simpleName)) {
            isSuspend = true
            // 2.取出真正的返回值
            if (last is ParameterizedTypeName) { // <T> 等类型
                //  取出Continuation< xxx >中的返回值 "xxx"
                var returnTypeTemp = last.typeArguments[0].javaToKotlinType()
                val returnTypeTempStr = returnTypeTemp.toString()

                // 返回值2的处理
                if (returnTypeTempStr.contains('<') && returnTypeTempStr.contains('>')) {
                    // 去除两个括号，生成新的数组：[in java.util.List, java.lang.String, ]
                    val returnTypeTempStrArray = returnTypeTempStr.split("<", ">")

                    val list = ArrayList<TypeName>()
                    returnTypeTempStrArray.forEach {
                        if (it.isNotEmpty()) {
                            val string = it.replaceFirst("in ", "").replaceFirst("out ", "")
                            list.add(getClassName(string))
                        }
                    }

                    var typeName = list.last() // String

                    for (i in list.size - 2 downTo 0) { // 取出List<>
                        typeName =
                            (list[i] as ClassName).parameterizedBy(typeName) // 把String 放入 List<这里>
                    }
                    returnType = typeName

                } else {
                    // 返回值1的处理
                    // kotlin.String
                    val className = getClassName(returnTypeTemp.toString().replaceFirst("in ", ""))
                    returnType = className

                }
            }
        }

        returnType = addTypeInReturnType(returnType)

        // 3.移除掉最后一个Continuation参数
        if (isSuspend) {
            parameters.removeLast()
        }
    }

    private fun getClassName(typeStr: String): TypeName {
        val index = typeStr.lastIndexOf('.')

        return ClassName(
            typeStr.substring(0, index),
            typeStr.substring(index + 1, typeStr.length)
        ).javaToKotlinType()
    }


    fun getCacheKey(): String {
        var pStr = if (parameters.isEmpty()) {
            ""
        } else {
            "?"
        }
        parameters.forEachIndexed { index, parameter ->
            var str = "${parameter.name}=\${${parameter.name}}"
            if (index != parameters.size - 1) {
                str = "$str&"
            }
            pStr += str
        }

        return "cache//${(executableElement.enclosingElement as TypeElement).qualifiedName}//$methodName${pStr}"
    }

    /**
     * 参数
     * @param name 参数名
     * @param type 参数类型
     * @param defaultValue 默认值
     */
    inner class Parameter(var name: String, val type: TypeName, val defaultValue: String? = null)
}