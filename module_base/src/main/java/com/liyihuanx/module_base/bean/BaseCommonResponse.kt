package com.liyihuanx.module_base.bean

import java.io.Serializable

/**
 * @ClassName: CommonResponse
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/8 22:41
 */
open class BaseCommonResponse<T> : Serializable {
    var data: T? = null
    var errorCode: Int? = 500
    var errorMsg: String? = "网络错误"
}


fun BaseCommonResponse<*>.isSuccess(): Boolean {
    return (this.errorCode == 200 || this.errorCode == 0)
}