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
 * reposity的方法
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
        //Observable<>
        val returnTypeTem = executableElement.returnType.asTypeName()
        AptContext.note(returnTypeTem)
//        if (returnTypeTem is ParameterizedTypeName) {
//            isObservable = returnTypeTem.rawType.simpleName == ObservableSimpleName
//            val returnType2 = returnTypeTem.typeArguments[0]
//            if (returnType2 is ParameterizedTypeName) {
//                //CommonParseModel<> returnType2
//                if (returnType2.rawType.simpleName == CommonParseModelSimpleName || returnType2.rawType.simpleName == CommonListResultSimpleName) {
//
//                    val returnType3 = returnType2.typeArguments[0]
//                    //是不是过滤　CommonParseModel
//
//                    if(isfilterCommonParseModel()){
//                        if(returnType2.rawType.simpleName == CommonParseModelSimpleName ){
//                            returnType = (ObservableClassType as ClassName).parameterizedBy(returnType3)
//                            filterFunClass = FilterHttpCodeClassType
//                        }else{
//                            //CommonListResultSimpleName
//                            val listType = (ListClassType as ClassName).parameterizedBy(returnType3)
//                            returnType = (ObservableClassType as ClassName).parameterizedBy(listType)
//                            filterFunClass = ListFilterHttpCodeClassType
//                        }
//                    }else{
//
//                        if(returnType2.rawType.simpleName == CommonParseModelSimpleName ){
//
//                            filterFunClass = OnlyFilterHttpCodeClassType
//                        }else{
//                            filterFunClass = ListOnlyFilterHttpCodeClassType
//                        }
//
//                        returnType = (ObservableClassType as ClassName).parameterizedBy(returnType2)
//                    }
//
//                } else {
//                    //isCommonParseModel = false
//                    returnType = (ObservableClassType as ClassName).parameterizedBy(returnType2)
//                }
//            } else {
//                //isCommonParseModel = false
//                returnType = (ObservableClassType as ClassName).parameterizedBy(returnType2)
//            }
//        }
    }


    /**
     * 参数
     */
    inner class Parameter(var name: String, val type: TypeName, val defaultValue: String? = null)
}