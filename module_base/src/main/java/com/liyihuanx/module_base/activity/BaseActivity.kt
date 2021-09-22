package com.liyihuanx.module_base.activity

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.liyihuanx.module_base.utils.StatusBarUtil

/**
 * @author created by liyihuanx
 * @date 2021/8/23
 * @description: 类的描述
 */
abstract class BaseActivity<T : ViewDataBinding> : BaseFinalActivity() {


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


    abstract fun bindViewOrData()
    open fun observeLiveData() {}

    open fun setViewStatusBar(){}
}