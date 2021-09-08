package com.liyihuanx.myjetpack

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import com.liyihuanx.module_base.http.RepositoryManager
import com.liyihuanx.module_base.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

/**
 * @ClassName: TwoViewModel
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/8 22:49
 */
class TwoViewModel(application: Application) : BaseViewModel(application) {

    fun http() {
        coroutine {
            doWork {
                RepositoryManager.getRepo(ConfigRepository::class.java)
                    .getData()
                    .collect {
                        Log.d("QWER", "http: ${Gson().toJson(it)}")
                    }
            }

            catchError {
                Log.d("QWER", "Exception: ${it.message}")

            }
        }
    }

}