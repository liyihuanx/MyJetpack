package com.liyihuanx.module_base.activity

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @ClassName: BaseDataBindingActivity
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/26 21:23
 */
abstract class BaseDataBindingActivity<T : ViewDataBinding> : BaseFinalActivity() {


    lateinit var mBinding: T

    override fun createContainerView(): View {
        val view =  super.createContainerView()
        createDataBinding(view)
        return view
    }

    protected open fun createDataBinding(view: View): T {
        val viewTag = view.tag
        if (viewTag != null && viewTag is String) {
            mBinding = DataBindingUtil.bind(view)!!
        }
        return mBinding
    }


    override fun initViewForBase() {
        setViewStatusBar()
        bindViewOrData()
        observeLiveData()
    }
}