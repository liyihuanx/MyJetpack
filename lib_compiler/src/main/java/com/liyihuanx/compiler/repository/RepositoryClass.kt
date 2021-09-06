package com.liyihuanx.compiler.repository

import com.liyihuanx.compiler.types.*
import com.squareup.kotlinpoet.asTypeName
import java.util.*

import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

/**
 * Reposity类
 */
class RepositoryClass(typeElement: TypeElement) {

    val simpleName = typeElement.simpleName()
    val serviceType = typeElement.asType().asTypeName()
    val packageName = typeElement.packageName()
    val methods = LinkedList<RepositoryMethod>()
    val isAbstract = typeElement.modifiers.contains(Modifier.ABSTRACT)

}