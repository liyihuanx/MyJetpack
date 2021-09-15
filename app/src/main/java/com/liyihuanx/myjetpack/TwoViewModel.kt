package com.liyihuanx.myjetpack

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.liyihuanx.module_base.viewmodel.BaseViewModel
import kotlinx.coroutines.*

/**
 * @ClassName: TwoViewModel
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/8 22:49
 */
class TwoViewModel(application: Application) : BaseViewModel(application) {

    val getHttpData by lazy {
        MutableLiveData<ChapterBean>()
    }

    val getHttpData2 by lazy {
        MutableLiveData<ChapterBean>()
    }

    fun http() {
        getRepo(ConfigRepository::class.java)
            .getData(viewModelScope) {
                Log.d("QWER", "http: ${Gson().toJson(it)}")
            }
    }

    fun http2() {
        viewModelScope.async(Dispatchers.Main) {
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