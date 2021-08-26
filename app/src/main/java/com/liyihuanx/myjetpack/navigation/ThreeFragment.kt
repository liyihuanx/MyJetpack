package com.liyihuanx.myjetpack.navigation

import com.liyihuanx.module_base.fragment.BaseFragment
import com.liyihuanx.myjetpack.R
import com.liyihuanx.myjetpack.databinding.FragmentThreeBinding

/**
 * @author created by liyihuanx
 * @date 2021/8/23
 * @description: 类的描述
 */
class ThreeFragment: BaseFragment<FragmentThreeBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_three
    }

    override fun initViewOrData() {
    }
}