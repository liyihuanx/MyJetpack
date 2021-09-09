package com.liyihuanx.myjetpack

import android.app.Application
import com.liyihuanx.module_base.http.RepositoryManager
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
        coroutine {
            doWork {
                RepositoryManager.getRepo(ConfigRepository::class.java)
                    .getData()
                    .collect {

                    }
            }

            catchError {

            }
        }

        RepositoryManager.getRepo(ConfigRepository::class.java).http {

        }


    }

//    /**
//     * 在Repository中最后想实现的效果
//     */
//    fun http(error: ((e: Exception) -> Unit)? = null): ChapterBean? {
//        var result: ChapterBean? = null
//        coroutine {
//            doWork {
//                getData().collect {
//                    result = it
//                }
//            }
//            catchError {
//                error?.invoke(it)
//            }
//            onFinally {
//
//            }
//        }
//        return result
//    }
}