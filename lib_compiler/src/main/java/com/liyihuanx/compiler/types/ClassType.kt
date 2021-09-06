package com.liyihuanx.compiler.types

import com.liyihuanx.compiler.types.TypeUtils
import com.liyihuanx.compiler.types.asKotlinTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import javax.lang.model.type.TypeMirror
import com.squareup.kotlinpoet.TypeName as KotlinTypeName

class ClassType(
    private val jvmClassName: String,
    private vararg val typeParameterClassTypes: ClassType
) {
    private val typeMirror: TypeMirror by lazy {
        TypeUtils.getTypeFromClassName(jvmClassName).erasure()
    }


    val kotlin: KotlinTypeName by lazy {
        if (typeParameterClassTypes.isNotEmpty()) {
            (typeMirror.asKotlinTypeName() as? com.squareup.kotlinpoet.ClassName)?.parameterizedBy(
                *(Array(
                    typeParameterClassTypes.size
                ) { i -> typeParameterClassTypes[i].kotlin })
            )
                ?: throw IllegalArgumentException("Only Declared class type should be parameterized.")
        } else {

            typeMirror.asKotlinTypeName()
        }
    }


    operator fun get(vararg typeParameterClassTypes: ClassType) =
        ClassType(jvmClassName, *typeParameterClassTypes)

    override fun toString(): String {
        return typeMirror.toString()
    }
}