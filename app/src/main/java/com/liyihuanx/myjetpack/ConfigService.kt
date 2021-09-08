package com.liyihuanx.myjetpack

import com.liyihuanx.annotation.AutoApi
import com.liyihuanx.annotation.NetStrategy

/**
 * @author created by liyihuanx
 * @date 2021/9/7
 * @description: 类的描述
 */
interface ConfigService {

    @AutoApi
    suspend fun config(page: String): String

    @AutoApi(cache = NetStrategy.OnlyCache, keys = ["page"], defaultValues = ["\"GS\""])
    suspend fun config2(page: String): List<String>
}