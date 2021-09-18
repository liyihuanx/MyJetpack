package com.liyihuanx.module_base.fragment

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.liyihuanx.module_base.utils.StatusBarUtil

/**
 * @author created by liyihuanx
 * @date 2021/8/23
 * @description: 类的描述
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    // 布局是否初始化
    var isViewCreated = false

    lateinit var mBinding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        isViewCreated = true
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewOrData()
        observeLiveData()
    }


    abstract fun getLayoutId(): Int
    abstract fun initViewOrData()
    abstract fun observeLiveData()

}