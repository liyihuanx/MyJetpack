package com.liyihuanx.annotation

/**
 * @author created by liyihuanx
 * @date 2021/9/6
 * @description: 类的描述
 */

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class AutoApi(

    /**
     * 默认值对应的字段
     * @return
     */
    val keys: Array<String> = [],
    /**
     * 默认值　　ex :   @AutoApi(keys = ["begin", "sort", "limit"], defaultValues = ["0", "\"GS\"", "20"])
     * @return
     */
    val defaultValues: Array<String> = [],

    )
