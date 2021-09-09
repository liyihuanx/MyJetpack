package com.liyihuanx.myjetpack

import android.app.Application
import com.liyihuanx.module_base.http.RepositoryManager
import com.liyihuanx.module_base.http.datasource.CoroutineDataFetcher
import com.liyihuanx.module_base.utils.asToast
import com.liyihuanx.module_base.utils.coroutine
import com.liyihuanx.module_base.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.collect
import java.lang.Exception

/**
 * @ClassName: TwoViewModel
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/8 22:49
 */
class TwoViewModel(application: Application) : BaseViewModel(application) {

    fun http() {
        RepositoryManager.getRepo(ConfigRepository::class.java)
            .config2 {

            }


    }


}