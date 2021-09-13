package com.liyihuanx.module_base.viewmodel

import android.app.Application
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import com.liyihuanx.module_base.http.RepositoryManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author created by liyihuanx
 * @date 2021/9/3
 * @description: 类的描述
 */
open class BaseViewModel : AndroidViewModel, LifecycleObserver {

    var mData: Bundle? = null
        private set

    constructor(application: Application) : super(application)
    constructor(application: Application, data: Bundle?) : super(application) {
        mData = data
    }


    /**
     * 获取activity fm
     */
    var getFragmentManagrCall: (() -> androidx.fragment.app.FragmentManager)? = null

    /**
     * 回调showloading
     */
    var showLoadingCall: ((show: Boolean) -> Unit)? = null

    /**
     * 接受activity 回调
     */
    var finishedActivityCall: (() -> Unit)? = null


    /**
     * 显示弹窗
     */
    fun showDialog(tag: String, call: () -> androidx.fragment.app.DialogFragment) {
        getFragmentManagrCall?.invoke()?.let {
            call().show(it, tag)
        }
    }


    override fun onCleared() {
        super.onCleared()
        removeCall()
    }

    /**
     * 页面销毁
     */
    private fun removeCall() {
        finishedActivityCall = null
        getFragmentManagrCall = null
        showLoadingCall = null
    }

    private fun getAppContext(): Application {
        return getApplication<Application>()
    }

    fun toast(@StringRes msgRes: Int) {
        Toast.makeText(
            getAppContext(),
            getAppContext().resources.getString(msgRes),
            Toast.LENGTH_SHORT
        ).show()
    }

    fun toast(msg: String?) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(getAppContext(), msg, Toast.LENGTH_SHORT).show()
        }
    }


    fun BaseViewModel.defaultBg(block: suspend CoroutineScope.() -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                e.printStackTrace()
                toast(e.message)
            } finally {

            }
        }
    }

    fun BaseViewModel.bg(c: CoroutineScopeWrap.() -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            val block = CoroutineScopeWrap()
            c.invoke(block)
            try {
                block.work.invoke(this)

            } catch (e: Exception) {
                block.error.invoke(e)
            } finally {
                block.complete.invoke()
            }
        }
    }

    class CoroutineScopeWrap {
        var work: (suspend CoroutineScope.() -> Unit) = {}
        var error: (e: Exception) -> Unit = {}
        var complete: () -> Unit = {}

        fun doWork(call: suspend CoroutineScope.() -> Unit) {
            this.work = call
        }

        fun catchError(error: (e: Exception) -> Unit) {
            this.error = error
        }

        fun onFinally(call: () -> Unit) {
            this.complete = call
        }
    }

    fun <T> BaseViewModel.getRepo(cls: Class<T>): T {
        return RepositoryManager.getRepo(cls)
    }

}