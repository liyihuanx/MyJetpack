package com.liyihuanx.compiler.repository

import com.liyihuanx.compiler.AptContext
import com.liyihuanx.compiler.ContinuationType
import com.liyihuanx.compiler.FlowType
import com.liyihuanx.compiler.types.javaToKotlinType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.util.*
import javax.lang.model.element.ExecutableElement

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
     * 是不是Observable
     */
    var isObservable = true

    /**
     * 是不是挂起函数
     */
    var isSuspend = false

    abstract fun initParameters()
    open fun isfilterCommonParseModel(): Boolean {
        return true
    }

    open fun build() {
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

                    AptContext.note(list)
                    var typeName = list.last() // String

                    for (i in list.size - 2 downTo 0) { // 取出List<>
                        typeName =
                            (list[i] as ClassName).parameterizedBy(typeName) // 把String 放入 List<这里>
                    }
                    // 生成 kotlin.collections.List<kotlin.String>
                    // 在放入Flow<T>中
                    returnType = FlowType.parameterizedBy(typeName)

                } else {
                    // 返回值1的处理
                    // kotlin.String
                    val className = getClassName(returnTypeTemp.toString().replaceFirst("in ", ""))
                    returnType = FlowType.parameterizedBy(className)

                }
            }
        }

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


    /**
     * 参数
     * @param name 参数名
     * @param type 参数类型
     * @param defaultValue 默认值
     */
    inner class Parameter(var name: String, val type: TypeName, val defaultValue: String? = null)
}