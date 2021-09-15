package com.liyihuan.module_common.utils

import androidx.lifecycle.*
import java.util.concurrent.ConcurrentHashMap

/**
 * @author created by liyihuanx
 * @date 2021/9/15
 * @description: 类的描述
 */
object LiveDataBus {

    private val mStickyMap = ConcurrentHashMap<String, StickyLiveData<*>>()

    fun <T> with(eventName: String): StickyLiveData<T> {
        var stickyLiveData = mStickyMap[eventName]
        if (stickyLiveData == null) {
            stickyLiveData = StickyLiveData<T>(eventName)
            mStickyMap[eventName] = stickyLiveData
        }

        return stickyLiveData as StickyLiveData<T>
    }


    /**
     * 将发射出去的LiveData包装一下，再做一些数据保存
     */
    class StickyLiveData<T>(private var eventName: String) : LiveData<T>() {

        var mLiveDataVersion = 0
        var mStickyData: T? = null

        fun setStickyData(stickyData: T) {
            mStickyData = stickyData
            setValue(stickyData)
        }

        fun postStickyData(stickyData: T) {
            mStickyData = stickyData
            postValue(stickyData)
        }

        override fun setValue(value: T) {
            mLiveDataVersion++
            super.setValue(value)
        }

        override fun postValue(value: T) {
            mLiveDataVersion++
            super.postValue(value)
        }

        fun observerSticky(owner: LifecycleOwner, sticky: Boolean, observer: Observer<in T>) {
            // 移除自己保存的StickyLiveData
            owner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    mStickyMap.remove(eventName)
                }
            })

            super.observe(owner, StickyObserver(this, sticky, observer))
        }

        /**
         * 重写LiveData的observer，把传入的observer包装一下
         */
        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            observerSticky(owner,false, observer)
        }
    }

    class StickyObserver<T>(
        private val stickyLiveData: StickyLiveData<T>,
        private val sticky: Boolean,
        private val observer: Observer<in T>
    ) : Observer<T> {

        /**
         * 打个比方：
         * 一条数据，名称为TestName，
         *      对应一个 StickyLiveData, 也就对应一个version, 初始的值为0，且这个可以复用
         *      且会创建StickyObserver，对应一个 mLastVersion, 初始的值为0
         *
         * 如果 StickyLiveData#version 和 StickyObserver#mLastVersion 没有对齐
         *      LastVersion < version --> 直接发送数据，就会产生黏性事件
         *
         * 源码就是这样没对齐，所以无法控制黏性事件
         *
         * 因为源码的流程
         *      将传入的observer包装成LifecycleBoundObserver(继承ObserverWrapper)会将传入的observer做保存和保存在hashMap
         *      最后在considerNotify遍历hashMap,活跃的观察者会调用observer.onChanged(t)去发送数据
         *
         * 所以这里把传入的observer包装成StickyObserver 进入源码后 --> 再变成LifecycleBoundObserver
         * 所以最终发送数据会调用StickyObserver的onChanged 就可以做黏性事件的处理了
         *
         */
        private var mLastVersion = stickyLiveData.mLiveDataVersion

        override fun onChanged(t: T) {

            if (mLastVersion >= stickyLiveData.mLiveDataVersion) {
                if (sticky && stickyLiveData.mStickyData != null) {
                    observer.onChanged(stickyLiveData.mStickyData)
                }
                return
            }
            observer.onChanged(t)
        }
    }


}