package com.liyihuanx.compiler

import com.squareup.kotlinpoet.ClassName
import java.io.File

/**
 * @author created by liyihuanx
 * @date 2021/9/6
 * @description: 类的描述
 */
const val ANNOTATION_PACKAGE = "com.liyihuanx.annotation"
const val ANNOTATION_NAME = "$ANNOTATION_PACKAGE.AutoApi"
val BaseRepositoryClassType = ClassName("com.liyihuanx.module_base.http","BaseRepository")
val CoroutineDataFetcherClassType = ClassName("com.liyihuanx.module_base.http.datasource","CoroutineDataFetcher")
val ContinuationType = ClassName("kotlin.coroutines", "Continuation")
val FlowType = ClassName("kotlinx.coroutines.flow", "Flow")
val UnitType = ClassName("kotlin", "Unit")

val CoroutineLambdaType = ClassName("com.liyihuanx.module_base.utils","globalCoroutine")
val ViewModelScopeCoroutineType = ClassName("com.liyihuanx.module_base.utils","viewModelScopeCoroutine")


val FlowCollectLambdaType = ClassName("kotlinx.coroutines.flow","collect")
val CoroutineScopeType = ClassName("kotlinx.coroutines","CoroutineScope")


const val CACHE_STRATEGY_PARAMETER_NAME = "cacheStrategy"
const val VIEW_MODEl_SCOPE = "viewModelScope"

fun String.transformFromKaptPathToAptPath(): String {
    return File(this).parentFile.parentFile.parentFile.parentFile.parentFile.path + "/src/main/java"
    // 每个parentFile往上翻一个文件夹 一直到 D:\MyJetPack\app
    // D:\MyJetPack\app\build\generated\source\kaptKotlin/src/main/java
}