package com.liyihuan.module_common.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.liyihuan.module_common.bean.ChapterBean
import com.liyihuan.module_common.service.ConfigRepository
import com.liyihuanx.module_base.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @ClassName: TwoViewModel
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/8 22:49
 */
class TestViewModel(application: Application, data: Bundle?) : BaseViewModel(application,data) {

    val peer = data?.getString("test") // 用户id


    val getHttpData by lazy {
        MutableLiveData<List<ChapterBean>?>()
    }

    val getHttpData2 by lazy {
        MutableLiveData<ChapterBean>()
    }

    fun http(cacheStrategy: Int, page: Int) {
        getRepo(ConfigRepository::class.java)
            .getData(cacheStrategy, viewModelScope) {
                getHttpData.value = it
            }
    }

    fun http2() {
        viewModelScope.launch(Dispatchers.Main) {
            val withContext = withContext(Dispatchers.IO) {
                getRepo(ConfigRepository::class.java).config()
            }
            getHttpData2.value = withContext
        }

//        viewModelScopeCoroutine(viewModelScope) {
//            doWork {
//                getHttpData2.value = withContext(Dispatchers.IO) {
//                    getRepo(ConfigRepository::class.java).config()
//                }
//            }
//        }

    }

}