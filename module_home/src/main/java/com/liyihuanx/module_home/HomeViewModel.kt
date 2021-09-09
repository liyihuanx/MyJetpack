package com.liyihuanx.module_home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.liyihuanx.module_base.utils.coroutine
import com.liyihuanx.module_base.utils.defaultCoroutine
import com.liyihuanx.module_base.viewmodel.BaseViewModel

/**
 * @author created by liyihuanx
 * @date 2021/9/3
 * @description: 类的描述
 */
class HomeViewModel(application: Application) : BaseViewModel(application) {


    fun http() {
        coroutine {
            doWork {

            }

            catchError {

            }

            onFinally {

            }
        }
    }

    fun defaultHttp(){
        defaultCoroutine {



        }
    }

}