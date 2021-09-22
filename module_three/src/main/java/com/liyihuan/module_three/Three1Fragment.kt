package com.liyihuan.module_three

import com.liyihuan.module_three.databinding.FragmentThree1Binding
import com.liyihuanx.module_base.fragment.BaseLazyFragment
import com.liyihuanx.module_base.fragment.MainFragment

/**
 * @author created by liyihuanx
 * @date 2021/9/16
 * @description: 类的描述
 */
class Three1Fragment : MainFragment<FragmentThree1Binding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_three1
    }

    override fun initViewOrData() {

    }

    override fun observeLiveData() {

    }

    override val getTagName: String
        get() = "Three1Fragment"
}