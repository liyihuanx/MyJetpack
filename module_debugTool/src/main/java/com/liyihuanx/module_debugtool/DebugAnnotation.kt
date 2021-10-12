package com.liyihuanx.module_debugtool

/**
 * @author created by liyihuanx
 * @date 2021/10/11
 * @description: 有点击事件的都加上注解
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class DebugAnnotation(val name: String, val desc: String)
