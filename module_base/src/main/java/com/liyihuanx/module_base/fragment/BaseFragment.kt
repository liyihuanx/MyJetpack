package com.liyihuanx.module_base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @author created by liyihuanx
 * @date 2021/8/23
 * @description: 类的描述
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    lateinit var mBinding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewOrData()
        observeLiveData()
    }


    abstract fun getLayoutId(): Int
    abstract fun initViewOrData()

    open fun defaultClick(v: View) {}
    open fun observeLiveData() {}

}