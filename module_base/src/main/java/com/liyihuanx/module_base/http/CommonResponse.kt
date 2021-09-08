package com.liyihuanx.module_base.http

/**
 * @ClassName: CommonResponse
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/8 22:41
 */
data class CommonResponse<T>(
    var data: T,
    val message: String,
    val code: Int
)