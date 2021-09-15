package com.liyihuanx.module_base.viewmodel

import android.app.Application
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.liyihuanx.module_base.http.RepositoryManager

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
    var getFragmentManagerCall: (() -> androidx.fragment.app.FragmentManager)? = null

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
        getFragmentManagerCall?.invoke()?.let {
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
        getFragmentManagerCall = null
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


    fun <T> BaseViewModel.getRepo(cls: Class<T>): T {
        return RepositoryManager.getRepo(cls)
    }

}