package com.liyihuanx.module_base.http.bean

import java.io.Serializable

/**
 * @author created by liyihuanx
 * @date 2021/9/14
 * @description: 类的描述
 */
open class BaseCommonListResponse<T> : Serializable {
    var data: List<T>? = null
    var errorCode: Int? = 500
    var errorMsg: String? = "网络错误"
}

fun BaseCommonListResponse<*>.isSuccess(): Boolean {
    return (this.errorCode == 200 || this.errorCode == 0)
}