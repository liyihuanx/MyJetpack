package com.liyihuanx.myjetpack

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.liyihuanx.module_base.http.RepositoryManager
import com.liyihuanx.module_base.http.datasource.CoroutineDataFetcher
import com.liyihuanx.module_base.utils.asToast
import com.liyihuanx.module_base.utils.coroutine
import com.liyihuanx.module_base.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import java.lang.Exception

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

    fun http() {
//        RepositoryManager.getRepo(ConfigRepository::class.java)
//            .getData {
//                getHttpData.value = it
//            }
    }




}