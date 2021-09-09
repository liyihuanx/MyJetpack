package com.liyihuanx.module_base.http

import com.liyihuanx.module_base.http.request.HttpProvider
import com.liyihuanx.module_base.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * @author created by liyihuanx
 * @date 2021/9/7
 * @description: 类的描述
 */
abstract class BaseRepository<T> {

    protected var apiService: T = HttpProvider.defaultCreate(Utils.findNeedType(javaClass) as Class<out T>)

}