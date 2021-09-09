package com.liyihuanx.module_base.http

/**
 * @ClassName: CommonResponse
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/8 22:41
 */
data class CommonListResponse<T>(
    var data: List<T>,
    val message: String,
    val code: Int
)


fun CommonListResponse<*>.isSuccess(): Boolean {
    return this.code == 200 || this.code == 0
}