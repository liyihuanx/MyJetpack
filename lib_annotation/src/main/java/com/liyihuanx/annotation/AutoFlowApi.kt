package com.liyihuanx.annotation

/**
 * @ClassName: FlowAuto
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/9 20:57
 */


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class AutoFlowApi(

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