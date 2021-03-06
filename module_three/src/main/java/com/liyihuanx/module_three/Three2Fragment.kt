package com.liyihuanx.module_three

import com.liyihuanx.module_base.fragment.BaseLazyFragment
import com.liyihuanx.module_three.databinding.FragmentThree2Binding
import com.liyihuanx.module_base.fragment.MainFragment

/**
 * @author created by liyihuanx
 * @date 2021/9/16
 * @description: 类的描述
 */
class Three2Fragment : BaseLazyFragment<FragmentThree2Binding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_three2
    }

    override fun initViewOrData() {

    }

    override fun observeLiveData() {

    }

    override val getTagName: String
        get() = "Three2Fragment"
}