package com.liyihuanx.module_base.utils

import android.widget.Toast
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * @author created by liyihuanx
 * @date 2021/9/9
 * @description: 类的描述
 */


fun Any.defaultCoroutine(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: suspend CoroutineScope.() -> Unit
) {
    GlobalScope.launch(dispatcher) {
        try {
            block.invoke(this)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            e.message.asToast()
        } finally {

        }
    }
}


fun Any.coroutine(
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

fun String?.asToast() {
    Toast.makeText(AppContext.get(), this, Toast.LENGTH_SHORT).show()
}