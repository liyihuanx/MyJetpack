package com.liyihuanx.module_base.utils

import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * @author created by liyihuanx
 * @date 2021/9/9
 * @description: 类的描述
 */

/**
 * 与viewModel绑定生命周期
 */
fun Any.viewModelScopeCoroutine(
    viewModelScope: CoroutineScope,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    c: CoroutineScopeWrap.() -> Unit,
) {
    viewModelScope.launch(dispatcher) {
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

/**
 * 与lifecycleScope绑定生命周期
 */
fun Any.lifecycleScopeCoroutine(
    lifecycleScope: LifecycleCoroutineScope,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    c: CoroutineScopeWrap.() -> Unit,
) {
    lifecycleScope.launch(dispatcher) {
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

/**
 * 普通的全局协程
 */
fun Any.globalCoroutine(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    c: CoroutineScopeWrap.() -> Unit,
){
    GlobalScope.launch(dispatcher) {
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

/**
 *  协程回调的包装类
 */
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

/**
 * Toast
 */
fun String?.asToast() {
    Toast.makeText(AppContext.get(), this, Toast.LENGTH_SHORT).show()
}