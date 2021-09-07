package com.liyihuanx.compiler.repository

import com.liyihuanx.compiler.AptContext
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.util.*

import javax.lang.model.element.ExecutableElement
import kotlin.collections.HashMap

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
     * 是不是Observable<CommonParseModel<*>>
     */
    var  filterFunClass:ClassName ?= null


    abstract fun initParameters()
    open fun isfilterCommonParseModel():Boolean{
        return true
    }

    open fun build(){
        initParameters()
        val returnTypeTem = executableElement.returnType.asTypeName()
        if (returnTypeTem is ParameterizedTypeName) { // <T> 等类型
        }
    }

//    if (returnTypeTem is ParameterizedTypeName) { 是泛型类型
//        isObservable = returnTypeTem.rawType.simpleName == ObservableSimpleName
//        val returnType2 = returnTypeTem.typeArguments[0]
//        if (returnType2 is ParameterizedTypeName) {
//            //CommonParseModel<> returnType2
//            if (returnType2.rawType.simpleName == CommonParseModelSimpleName || returnType2.rawType.simpleName == CommonListResultSimpleName) {
//
//                val returnType3 = returnType2.typeArguments[0]
//                //是不是过滤　CommonParseModel
//
//                if(isfilterCommonParseModel()){
//                    if(returnType2.rawType.simpleName == CommonParseModelSimpleName ){
//                        returnType = (ObservableClassType as ClassName).parameterizedBy(returnType3)
//                        filterFunClass = FilterHttpCodeClassType
//                    }else{
//                        //CommonListResultSimpleName
//                        val listType = (ListClassType as ClassName).parameterizedBy(returnType3)
//                        returnType = (ObservableClassType as ClassName).parameterizedBy(listType)
//                        filterFunClass = ListFilterHttpCodeClassType
//                    }
//                }else{
//
//                    if(returnType2.rawType.simpleName == CommonParseModelSimpleName ){
//
//                        filterFunClass = OnlyFilterHttpCodeClassType
//                    }else{
//                        filterFunClass = ListOnlyFilterHttpCodeClassType
//                    }
//
//                    returnType = (ObservableClassType as ClassName).parameterizedBy(returnType2)
//                }
//
//            } else {
//                //isCommonParseModel = false
//                returnType = (ObservableClassType as ClassName).parameterizedBy(returnType2)
//            }
//        } else {
//            //isCommonParseModel = false
//            returnType = (ObservableClassType as ClassName).parameterizedBy(returnType2)
//        }
//    }

    /**
     * 参数
     * @param name 参数名
     * @param type 参数类型
     * @param defaultValue 默认值
     */
    inner class Parameter(var name: String, val type: TypeName, val defaultValue: String? = null)
}