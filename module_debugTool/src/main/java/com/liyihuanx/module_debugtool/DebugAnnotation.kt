package com.liyihuanx.module_debugtool

/**
 * @author created by liyihuanx
 * @date 2021/10/11
 * @description: 类的描述
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class DebugAnnotation(val name: String, val desc: String)
