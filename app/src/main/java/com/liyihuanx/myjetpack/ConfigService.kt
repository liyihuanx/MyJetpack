package com.liyihuanx.myjetpack

import com.liyihuanx.annotation.AutoApi

/**
 * @author created by liyihuanx
 * @date 2021/9/7
 * @description: 类的描述
 */
interface ConfigService {

    @AutoApi
    fun config(page: String): String
}